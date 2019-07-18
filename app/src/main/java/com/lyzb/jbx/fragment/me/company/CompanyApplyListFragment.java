package com.lyzb.jbx.fragment.me.company;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.DividerItemDecoration;
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener;
import com.like.longshaolib.base.BaseToolbarFragment;
import com.lyzb.jbx.R;
import com.lyzb.jbx.activity.GoodToMeActivity;
import com.lyzb.jbx.adapter.me.company.CompanyApplyListAdapter;
import com.lyzb.jbx.fragment.me.card.CardFragment;
import com.lyzb.jbx.model.me.ApplyListModel;
import com.lyzb.jbx.model.me.CompanyApplyListModel;
import com.lyzb.jbx.model.params.AuditMembersBody;
import com.lyzb.jbx.mvp.presenter.me.company.CompanyApplyListPersenter;
import com.lyzb.jbx.mvp.view.me.ICompanyApplyListView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

public class CompanyApplyListFragment extends BaseToolbarFragment<CompanyApplyListPersenter> implements ICompanyApplyListView, OnRefreshLoadMoreListener {

    private static final String PARAMS_ID = "mOrganId";
    private String mCompanyId = "";


    SmartRefreshLayout mRefreshLayout;
    RecyclerView mRecyclerView;
    View empty_view;

    private CompanyApplyListAdapter mCompanyApplyListAdapter = null;


    public static CompanyApplyListFragment newIntance(String companyId) {
        CompanyApplyListFragment fragment = new CompanyApplyListFragment();
        Bundle args = new Bundle();
        args.putString(PARAMS_ID, companyId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mCompanyId = args.getString(PARAMS_ID);
        }
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        super.onInitView(savedInstanceState);
        onBack();
        setToolbarTitle("申请列表");

        mRefreshLayout = findViewById(R.id.sf_company_applist);
        mRecyclerView = findViewById(R.id.recy_company_applist);

        empty_view = findViewById(R.id.empty_view);

        mRecyclerView.addOnItemTouchListener(new OnRecycleItemClickListener() {
            @Override
            public void onItemClick(BaseRecyleAdapter adapter, View view, int position) {
                super.onItemClick(adapter, view, position);
                //他的名片
                CompanyApplyListModel.DataBean.ListBean bean = (CompanyApplyListModel.DataBean.ListBean) adapter.getPositionModel(position);
                start(CardFragment.newIntance(2, bean.getUserId()));
            }

            @Override
            public void onItemChildClick(BaseRecyleAdapter adapter, View view, int position) {
                super.onItemChildClick(adapter, view, position);
                CompanyApplyListModel.DataBean.ListBean bean = (CompanyApplyListModel.DataBean.ListBean) adapter.getPositionModel(position);
                AuditMembersBody auditMembersBody = new AuditMembersBody();
                auditMembersBody.setApplyId(bean.getApplyId());
                switch (view.getId()) {
                    case R.id.tv_item_company_agreed://同意
                        start(AddStaffFragment.Companion.newIntance(AddStaffFragment.TYPE_APPLY, mCompanyId, bean.getUserId(), bean.getApplyId()));
                        break;
                    case R.id.tv_item_company_cancle://驳回
                        auditMembersBody.setAuditState(2);
                        mPresenter.onAudit(auditMembersBody, position);
                        break;
                }
            }
        });
        mRefreshLayout.setOnRefreshLoadMoreListener(this);

        mCompanyApplyListAdapter = new CompanyApplyListAdapter(getContext(), null);
        mCompanyApplyListAdapter.setLayoutManager(mRecyclerView);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(DividerItemDecoration.VERTICAL_LIST, R.drawable.listdivider_window_10));
        mRecyclerView.setAdapter(mCompanyApplyListAdapter);
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public Object getResId() {
        return R.layout.fragment_company_apply_list;
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        mPresenter.getList(true, mCompanyId);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getList(false, mCompanyId);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getList(true, mCompanyId);
    }

    @Override
    public void onApplyList(boolean isRefresh, List<CompanyApplyListModel.DataBean.ListBean> list) {
        if (isRefresh) {
            mRefreshLayout.finishRefresh();
            if (list.size() < 15) {
                mRefreshLayout.finishLoadMoreWithNoMoreData();
            }
            mCompanyApplyListAdapter.update(list);
        } else {
            if (list.size() < 15) {
                mRefreshLayout.finishLoadMoreWithNoMoreData();
            } else {
                mRefreshLayout.finishLoadMore();
            }
            mCompanyApplyListAdapter.addAll(list);
        }

        if (mCompanyApplyListAdapter.getItemCount() == 0) {
            empty_view.setVisibility(View.VISIBLE);
        } else {
            empty_view.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFinshOrLoadMore(boolean isRefrsh) {
        if (isRefrsh) {
            mRefreshLayout.finishRefresh();
        } else {
            mRefreshLayout.finishLoadMore();
        }
    }

    @Override
    public void onAudit(int auditState, int position) {
        CompanyApplyListModel.DataBean.ListBean model = mCompanyApplyListAdapter.getPositionModel(position);
        model.setAuditState(auditState);
        mCompanyApplyListAdapter.change(position, model);
    }
}
