package com.lyzb.jbx.fragment.home.first;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.DividerItemDecoration;
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener;
import com.like.longshaolib.base.BaseStatusFragment;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.campaign.HomeCampaginListAdapter;
import com.lyzb.jbx.fragment.campaign.CampaignDetailFragment;
import com.lyzb.jbx.model.campagin.CampaignModel;
import com.lyzb.jbx.mvp.presenter.home.first.HomeCampaignPresenter;
import com.lyzb.jbx.mvp.view.home.first.IHomeCampaignView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

/**
 * 首页-活动页面
 */
public class HomeCampaignFragment extends BaseStatusFragment<HomeCampaignPresenter> implements IHomeCampaignView,
        OnRefreshLoadMoreListener {

    private SmartRefreshLayout refresh_campaign;
    private RecyclerView recycle_campaign_list;

    private HomeCampaginListAdapter mAdapter;

    @Override
    public Integer getMainResId() {
        return R.layout.fragment_campaign_home;
    }

    @Override
    public void onLazyRequest() {
        mPresenter.getList(true);
    }

    @Override
    public void onAgainRequest() {
        mPresenter.getList(true);
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        super.onInitView(savedInstanceState);
        refresh_campaign = (SmartRefreshLayout) main_view;
        recycle_campaign_list = findViewById(R.id.recycle_campaign_list);

        refresh_campaign.setOnRefreshLoadMoreListener(this);
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        mAdapter = new HomeCampaginListAdapter(getContext(), null);
        mAdapter.setLayoutManager(recycle_campaign_list);
        recycle_campaign_list.addItemDecoration(new DividerItemDecoration(DividerItemDecoration.VERTICAL_LIST, R.drawable.listdivider_window_10));
        recycle_campaign_list.setAdapter(mAdapter);

        recycle_campaign_list.addOnItemTouchListener(new OnRecycleItemClickListener() {
            @Override
            public void onItemClick(BaseRecyleAdapter adapter, View view, int position) {
                childDoubleStart(CampaignDetailFragment.newIntance(mAdapter.getPositionModel(position).getId()));
            }
        });
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getList(false);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getList(true);
    }

    @Override
    public void onGetCampaignResult(boolean isRefrsh, List<CampaignModel> list) {
        if (isRefrsh) {
            refresh_campaign.finishRefresh();
            mAdapter.update(list);
            if (list.size() < 10) {
                refresh_campaign.finishLoadMoreWithNoMoreData();
            }
        } else {
            mAdapter.addAll(list);
            if (list.size() < 10) {
                refresh_campaign.finishLoadMoreWithNoMoreData();
            } else {
                refresh_campaign.finishLoadMore();
            }
        }
    }

    @Override
    public boolean isDelayedData() {
        return false;
    }
}
