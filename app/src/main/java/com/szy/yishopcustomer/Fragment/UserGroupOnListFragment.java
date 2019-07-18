package com.szy.yishopcustomer.Fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.TextView;

import com.szy.common.Interface.OnEmptyViewClickListener;
import com.szy.common.Other.CommonRequest;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Activity.GoodsActivity;
import com.szy.yishopcustomer.Activity.GroupOnListActivity;
import com.szy.yishopcustomer.Activity.OrderDetailActivity;
import com.szy.yishopcustomer.Activity.UserGroupOnDetailActivity;
import com.szy.yishopcustomer.Adapter.UserGroupOnListAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.ViewType;
import com.szy.yishopcustomer.Other.BottomMenuController;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Checkout.CheckoutDividerModel;
import com.szy.yishopcustomer.ResponseModel.SpellGroupListModel.ListModel;
import com.szy.yishopcustomer.ResponseModel.SpellGroupListModel.Model;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import me.zongren.pullablelayout.Constant.Result;
import me.zongren.pullablelayout.Constant.Side;
import me.zongren.pullablelayout.Inteface.OnPullListener;
import me.zongren.pullablelayout.Main.PullableComponent;
import me.zongren.pullablelayout.Main.PullableLayout;

/**
 * Created by liwei on 2017/3/20.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class UserGroupOnListFragment extends YSCBaseFragment  implements OnEmptyViewClickListener, OnPullListener {

    @BindView(R.id.fragment_user_groupon_list_recyclerView)
    CommonRecyclerView mRecyclerView;
    @BindView(R.id.fragment_groupon_list_all_textView)
    TextView mAllTextView;
    @BindView(R.id.fragment_groupon_list_active_textView)
    TextView mActiveTextView;
    @BindView(R.id.fragment_groupon_list_success_textView)
    TextView mSuccessTextView;
    @BindView(R.id.fragment_groupon_list_fail_textView)
    TextView mFailTextView;
    @BindView(R.id.fragment_user_group_on_pullAbleLayout)
    PullableLayout mPullableLayout;

    private boolean upDataSuccess = true;
    private int pageCount;
    private int pageSize = 5;
    private int curPage = 1;
    private String grouponType = "groupon_all";

    private ArrayList<Object> mList;
    private UserGroupOnListAdapter mAdapter;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_user_group_on_list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        BottomMenuController.init(getContext(),v);

        mRecyclerView.addOnScrollListener(mOnScrollListener);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new UserGroupOnListAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter.onClickListener = this;
        mRecyclerView.setEmptyViewClickListener(this);
        mPullableLayout.topComponent.setOnPullListener(this);

        Utils.setViewTypeForTag(mAllTextView, ViewType.VIEW_TYPE_ALL_GROUPON);
        mAllTextView.setOnClickListener(this);
        Utils.setViewTypeForTag(mActiveTextView, ViewType.VIEW_TYPE_ACTIVE_GROUPON);
        mActiveTextView.setOnClickListener(this);
        Utils.setViewTypeForTag(mSuccessTextView, ViewType.VIEW_TYPE_SUCCESS_GROUPON);
        mSuccessTextView.setOnClickListener(this);
        Utils.setViewTypeForTag(mFailTextView, ViewType.VIEW_TYPE_FAIL_GROUPON);
        mFailTextView.setOnClickListener(this);


        final int topMargin = Utils.dpToPx(getContext(), 10);
        RecyclerView.ItemDecoration mItemDecoration = new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView
                    .State state) {
                super.getItemOffsets(outRect, view, parent, state);
                final int itemPosition = parent.getChildAdapterPosition(view);
                outRect.top = topMargin;
            }
        };
        mRecyclerView.addItemDecoration(mItemDecoration);

        mList = new ArrayList<Object>();

        colorSelect(mAllTextView);
        refresh();
        return v;
    }

    @Override
    public void onEmptyViewClicked() {
        goGroupOn();
    }

    @Override
    public void onClick(View v) {
        if (Utils.isDoubleClick()) {
            return;
        }
        ViewType viewType = Utils.getViewTypeOfTag(v);
        int position = Utils.getPositionOfTag(v);
        int extraInfo = Utils.getExtraInfoOfTag(v);
        switch (viewType) {
            case VIEW_TYPE_ORDER:
                ListModel listModel = (ListModel) mAdapter.data.get(position);
                openOrderDetail(listModel.order_id);
                break;
            case VIEW_TYPE_USER_GROUPON_DETAIL:
                ListModel listModel2 = (ListModel) mAdapter.data.get(position);
                openUserGroupOnDetail(listModel2.group_sn, listModel2.status, "0");
                break;
            case VIEW_TYPE_USER_GROUPON_DETAIL_SHARE:
                ListModel listModel3 = (ListModel) mAdapter.data.get(position);
                openUserGroupOnDetail(listModel3.group_sn, listModel3.status, "1");
                break;
            case VIEW_TYPE_ACTIVE_GROUPON:
                curPage = 1;
                grouponType = "groupon_active";
                refresh();
                colorSelect(mActiveTextView);
                break;
            case VIEW_TYPE_SUCCESS_GROUPON:
                curPage = 1;
                grouponType = "groupon_success";
                refresh();
                colorSelect(mSuccessTextView);
                break;
            case VIEW_TYPE_FAIL_GROUPON:
                curPage = 1;
                grouponType = "groupon_fail";
                refresh();
                colorSelect(mFailTextView);
                break;
            case VIEW_TYPE_ALL_GROUPON:
                curPage = 1;
                grouponType = "groupon_all";
                refresh();
                colorSelect(mAllTextView);
                break;
            case VIEW_TYPE_GOODS:
                openGoodsActivity(String.valueOf(extraInfo));
                break;
            default:
                super.onClick(v);
        }
    }

    @Override
    public void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_USER_GROUP_0N_LSIT:
                refreshCallback(response);
                break;

            default:
                super.onRequestSucceed(what, response);
        }
    }

    public void openGoodsActivity(String skuId) {
        Intent intent = new Intent();
        intent.putExtra(Key.KEY_SKU_ID.getValue(), skuId);
        intent.setClass(getActivity(), GoodsActivity.class);
        startActivity(intent);
    }

    public void refresh() {
        CommonRequest request = new CommonRequest(Api.API_USER_GROUP_ON_LIST, HttpWhat
                .HTTP_USER_GROUP_0N_LSIT.getValue());
        request.add("status", grouponType);
        request.add("page[page_size]", pageSize);
        request.add("page[cur_page]", curPage);
        addRequest(request);
    }

    private void refreshCallback(String response){
        upDataSuccess = true;
        HttpResultManager.resolve(response, Model.class, new HttpResultManager.HttpResultCallBack<Model>() {
            @Override
            public void onSuccess(Model model) {
                mPullableLayout.topComponent.finish(Result.SUCCEED);
                pageCount = model.data.page.page_count;

                if(curPage == 1){
                    mList.clear();
                }

                if(!Utils.isNull(model.data.list)) {
                    mRecyclerView.hideEmptyView();
                    mList.addAll(model.data.list);
                    mAdapter.setData(mList);
                    mAdapter.notifyDataSetChanged();
                }else{
                    upDataSuccess = false;
                    mRecyclerView.showEmptyView();
                }
            }
        });
    }

    private void openUserGroupOnDetail(String groupSn, int status, String share) {
        Intent intent = new Intent();
        intent.putExtra(Key.KEY_GROUP_SN.getValue(), groupSn);
        intent.putExtra(Key.KEY_GROUPON_STATUS.getValue(), status);
        if (share.equals("1")) {
            intent.putExtra(Key.KEY_BOOLEAN.getValue(), share);
        }
        intent.setClass(getActivity(), UserGroupOnDetailActivity.class);
        startActivity(intent);
    }
    private void openOrderDetail(String orderId) {
        Intent intent = new Intent();
        intent.putExtra(Key.KEY_ORDER_ID.getValue(), orderId);
        intent.setClass(getActivity(), OrderDetailActivity.class);
        startActivity(intent);
    }

    public void colorSelect(TextView t) {
        mAllTextView.setSelected(false);
        mActiveTextView.setSelected(false);
        mSuccessTextView.setSelected(false);
        mFailTextView.setSelected(false);
        t.setSelected(true);
    }

    private void loadMore() {
        upDataSuccess = false;
        curPage++;
        if (curPage > pageCount) {
            upDataSuccess = false;
            mList.add(new CheckoutDividerModel());
            mAdapter.setData(mList);
            mAdapter.notifyDataSetChanged();
            return;
        } else {
            refresh();
        }
    }

    private void goGroupOn(){
        Intent intent = new Intent();
        intent.setClass(getActivity(), GroupOnListActivity.class);
        startActivity(intent);
    }

    @Override
    public void onCanceled(PullableComponent pullableComponent) {

    }

    @Override
    public void onLoading(PullableComponent pullableComponent) {
        curPage = 1;
        refresh();
    }

    @Override
    public void onSizeChanged(PullableComponent pullableComponent, int mSize) {

    }
}
