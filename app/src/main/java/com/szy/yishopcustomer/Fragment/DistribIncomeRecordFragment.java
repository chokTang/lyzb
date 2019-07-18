package com.szy.yishopcustomer.Fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;

import com.szy.common.Other.CommonRequest;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Adapter.DistribIncomeRecordAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Distrib.DistribIncomeRecordModel;
import com.szy.yishopcustomer.Util.HttpResultManager;

import butterknife.BindView;
import me.zongren.pullablelayout.Constant.Side;

/**
 * Created by liwei on 2017/7/19.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class DistribIncomeRecordFragment extends YSCBaseFragment {

    private static final int HTTP_WHAT_DISTRIB_INCOME_RECORD = 1;

    @BindView(R.id.fragment_distrib_income_record_recyclerView)
    CommonRecyclerView mRecyclerView;

    private DistribIncomeRecordAdapter mAdapter;
    private DistribIncomeRecordModel data;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_distrib_income_record;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        mAdapter = new DistribIncomeRecordAdapter();
        mRecyclerView.addOnScrollListener(mOnScrollListener);
        mRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        refresh();
        return view;
    }

    //默认加载第一页
    private int cur_page = 1;
    private int page_size = 10;

    public void loadMore() {
        if (data.data != null && cur_page > data.data.page.page_count) {
            //设置footer
            View footer = LayoutInflater.from(getContext()).inflate(R.layout.fragment_goods_list_item_empty, null);
            mAdapter.setFooterView(footer);
            upDataSuccess = false;
        } else {
            refresh();
        }
    }



    public void refresh() {
        CommonRequest request = new CommonRequest(Api.API_DISTRIB_INCOME_RECORD,
                HTTP_WHAT_DISTRIB_INCOME_RECORD);
        request.add("page[cur_page]", cur_page);
        request.add("page[page_size]", page_size);
        addRequest(request);
    }


    //接口成功回调
    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (what) {
            case HTTP_WHAT_DISTRIB_INCOME_RECORD:
                refreshSucceed(response);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }


    //设置数据
    private void refreshSucceed(String response) {
        HttpResultManager.resolve(response, DistribIncomeRecordModel.class, new HttpResultManager
                .HttpResultCallBack<DistribIncomeRecordModel>() {
            @Override
            public void onSuccess(DistribIncomeRecordModel back) {
                data = back;

                if(cur_page == 1) {
                    mAdapter.data.clear();
                    mAdapter.setFooterView(null);
                }

                if(back.data.list.size() > 0) {
                    mRecyclerView.hideEmptyView();
                    mAdapter.data.addAll(back.data.list);
                } else {
                    mRecyclerView.showEmptyView();
                }
                mAdapter.notifyDataSetChanged();
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
                        if (data != null) {
                            cur_page = data.data.page.cur_page + 1;
                            loadMore();
                        }
                    }
                }
            }
        }
    };

}
