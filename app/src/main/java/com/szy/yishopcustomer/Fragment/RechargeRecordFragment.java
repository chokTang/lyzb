package com.szy.yishopcustomer.Fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.TextView;

import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Adapter.RechargeRecordAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.ViewType;
import com.szy.yishopcustomer.Other.BottomMenuController;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.DividerModel;
import com.szy.yishopcustomer.ResponseModel.RechargeRecord.ListModel;
import com.szy.yishopcustomer.ResponseModel.RechargeRecord.Model;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;

import butterknife.BindView;
import me.zongren.pullablelayout.Constant.Result;
import me.zongren.pullablelayout.Constant.Side;
import me.zongren.pullablelayout.Inteface.OnPullListener;
import me.zongren.pullablelayout.Main.PullableComponent;
import me.zongren.pullablelayout.Main.PullableLayout;

/**
 * Created by liwei on 17/5/5.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class RechargeRecordFragment extends YSCBaseFragment implements OnPullListener {

    @BindView(R.id.fragment_recharge_record_all)
    TextView mAllTextView;
    @BindView(R.id.fragment_recharge_record_paid)
    TextView mPaidTextView;
    @BindView(R.id.fragment_recharge_record_not_paid)
    TextView mNotPaidTextView;
    @BindView(R.id.fragment_recharge_record_recyclerView)
    CommonRecyclerView mRecyclerView;
    @BindView(R.id.fragment_rechage_record_pullablelayout)
    PullableLayout mPullableLayout;

    private LinearLayoutManager mLayoutManager;
    private RechargeRecordAdapter mAdapter;

    private String rechargeType = "all";
    private int page = 1;
    private int pageCount;
    private boolean upDataSuccess = true;

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
        mLayoutId = R.layout.fragment_recharge_record;
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        BottomMenuController.init(getContext(),view);

        Utils.setViewTypeForTag(mAllTextView, ViewType.VIEW_ALL);
        Utils.setViewTypeForTag(mPaidTextView, ViewType.VIEW_PAID);
        Utils.setViewTypeForTag(mNotPaidTextView, ViewType.VIEW_NO_PAID);
        mAllTextView.setOnClickListener(this);
        mPaidTextView.setOnClickListener(this);
        mNotPaidTextView.setOnClickListener(this);

        colorSelect(mAllTextView, mPaidTextView, mNotPaidTextView);

        mAdapter = new RechargeRecordAdapter();
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.onClickListener = this;
        mRecyclerView.addOnScrollListener(mOnScrollListener);
        mPullableLayout.topComponent.setOnPullListener(this);

        refresh();

        return view;
    }

    @Override
    public void onClick(View view) {
        ViewType viewType = Utils.getViewTypeOfTag(view);
        int position = Utils.getPositionOfTag(view);
        switch (viewType) {
            case VIEW_ALL:
                colorSelect(mAllTextView, mPaidTextView, mNotPaidTextView);
                rechargeType = "all";
                page = 1;
                mAdapter.mData.clear();
                refresh();
                break;
            case VIEW_PAID:
                colorSelect(mPaidTextView, mAllTextView, mNotPaidTextView);
                rechargeType = "paid";
                page = 1;
                mAdapter.mData.clear();
                refresh();
                break;
            case VIEW_NO_PAID:
                colorSelect(mNotPaidTextView, mAllTextView, mPaidTextView);
                rechargeType = "not_paid";
                page = 1;
                mAdapter.mData.clear();
                refresh();
                break;
            default:
                super.onClick(view);
        }
    }

    @Override
    public void onEvent(CommonEvent event) {
        switch (EventWhat.valueOf(event.getWhat())) {
            case EVENT_LOGIN:

                break;
            default:
                super.onEvent(event);
                break;
        }
    }

    @Override
    public void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_RECHARGE:
                refreshCallback(response);
                break;
            default:
                super.onRequestSucceed(what, response);
        }
    }

    public void refresh() {
        CommonRequest request = new CommonRequest(Api.API_RECHARGE_RECORD, HttpWhat.HTTP_RECHARGE
                .getValue());
        request.add("pay_type", rechargeType);
        request.add("page[page_size]", "10");
        request.add("page[cur_page]", page);
        addRequest(request);
    }

    private void refreshCallback(String response) {
        upDataSuccess = true;
        mPullableLayout.topComponent.finish(Result.SUCCEED);
        HttpResultManager.resolve(response, Model.class, new HttpResultManager.HttpResultCallBack<Model>() {
            @Override
            public void onSuccess(Model model) {
                if(model.data.list.size()!=0) {
                    mRecyclerView.hideEmptyView();
                    for (ListModel listModel : model.data.list) {
                        mAdapter.mData.add(listModel);
                    }
                }else{
                    mRecyclerView.showEmptyView();
                }
                pageCount = model.data.page.page_count;
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    public void colorSelect(TextView t1, TextView t2, TextView t3) {
        t1.setSelected(true);
        t2.setSelected(false);
        t3.setSelected(false);
    }

    private void changeTab(String type) {
        refresh();
    }

    private void loadMore() {
        upDataSuccess = false;
        page++;
        if (page > pageCount) {
            upDataSuccess = false;
            DividerModel blankModel = new DividerModel();
            mAdapter.mData.add(blankModel);
            mAdapter.notifyDataSetChanged();
            return;
        } else {
            refresh();
        }
    }

    @Override
    public void onCanceled(PullableComponent pullableComponent) {

    }

    @Override
    public void onLoading(PullableComponent pullableComponent) {
        page = 1;
        mAdapter.mData.clear();
        refresh();
    }

    @Override
    public void onSizeChanged(PullableComponent pullableComponent, int mSize) {

    }
}
