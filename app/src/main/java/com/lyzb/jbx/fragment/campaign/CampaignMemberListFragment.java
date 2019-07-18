package com.lyzb.jbx.fragment.campaign;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener;
import com.like.longshaolib.base.BaseStatusToolbarFragment;
import com.like.utilslib.screen.ScreenUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.campaign.CampaignDetailMoreMemeberAdapter;
import com.lyzb.jbx.fragment.me.card.CardFragment;
import com.lyzb.jbx.model.campagin.CampaginDetailUserModel;
import com.lyzb.jbx.model.campagin.CampaginUserListModel;
import com.lyzb.jbx.mvp.presenter.campaign.CampaignMemberPresenter;
import com.lyzb.jbx.mvp.view.campaign.ICampaignMemberView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

/**
 * 参与活动的报名人员列表
 *
 * @author longshao
 */
public class CampaignMemberListFragment extends BaseStatusToolbarFragment<CampaignMemberPresenter> implements ICampaignMemberView,
        OnRefreshLoadMoreListener {

    private String mCampaignId;
    private final static String PARAMS_ID = "campaignId";

    private SmartRefreshLayout refresh_member;
    private RecyclerView recycle_member;
    private CampaignDetailMoreMemeberAdapter memeberAdapter;

    public static CampaignMemberListFragment newIntance(String campaignId) {
        CampaignMemberListFragment fragment = new CampaignMemberListFragment();
        Bundle args = new Bundle();
        args.putString(PARAMS_ID, campaignId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mCampaignId = args.getString(PARAMS_ID);
        }
    }

    @Override
    public Integer getMainResId() {
        return R.layout.fragment_campaign_member;
    }

    @Override
    public void onLazyRequest() {
        mPresenter.getMemberList(true, mCampaignId);
    }

    @Override
    public void onAgainRequest() {
        mPresenter.getMemberList(true, mCampaignId);
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        super.onInitView(savedInstanceState);
        onBack();

        refresh_member = (SmartRefreshLayout) main_view;
        recycle_member = findViewById(R.id.recycle_member);

        refresh_member.setOnRefreshLoadMoreListener(this);
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        int width = ScreenUtil.getScreenWidth() / 3;
        memeberAdapter = new CampaignDetailMoreMemeberAdapter(getContext(), width, null);
        memeberAdapter.setGridLayoutManager(recycle_member, 3);
        recycle_member.setAdapter(memeberAdapter);

        recycle_member.addOnItemTouchListener(new OnRecycleItemClickListener() {
            @Override
            public void onItemClick(BaseRecyleAdapter adapter, View view, int position) {
                //跳转到个人名片页面
                start(CardFragment.newIntance(2, memeberAdapter.getPositionModel(position).getUserId()));
            }
        });
    }

    @Override
    public void onCampaignMember(boolean isfresh, int totalNumber, List<CampaginUserListModel> list) {
        setToolbarTitle(String.format("报名列表(%d)", totalNumber));
        if (isfresh) {
            refresh_member.finishRefresh();
            memeberAdapter.update(list);
            if (list.size() < 10) {
                refresh_member.finishLoadMoreWithNoMoreData();
            }
        } else {
            memeberAdapter.addAll(list);
            if (list.size() < 10) {
                refresh_member.finishLoadMoreWithNoMoreData();
            } else {
                refresh_member.finishLoadMore();
            }
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getMemberList(false, mCampaignId);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getMemberList(true, mCampaignId);
    }
}
