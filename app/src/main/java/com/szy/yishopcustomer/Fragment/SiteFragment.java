package com.szy.yishopcustomer.Fragment;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.*;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.yishopcustomer.Activity.RootActivity;
import com.szy.yishopcustomer.Adapter.SiteAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.CommonModel;
import com.szy.yishopcustomer.ResponseModel.Site.Model;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.View.SiteHeaderListView;
import com.szy.yishopcustomer.ViewModel.SiteEntity;
import com.yolanda.nohttp.RequestMethod;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * Created by liuzhifeng on 2016/8/25.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class SiteFragment extends YSCBaseFragment {

    @BindView(R.id.fragment_site_count)
    TextView mCity;
    @BindView(R.id.layout)
    LinearLayout layoutIndex;
    @BindView(R.id.tv)
    TextView tv_show;
    @BindView(R.id.listView)
    SiteHeaderListView mListView;
    private HashMap<String, Integer> selector;
    private List<SiteEntity> data;
    private Model model;

    public void getIndexView(final List<String> indexStr) {
        final int height = 50;
        final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup
                .LayoutParams.WRAP_CONTENT, height);
        for (int i = 0; i < indexStr.size(); i++) {
            final TextView tv = new TextView(getActivity());
            tv.setLayoutParams(params);
            tv.setText(indexStr.get(i));
            tv.setTextColor(ContextCompat.getColor(getActivity(),R.color.colorPrimary));
            tv.setPadding(20, 0, 20, 0);
            layoutIndex.addView(tv);
            layoutIndex.setOnTouchListener(new View.OnTouchListener() {

                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override
                public boolean onTouch(View v, MotionEvent event)

                {
                    float y = event.getY();
                    int index = (int) (y / height);
                    if (index > -1 && index < indexStr.size()) {// 防止越界
                        String key = indexStr.get(index);
                        if (selector.containsKey(key)) {
                            int pos = selector.get(key);
                            if (mListView.getHeaderViewsCount() > 0) {// 防止ListView有标题栏
                                mListView.setSelectionFromTop(pos, 0);
                            } else {
                                mListView.setSelectionFromTop(pos, 0);// 滑动到第一项
                            }
                            tv_show.setVisibility(View.VISIBLE);
                            tv_show.setText(indexStr.get(index));
                        }
                    }
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            //layoutIndex.setBackgroundColor(Color.parseColor("#f23030"));
                            break;

                        case MotionEvent.ACTION_MOVE:

                            break;
                        case MotionEvent.ACTION_UP:
                            layoutIndex.setBackgroundColor(Color.parseColor("#00ffffff"));
                            tv_show.setVisibility(View.GONE);
                            break;
                    }
                    return true;
                }
            });
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_site;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
//        Utils.toastUtil.showToast(getActivity(), getString(R.string.pleaseSelectASit));

        Utils.setViewTypeForTag(mCity, ViewType.VIEW_TYPE_SITE);
        mCity.setOnClickListener(this);

        refresh();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        if(Utils.isDoubleClick()) {
            return;
        }
        ViewType viewType = Utils.getViewTypeOfTag(view);
        int position = Utils.getPositionOfTag(view);
        int info = Utils.getExtraInfoOfTag(view);

        switch (viewType) {
            case VIEW_TYPE_SITE:
                if(model != null && model.data !=null) {
                    selectSite(model.data.site_id);
                }
                break;
            default:
                super.onClick(view);
        }
    }


    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_GET_SITE:
                refreshCallBack(response);
                break;
            case HTTP_GET_SITE_SELECT:
                HttpResultManager.resolve(response, CommonModel.class, new HttpResultManager.HttpResultCallBack<CommonModel>() {
                    @Override
                    public void onSuccess(CommonModel back) {
                        EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_REFRESH_INDEX
                                .getValue()));
                        EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_REFRESH_NEARBY
                                .getValue()));
                        startActivity(new Intent(getContext(), RootActivity.class));
                    }
                },true);

                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    private void refreshCallBack(String response) {
        data = new ArrayList<SiteEntity>();

        HttpResultManager.resolve(response, Model.class, new HttpResultManager.HttpResultCallBack<Model>() {
            @Override
            public void onSuccess(Model back) {
                model = back;
                mCity.getPaint().setFakeBoldText(true);
                mCity.setText(model.data.site_name);

                for (int i = 0; i < model.data.site_list.size(); i++) {
                    SiteEntity good = new SiteEntity(model.data.site_list.get(i).site_letter, model.data.site_list.get(i).site_name);

                    data.add(good);
                }
                selector = new HashMap<String, Integer>();
                for (int j = 0; j < model.data.site_letters.size(); j++) {// 循环字母表，找出newPersons中对应字母的位置
                    int current = 0;
                    for (int i = 0; i < data.size(); i++) {
                        if (data.get(i).getTitle().equals(model.data.site_letters.get(j)) && current
                                == 0) {
                            selector.put(model.data.site_letters.get(j), i);
                            current++;

                        }
                    }
                }

                getIndexView(model.data.site_letters);
                setUpAdapter();
            }
        });

    }

    private void setUpAdapter() {
        // * 创建新的HeaderView，即置顶的HeaderView
        View HeaderView = getActivity().getLayoutInflater().inflate(R.layout
                .site_listview_item_header, mListView, false);
        mListView.setPinnedHeader(HeaderView);
        SiteAdapter siteAdapter = new SiteAdapter(getActivity(), data);
        mListView.setAdapter(siteAdapter);
        mListView.setOnScrollListener(siteAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                selectSite(model.data.site_list.get(position).site_id);
            }
        });
    }

    public void refresh(){
        CommonRequest addCartRequest = new CommonRequest(Api.API_SITE, HttpWhat.HTTP_GET_SITE
                .getValue());
        addRequest(addCartRequest);
    }
    private void selectSite(String siteId){
        CommonRequest mRequest = new CommonRequest(Api.API_SITE_SELECT, HttpWhat
                .HTTP_GET_SITE_SELECT.getValue(), RequestMethod.POST);
        mRequest.add("site_id", siteId);
        addRequest(mRequest);
    }

}


