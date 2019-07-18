package com.szy.yishopcustomer.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.szy.common.Other.CommonRequest;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Activity.YSCWebViewActivity;
import com.szy.yishopcustomer.Adapter.DistribHelpAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Distrib.DistribHelpModel;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;

import butterknife.BindView;
import me.zongren.pullablelayout.Constant.Side;

public class DistribHelpFragment extends YSCBaseFragment {

    private static final int HTTP_WHAT_DISTRIB_HELP = 1;


    @BindView(R.id.fragment_distrib_help_recyclerView)
    CommonRecyclerView mRecyclerView;
    @BindView(R.id.linearlayout_empty)
    LinearLayout emptyLayout;
    @BindView(R.id.view)
    View lineView;


    private DistribHelpAdapter mAdapter;
    private DistribHelpModel data;

    //默认加载第一页
    private int cur_page = 1;
    private int page_size = 10;

    public void loadMore() {
        if (data.data != null && cur_page > data.data.page.page_count) {
            //设置footer
            View footer = LayoutInflater.from(getContext()).inflate(R.layout
                    .fragment_goods_list_item_empty, null);
            mAdapter.setFooterView(footer);
            upDataSuccess = false;
        } else {
            refresh();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_distrib_help;

        mAdapter = new DistribHelpAdapter();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        mRecyclerView.addOnScrollListener(mOnScrollListener);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.onClickListener = this;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        refresh();

        return view;
    }

    @Override
    public void onClick(View view) {
        if (Utils.isDoubleClick()) {
            return;
        }

        ViewType viewType = Utils.getViewTypeOfTag(view);
        int position = Utils.getPositionOfTag(view);
        switch (viewType) {
            case VIEW_TYPE_DETAIL:
                DistribHelpModel.DataBean.ListModel listModel = (DistribHelpModel.DataBean.ListModel)mAdapter.data.get(position);
                openDistribHelpArticle(listModel.article_id);
                break;
            default:
                super.onClick(view);
                break;
        }
    }


    public void refresh() {
        CommonRequest request = new CommonRequest(Api.API_DISTRIB_HELP, HTTP_WHAT_DISTRIB_HELP);
        request.add("page[cur_page]", cur_page);
        request.add("page[page_size]", page_size);
        addRequest(request);
    }

    //接口成功回调
    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (what) {
            case HTTP_WHAT_DISTRIB_HELP:
                refreshSucceed(response);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    //设置数据
    private void refreshSucceed(String response) {
        HttpResultManager.resolve(response, DistribHelpModel.class, new HttpResultManager
                .HttpResultCallBack<DistribHelpModel>() {
            @Override
            public void onSuccess(DistribHelpModel back) {
                data = back;

                if (cur_page == 1) {
                    mAdapter.data.clear();
                    mAdapter.setFooterView(null);
                }

                if (back.data.list.size() > 0) {
                    upDataSuccess = true;
                    emptyLayout.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    lineView.setVisibility(View.VISIBLE);
                    mAdapter.data.addAll(back.data.list);
                } else {
                    emptyLayout.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.GONE);
                    lineView.setVisibility(View.GONE);
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

    private void openDistribHelpArticle(String id){

        Intent intent = new Intent(getContext(), YSCWebViewActivity.class);
        intent.putExtra(Key.KEY_URL.getValue(), Api.API_ARTICLE + id);
        startActivity(intent);
    }
}
