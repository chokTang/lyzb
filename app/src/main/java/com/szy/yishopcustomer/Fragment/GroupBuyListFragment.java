package com.szy.yishopcustomer.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;

import com.szy.common.Interface.OnEmptyViewClickListener;
import com.szy.common.Other.CommonRequest;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Activity.GoodsActivity;
import com.szy.yishopcustomer.Adapter.GroupBuyListAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.ViewType;
import com.szy.yishopcustomer.Other.BottomMenuController;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Checkout.CheckoutDividerModel;
import com.szy.yishopcustomer.ResponseModel.GroupBuyList.GroupBuylistModel;
import com.szy.yishopcustomer.ResponseModel.GroupBuyList.Model;
import com.szy.yishopcustomer.Util.JSON;
import com.szy.yishopcustomer.Util.Utils;

import butterknife.BindView;
import io.github.erehmi.countdown.CountDownTask;
import me.zongren.pullablelayout.Constant.Side;
import me.zongren.pullablelayout.Main.PullableLayout;

/**
 * Created by liwei on 2016/6/17.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司
 */
public class GroupBuyListFragment extends YSCBaseFragment implements OnEmptyViewClickListener {
    @BindView(R.id.fragment_group_buy_list_pullableLayout)
    PullableLayout mPullableLayout;
    @BindView(R.id.fragment_group_buy_list_pullableRecyclerView)
    CommonRecyclerView mRecyclerView;

    private Model mResponseModel;
    private String act_id;

    private LinearLayoutManager mLayoutManager;
    private GroupBuyListAdapter mGroupBuyListAdapter;
    private int page = 1;
    private int pageCount;
    private boolean upDataSuccess = false;

    private CountDownTask mCountDownTask;
    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == 0) {
                if (mRecyclerView.reachEdgeOfSide(Side.BOTTOM)) {
                    if (upDataSuccess) {
                        loadMore();
                    }

                }
            }
        }
    };

    @Override
    public void onClick(View v) {
        if(Utils.isDoubleClick()) {
            return;
        }

        ViewType viewType = Utils.getViewTypeOfTag(v);
        int position = Utils.getPositionOfTag(v);
        int extraInfo = Utils.getExtraInfoOfTag(v);
        switch (viewType) {
            case VIEW_TYPE_BUY_NOW:
                openGoodsActivity(String.valueOf(extraInfo));
                break;
            default:
                super.onClick(v);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        BottomMenuController.init(getContext(),view);

        mGroupBuyListAdapter = new GroupBuyListAdapter();
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mGroupBuyListAdapter);
        mGroupBuyListAdapter.onClickListener = this;
        mRecyclerView.setEmptyViewClickListener(this);
        mRecyclerView.addOnScrollListener(mOnScrollListener);
        Intent intent = getActivity().getIntent();
        act_id = intent.getStringExtra(Key.KEY_GROUP_BUY_ACT_ID.getValue());
        refresh(act_id);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        startCountDown();
    }

    @Override
    public void onOfflineViewClicked() {
        mGroupBuyListAdapter.data.clear();
        upDataSuccess = false;
        refresh(act_id);
    }

    @Override
    protected void onRequestFailed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_GROUP_BUY_GOODS_LIST:
                mRecyclerView.showOfflineView();
                break;
            default:
                super.onRequestFailed(what, response);
                break;
        }
    }

    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_GROUP_BUY_GOODS_LIST:
                refreshCallback(response);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_group_buy_list;
    }

    public void openGoodsActivity(String goodsId) {
        Intent intent = new Intent();
        intent.putExtra(Key.KEY_GOODS_ID.getValue(), goodsId);
        intent.setClass(getActivity(), GoodsActivity.class);
        startActivity(intent);
    }

    public void refresh(String act_id) {
        CommonRequest mGroupBuyListRequest = new CommonRequest(Api.API_GROUP_BUY_LIST, HttpWhat
                .HTTP_GROUP_BUY_GOODS_LIST.getValue());
        mGroupBuyListRequest.add("act_id", act_id);
        mGroupBuyListRequest.add("go", page);
        addRequest(mGroupBuyListRequest);
    }

    private void loadMore() {
        upDataSuccess = false;
        page++;
        if (page > pageCount) {
            upDataSuccess = false;
            CheckoutDividerModel blankModel = new CheckoutDividerModel();
            mGroupBuyListAdapter.data.add(blankModel);
            mGroupBuyListAdapter.notifyDataSetChanged();
            return;
        } else {
            refresh(act_id);
        }
    }

    private void refreshCallback(String response) {
        upDataSuccess = true;
        mResponseModel = JSON.parseObject(response, Model.class);
        pageCount = mResponseModel.data.page.page_count;

        if (!Utils.isNull(mResponseModel.data.list)) {
            for (GroupBuylistModel groupBuylistModel : mResponseModel.data.list) {
                mGroupBuyListAdapter.data.add(groupBuylistModel);
            }
        } else {
            upDataSuccess = false;
            mRecyclerView.showEmptyView();
        }
        mGroupBuyListAdapter.notifyDataSetChanged();
    }

    private void startCountDown() {
        mCountDownTask = CountDownTask.create();
        mGroupBuyListAdapter.setCountDownTask(mCountDownTask);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        cancelCountDown();
    }

    private void cancelCountDown() {
        mGroupBuyListAdapter.setCountDownTask(null);
        mCountDownTask.cancel();
    }
}
