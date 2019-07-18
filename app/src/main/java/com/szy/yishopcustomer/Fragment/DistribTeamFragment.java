package com.szy.yishopcustomer.Fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;
import android.view.ViewGroup;
import android.widget.TextView;

import com.szy.common.Other.CommonRequest;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Adapter.DistribTeamAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Distrib.DistribTeamModel;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;

import butterknife.BindView;
import me.zongren.pullablelayout.Constant.Side;

public class DistribTeamFragment extends YSCBaseFragment {

    private static final int HTTP_WHAT_DISTRIB_TEAM = 1;

    @BindView(R.id.level_one_textView)
    TextView mLevelOne;
    @BindView(R.id.level_two_textView)
    TextView mLevelTwo;
    @BindView(R.id.level_three_textView)
    TextView mLevelThree;

    @BindView(R.id.fragment_distrib_team_recyclerView)
    CommonRecyclerView mRecyclerView;


    private DistribTeamAdapter mAdapter;
    private DistribTeamModel data;

    //默认加载第一页
    private int cur_page = 1;
    private int page_size = 10;

    private int temp = 1;

    private String level = "1";//默认展示全部

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
        mLayoutId = R.layout.fragment_distrib_team;

        mAdapter = new DistribTeamAdapter();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        mLevelOne.setOnClickListener(this);
        mLevelTwo.setOnClickListener(this);
        mLevelThree.setOnClickListener(this);

        mRecyclerView.addOnScrollListener(mOnScrollListener);
        mRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        refresh();
        switchButton(mLevelOne);
        return view;
    }

    @Override
    public void onClick(View view) {
        if (Utils.isDoubleClick()) {
            return;
        }

        switch (view.getId()) {
            case R.id.level_one_textView:
                cur_page = 1;
                level = "1";
                switchButton(mLevelOne);
                refresh();
                break;
            case R.id.level_two_textView:
                cur_page = 1;
                level = "2";
                switchButton(mLevelTwo);
                refresh();
                break;
            case R.id.level_three_textView:
                cur_page = 1;
                level = "3";
                switchButton(mLevelThree);
                refresh();
                break;
            default:
                super.onClick(view);
                break;
        }
    }


    public void refresh() {
        CommonRequest request = new CommonRequest(Api.API_DISTRIB_TEAM, HTTP_WHAT_DISTRIB_TEAM);
        request.add("page[cur_page]", cur_page);
        request.add("page[page_size]", page_size);
        request.add("level", level);
        addRequest(request);
    }

    //接口成功回调
    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (what) {
            case HTTP_WHAT_DISTRIB_TEAM:
                refreshSucceed(response);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    //设置数据
    private void refreshSucceed(String response) {
        HttpResultManager.resolve(response, DistribTeamModel.class, new HttpResultManager
                .HttpResultCallBack<DistribTeamModel>() {
            @Override
            public void onSuccess(DistribTeamModel back) {
                data = back;

                if(temp == 1) {

                    if(back.data.dis_rank.size()==1){
                        mLevelOne.setVisibility(View.VISIBLE);
                        mLevelTwo.setVisibility(View.GONE);
                        mLevelThree.setVisibility(View.GONE);
                    }else if(back.data.dis_rank.size()==2){
                        mLevelOne.setVisibility(View.VISIBLE);
                        mLevelTwo.setVisibility(View.VISIBLE);
                        mLevelThree.setVisibility(View.GONE);
                    }else{
                        mLevelOne.setVisibility(View.VISIBLE);
                        mLevelTwo.setVisibility(View.VISIBLE);
                        mLevelThree.setVisibility(View.VISIBLE);
                    }

                    mLevelOne.setText("一级会员(" + back.data.one_level_user_count + ")");
                    mLevelTwo.setText("二级会员(" + back.data.two_level_user_count + ")");
                    mLevelThree.setText("三级会员(" + back.data.three_level_user_count + ")");

                    temp++;
                }

                if (cur_page == 1) {
                    mAdapter.data.clear();
                    mAdapter.setFooterView(null);
                }

                if (back.data.list.size() > 0) {
                    upDataSuccess = true;
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

    void switchButton(View w) {

        mLevelOne.setSelected(false);
        mLevelTwo.setSelected(false);
        mLevelThree.setSelected(false);

        w.setSelected(true);
    }
}
