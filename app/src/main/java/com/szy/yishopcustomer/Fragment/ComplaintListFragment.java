package com.szy.yishopcustomer.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.*;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.szy.common.Interface.OnEmptyViewClickListener;
import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Activity.ComplaintDetailActivity;
import com.szy.yishopcustomer.Activity.OrderDetailActivity;
import com.szy.yishopcustomer.Activity.ShopActivity;
import com.szy.yishopcustomer.Adapter.ComplaintListAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.OrderList.ComplaintListModel;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;

import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import me.zongren.pullablelayout.Constant.Result;
import me.zongren.pullablelayout.Constant.Side;
import me.zongren.pullablelayout.Inteface.OnPullListener;
import me.zongren.pullablelayout.Main.PullableComponent;
import me.zongren.pullablelayout.Main.PullableLayout;

/**
 * Created by liwei on 2017/11/16.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class ComplaintListFragment extends YSCBaseFragment implements OnEmptyViewClickListener, OnPullListener {

    private static final int HTTP_USER_COMPLAINT = 0;

    @BindView(R.id.fragment_complaint_list_recyclerView_layout)
    PullableLayout fragment_pullableLayout;
    @BindView(R.id.fragment_complaint_list_recyclerView)
    CommonRecyclerView mRecyclerView;
    @BindView(R.id.fragment_complaint_list_search_layout)
    RelativeLayout mSearchLayout;

    @BindView(R.id.fragment_complaint_list_search_imageView)
    ImageView searchImage;
    @BindView(R.id.fragment_complaint_list_search_input)
    EditText editText;

    private ComplaintListAdapter mAdapter;
    private ComplaintListModel data;
    private int cur_page = 1;
    private String keyword;

    @Override
    public void onEvent(CommonEvent event) {
        switch (EventWhat.valueOf(event.getWhat())) {
            case EVENT_COMPLAINT_REFRESH:
                cur_page = 1;
                mAdapter.data.clear();
                keyword = "";
                refresh();
                break;
            default:
                super.onEvent(event);
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_complaint_list;

        mAdapter = new ComplaintListAdapter();
        mAdapter.onClickListener = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        searchImage.setOnClickListener(this);

        mRecyclerView.addOnScrollListener(mOnScrollListener);
        mRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        fragment_pullableLayout.topComponent.setOnPullListener(this);

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    keyword = textView.getText().toString();
                    cur_page = 1;
                    mAdapter.data.clear();
                    refresh();
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService
                            (Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                }
                return false;
            }
        });


        refresh();
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_complaint_list_search_imageView:
                cur_page = 1;
                mAdapter.data.clear();
                keyword = editText.getText().toString();
                refresh();
                break;
            default:
                ViewType viewType = Utils.getViewTypeOfTag(v);
                int position = Utils.getPositionOfTag(v);
                int extraInfo = Utils.getExtraInfoOfTag(v);
                switch (viewType) {
                    case VIEW_TYPE_DETAIL:
                        ComplaintListModel.DataBean.ListBean model = (ComplaintListModel.DataBean.ListBean)mAdapter.data.get(position);
                        openComplaintDetail(model.complaint_id);
                        break;
                    case VIEW_TYPE_SHOP:
                        ComplaintListModel.DataBean.ListBean model2 = (ComplaintListModel.DataBean.ListBean)mAdapter.data.get(position);
                        openShop(model2.shop_id);
                        break;
                    case VIEW_TYPE_ORDER:
                        ComplaintListModel.DataBean.ListBean model3 = (ComplaintListModel.DataBean.ListBean)mAdapter.data.get(position);
                        openOrderDetail(model3.order_id);
                        break;
                    default:
                        super.onClick(v);
                }

        }
    }

    private void openComplaintDetail(String complaint_id) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), ComplaintDetailActivity.class);
        intent.putExtra(Key.KEY_COMPLAINT_ID.getValue(), complaint_id);
        startActivity(intent);
    }
    private void openShop(String shop_id) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), ShopActivity.class);
        intent.putExtra(Key.KEY_SHOP_ID.getValue(), shop_id);
        startActivity(intent);
    }
    private void openOrderDetail(String order_id) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), OrderDetailActivity.class);
        intent.putExtra(Key.KEY_ORDER_ID.getValue(), order_id);
        startActivity(intent);
    }

    @Override
    public void onOfflineViewClicked() {
        refresh();
    }

    @Override
    protected void onRequestFailed(int what, String response) {
        switch (what) {
            case HTTP_USER_COMPLAINT:
                fragment_pullableLayout.topComponent.finish(Result.FAILED);
                mRecyclerView.showOfflineView();
            break;
            default:
                super.onRequestFailed(what, response);
                break;
        }
    }

    @Override
    public void onRequestSucceed(int what, String response) {
        switch (what) {
            case HTTP_USER_COMPLAINT:
                refreshCallback(response);
                break;
            default:
                super.onRequestSucceed(what, response);
        }
    }

    public void refresh() {
        if (data != null && cur_page > data.data.page.page_count) {
            //设置footer
            View footer = LayoutInflater.from(getContext()).inflate(R.layout.fragment_goods_list_item_empty, null);
            mAdapter.setFooterView(footer);
        } else {
            CommonRequest mRequest = new CommonRequest(Api.API_USER_COMPLAINT, HTTP_USER_COMPLAINT);
            mRequest.add("page[cur_page]", cur_page);
            if(!Utils.isNull(keyword)){
                mRequest.add("order_id",keyword);
            }
            addRequest(mRequest);
        }
    }


    private void refreshCallback(String response) {
        fragment_pullableLayout.topComponent.finish(Result.SUCCEED);

        HttpResultManager.resolve(response, ComplaintListModel.class, new HttpResultManager.HttpResultCallBack<ComplaintListModel>() {
            @Override
            public void onSuccess(ComplaintListModel back) {
                data = back;
                List<String> reasons = back.data.complaint_item;
                TreeMap<String, String> status = back.data.complaint_status_list;
                if(cur_page == 1) {
                    mAdapter.data.clear();
                    mAdapter.setFooterView(null);
                }

                if(back.data.list.size() > 0) {
                    mSearchLayout.setVisibility(View.VISIBLE);
                    mRecyclerView.hideEmptyView();

                    for(int i=0;i<back.data.list.size();i++){
                       back.data.list.get(i).add_time_format = Utils.times(back.data.list.get(i).add_time);
                       back.data.list.get(i).complaint_type_string = reasons.get(back.data.list.get(i).complaint_type);
                       back.data.list.get(i).role_type_string = status.get(back.data.list.get(i).complaint_status);
                    }

                    mAdapter.data.addAll(back.data.list);
                } else {
                    mSearchLayout.setVisibility(View.GONE);
                    mRecyclerView.showEmptyView();
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onEmptyData(int state) {
                mRecyclerView.showEmptyView();
            }

            @Override
            public void onFailure(String message) {
                mRecyclerView.showOfflineView();
            }
        });
    }

    private boolean upDataSuccess = true;
    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == 0) {
                if (mRecyclerView.reachEdgeOfSide(Side.BOTTOM)) {
                    if (upDataSuccess) {
                        try {
                            cur_page = data.data.page.cur_page + 1;
                        }catch (Exception e) {

                        }
                        refresh();
                    }
                }
            }
        }
    };


    @Override
    public void onCanceled(PullableComponent pullableComponent) {

    }

    @Override
    public void onLoading(PullableComponent pullableComponent) {
        cur_page = 1;
        mAdapter.data.clear();
        keyword = "";
        refresh();
    }

    @Override
    public void onSizeChanged(PullableComponent pullableComponent, int mSize) {

    }
}
