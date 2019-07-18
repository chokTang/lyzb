package com.szy.yishopcustomer.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.szy.common.Interface.OnEmptyViewClickListener;
import com.szy.common.Other.CommonRequest;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Activity.GroupBuyListActivity;
import com.szy.yishopcustomer.Adapter.GroupBuyAdapter;
import com.szy.yishopcustomer.Adapter.ImageAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.Config;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.ViewType;
import com.szy.yishopcustomer.Other.BottomMenuController;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.DividerModel;
import com.szy.yishopcustomer.ResponseModel.GroupBuy.DataModel;
import com.szy.yishopcustomer.ResponseModel.GroupBuy.GroupBuyModel;
import com.szy.yishopcustomer.ResponseModel.GroupBuy.GroupBuySlideListModel;
import com.szy.yishopcustomer.ResponseModel.GroupBuy.Model;
import com.szy.yishopcustomer.Util.BrowserUrlManager;
import com.szy.yishopcustomer.Util.JSON;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.View.CirclePageIndicator;
import com.szy.yishopcustomer.View.HeadWrapContentViewPager;
import com.szy.yishopcustomer.ViewModel.EmptyItemModel;

import java.util.ArrayList;

import butterknife.BindView;
import me.zongren.pullablelayout.Inteface.OnPullListener;
import me.zongren.pullablelayout.Main.PullableComponent;

/**
 * Created by liwei on 16/11/01.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class GroupBuyFragment extends YSCBaseFragment implements OnPullListener,
        OnEmptyViewClickListener {

    private static final int STATE_TAB_ONE = 1;
    private static final int STATE_TAB_TWO = 2;

    private static final int CODE_SUCCESS = 0;
    @BindView(R.id.fragment_group_buy_recyclerView)
    CommonRecyclerView mRecyclerView;
//    @BindView(R.id.fragment_group_buy_pullableLayout)
//    PullableLayout mPullableLayout;

    @BindView(R.id.fragment_group_buy_banner_viewPager)
    public HeadWrapContentViewPager viewPager;
    @BindView(R.id.fragment_group_buy_banner_pageIndicator)
    public CirclePageIndicator pageIndicator;

    @BindView(R.id.fragment_group_buy_tab)
    LinearLayout fragment_group_buy_tab;
    @BindView(R.id.fragment_group_buy_tab_float)
    LinearLayout fragment_group_buy_tab_float;

    @BindView(R.id.activity_appBarLayout)
    AppBarLayout mAppBarLayout;

    private LinearLayoutManager mLayoutManager;
    private GroupBuyAdapter mGroupBuyAdapter;
    private Model mModel;
    private String shopId;

    private int currentTabState = STATE_TAB_ONE;

    @Override
    public void onCanceled(PullableComponent pullableComponent) {

    }

    @Override
    public void onLoading(PullableComponent pullableComponent) {
        refresh();
    }

    @Override
    public void onSizeChanged(PullableComponent pullableComponent, int mSize) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_tab_one:
                switchUpdate(STATE_TAB_ONE);
                break;
            case R.id.fragment_tab_two:
                switchUpdate(STATE_TAB_TWO);
                break;
            default:
                if (Utils.isDoubleClick()) {
                    return;
                }

                ViewType viewType = Utils.getViewTypeOfTag(v);
                int position = Utils.getPositionOfTag(v);
                int extraInfo = Utils.getExtraInfoOfTag(v);
                switch (viewType) {
                    case VIEW_TYPE_GROUP_BUY_BANNER:
                        openAd(position, extraInfo);
                        break;
                    case VIEW_TYPE_GROUP:
                        openGroupBuyList(String.valueOf(extraInfo));
                        break;
                    default:
                        super.onClick(v);
                }
                break;
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_group_buy;

        mModel = new Model();

        Intent intent = getActivity().getIntent();
        shopId = intent.getStringExtra(Key.KEY_SHOP_ID.getValue());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        BottomMenuController.init(getContext(), view);

        mGroupBuyAdapter = new GroupBuyAdapter();

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mGroupBuyAdapter);
//        mPullableLayout.topComponent.setOnPullListener(this);
        mGroupBuyAdapter.onClickListener = this;
        mRecyclerView.setEmptyViewClickListener(this);


        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if(Math.abs(verticalOffset) >= fragment_group_buy_tab.getTop()) {
                    fragment_group_buy_tab.setVisibility(View.INVISIBLE);
                    fragment_group_buy_tab_float.setVisibility(View.VISIBLE);
                } else {
                    fragment_group_buy_tab.setVisibility(View.VISIBLE);
                    fragment_group_buy_tab_float.setVisibility(View.GONE);
                }
            }
        });


        switchUpdate(currentTabState);
        return view;
    }

    private void switchUpdate(int state) {
        currentTabState = state;

        tabClick(fragment_group_buy_tab);
        tabClick(fragment_group_buy_tab_float);

        refresh();
    }

    private void tabClick(LinearLayout view){
        int selectColor = Color.parseColor("#FF6C74");
        int unSelectColor = Color.parseColor("#FFE6EA");

        LinearLayout fragment_tab_one = (LinearLayout) view.getChildAt(0);
        LinearLayout fragment_tab_two = (LinearLayout) view.getChildAt(1);

        fragment_tab_one.setOnClickListener(this);
        fragment_tab_two.setOnClickListener(this);

        View tab_one = fragment_tab_one.getChildAt(0);
        View tab_two = fragment_tab_two.getChildAt(0);

        View tab_one_v = fragment_tab_one.getChildAt(1);
        View tab_two_v = fragment_tab_two.getChildAt(1);

        switch (currentTabState) {
            case STATE_TAB_ONE:
                tab_one.setBackgroundColor(selectColor);
                tab_one.setSelected(true);
                tab_one_v.setVisibility(View.VISIBLE);

                tab_two.setBackgroundColor(unSelectColor);
                tab_two.setSelected(false);
                tab_two_v.setVisibility(View.INVISIBLE);
                break;
            case STATE_TAB_TWO:
                tab_one.setBackgroundColor(unSelectColor);
                tab_one.setSelected(false);
                tab_one_v.setVisibility(View.INVISIBLE);

                tab_two.setBackgroundColor(selectColor);
                tab_two.setSelected(true);
                tab_two_v.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onOfflineViewClicked() {
        refresh();
    }

    @Override
    protected void onRequestFailed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_GROUP_BUY_LIST:
                mRecyclerView.showOfflineView();
                break;
            default:
                super.onRequestFailed(what, response);
                break;
        }
    }

    @Override
    public void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_GROUP_BUY_LIST:
                refreshCallback(response);
                break;
            default:
                super.onRequestSucceed(what, response);
        }
    }

    public void openAd(int position, int extraInfo) {
        DataModel dataModel = (DataModel) mGroupBuyAdapter.data.get(position);
        GroupBuySlideListModel slideListModel = dataModel.slide_list.get(extraInfo);


        if (!Utils.isNull(slideListModel.link)) {
            new BrowserUrlManager().parseUrl(getContext(), slideListModel.link);
        } else {
            if (Config.DEBUG) {
                Toast.makeText(getActivity(), "链接为空", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void openGroupBuyList(String actId) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), GroupBuyListActivity.class);
        intent.putExtra(Key.KEY_GROUP_BUY_ACT_ID.getValue(), actId);
        startActivity(intent);
    }

    public void refresh() {
        CommonRequest mGroupBuyRequest = new CommonRequest(Api.API_GROUP_BUY, HttpWhat
                .HTTP_GROUP_BUY_LIST.getValue());
        if (!Utils.isNull(shopId)) {
            mGroupBuyRequest.add("shop_id", shopId);
        }

        if(currentTabState == STATE_TAB_TWO) {
            mGroupBuyRequest.add("finish_type", 0);
        }
        addRequest(mGroupBuyRequest);
    }

    private void refreshCallback(String response) {
        mModel = JSON.parseObject(response, Model.class);
        if (mModel.code == 0) {
            bindBanner();
            setUpAdapterData();

        } else {
        }
    }

    private void bindBanner() {

        if (!Utils.isNull(mModel.data.slide_list)) {
            DataModel dataModel = new DataModel();
            dataModel = mModel.data;

            if (Utils.isNull(dataModel.slide_list)) {
                return;
            }


            ArrayList<String> imgs;
            ImageAdapter imageAdapter;
            final BrowserUrlManager mBrowserUrlManager = new BrowserUrlManager();

            imgs = new ArrayList<>();
            viewPager.setAdapter(imageAdapter = new ImageAdapter(viewPager.getContext(), imgs));
            final DataModel finalDataModel = dataModel;
            imageAdapter.listener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Object obj = view.getTag(R.id.imagePosition);
                    int position = obj == null ? 0 : (int) obj;
                    try {
                        mBrowserUrlManager.parseUrl(viewPager.getContext(), finalDataModel.slide_list.get(position).link);
                    } catch (Exception e) {
                    }
                }
            };


            pageIndicator.setViewPager(viewPager);
            pageIndicator.setSnap(true);

            imgs.clear();
            for (int i = 0; i < dataModel.slide_list.size(); i++) {
                imgs.add(Utils.urlOfImage(dataModel.slide_list.get(i).img));
            }
            viewPager.getMyPagerAdapter().notifyDataSetChanged();
        }
    }

    private void setUpAdapterData() {
        mGroupBuyAdapter.data.clear();

        long currentTime = mModel.data.context.current_time;
        if (!Utils.isNull(mModel.data.group_buy_list)&&mModel.data.group_buy_list.size()>0) {
            for(int i = 0 ; i < mModel.data.group_buy_list.size() ; i ++) {
                if(i != 0) {
                    DividerModel dividerModel = new DividerModel();
                    mGroupBuyAdapter.data.add(dividerModel);
                }

                GroupBuyModel groupBuyModel = mModel.data.group_buy_list.get(i);
                groupBuyModel.current_time = currentTime;
                mGroupBuyAdapter.data.add(groupBuyModel);
            }
        } else {
            EmptyItemModel emptyModel = new EmptyItemModel();
            mGroupBuyAdapter.data.add(emptyModel);
        }

        mGroupBuyAdapter.notifyDataSetChanged();
    }
}
