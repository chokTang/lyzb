package com.szy.yishopcustomer.Fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szy.common.Other.CommonRequest;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Adapter.RecommendListAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.ViewModel.RecommendListModel;

import butterknife.BindView;
import me.zongren.pullablelayout.Constant.Result;
import me.zongren.pullablelayout.Constant.Side;
import me.zongren.pullablelayout.Inteface.OnPullListener;
import me.zongren.pullablelayout.Main.PullableComponent;
import me.zongren.pullablelayout.Main.PullableLayout;

/**
 * Created by liwei on 2017/7/14.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class RecommendFragment extends YSCBaseFragment implements OnPullListener {

    private static final int HTTP_WHAT_RECOMMEND = 1;

    @BindView(R.id.fragment_recommend_user_count)
    TextView mUserCount;
    @BindView(R.id.fragmen_recommend_user_total_bonus)
    TextView mUserTotalBonus;
    @BindView(R.id.fragment_recommend_bonus_layout)
    LinearLayout mBonusLayout;

    @BindView(R.id.fragment_recommend_listView)
    CommonRecyclerView mRecyclerView;
    @BindView(R.id.fragment_recommend_pullableLayout)
    PullableLayout mPullableLayout;

    private RecommendListAdapter mAdapter;
    private RecommendListModel data;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_recommend;

        mAdapter = new RecommendListAdapter();
        mAdapter.onClickListener = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        mRecyclerView.addOnScrollListener(mOnScrollListener);
        mRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mPullableLayout.topComponent.setOnPullListener(this);
        refresh();
        return view;
    }

    //默认加载第一页
    private int cur_page = 1;
    private int page_size = 10;

    public void loadMore() {
        if (data != null && cur_page > data.data.page.page_count) {
            upDataSuccess = false;
            //设置footer
            View footer = LayoutInflater.from(getContext()).inflate(R.layout
                    .fragment_goods_list_item_empty, null);
            mAdapter.setFooterView(footer);
        } else {
            CommonRequest request = new CommonRequest(Api.API_USER_RECOMMEND, HTTP_WHAT_RECOMMEND);
            request.add("page[cur_page]", cur_page);
            request.add("page[page_size]", page_size);
            addRequest(request);
        }
    }

    public void refresh() {
        cur_page = 1;
        CommonRequest request = new CommonRequest(Api.API_USER_RECOMMEND, HTTP_WHAT_RECOMMEND);
        request.add("page[cur_page]", cur_page);
        request.add("page[page_size]", page_size);
        addRequest(request);
    }


    //接口成功回调
    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (what) {
            case HTTP_WHAT_RECOMMEND:
                refreshSucceed(response);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }


    //设置数据
    private void refreshSucceed(String response) {
        HttpResultManager.resolve(response, RecommendListModel.class, new HttpResultManager
                .HttpResultCallBack<RecommendListModel>() {
            @Override
            public void onSuccess(RecommendListModel back) {
                upDataSuccess = true;
                data = back;
                if (cur_page == 1) {
                    mAdapter.data.clear();
                    mAdapter.setFooterView(null);
                }

                mUserCount.setText(data.data.user_count + "个");

                if (back.data.is_bonus_reward) {
                    mBonusLayout.setVisibility(View.VISIBLE);
                    mUserTotalBonus.setText(data.data.user_total_bonus + "元");
                } else {
                    mBonusLayout.setVisibility(View.GONE);
                }


                if (back.data.list.size() > 0) {
                    mRecyclerView.hideEmptyView();
                    mAdapter.data.addAll(back.data.list);
                } else {
                    upDataSuccess = false;
                    mRecyclerView.showEmptyView();
                }
                mAdapter.notifyDataSetChanged();
                mPullableLayout.topComponent.finish(Result.SUCCEED);
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
}
