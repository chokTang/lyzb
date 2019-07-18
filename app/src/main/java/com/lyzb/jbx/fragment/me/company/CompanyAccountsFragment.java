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
import com.lyzb.jbx.adapter.me.company.CompanyAccountsAdapter;
import com.lyzb.jbx.adapter.me.company.CompanyMembersAdapter;
import com.lyzb.jbx.fragment.me.card.CardFragment;
import com.lyzb.jbx.model.me.CompanyAccountsModel;
import com.lyzb.jbx.model.me.CompanyMembersModel;
import com.lyzb.jbx.model.params.CompanyAccountBody;
import com.lyzb.jbx.model.params.RemoveMembersBody;
import com.lyzb.jbx.mvp.presenter.me.company.CompanyAccountsPersenter;
import com.lyzb.jbx.mvp.view.me.ICompanyAccountsView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.szy.yishopcustomer.Util.App;

import java.util.List;

public class CompanyAccountsFragment extends BaseToolbarFragment<CompanyAccountsPersenter> implements ICompanyAccountsView, OnRefreshLoadMoreListener {
    private static final String PARAMS_ID = "mOrganId";
    public static final int EDIT_CODE = 1011;//
    private String mCompanyId = "";

    SmartRefreshLayout mRefreshLayout;
    RecyclerView mRecyclerView;
    View empty_view;

    private CompanyAccountsAdapter mCompanyAccountsAdapter = null;

    public static CompanyAccountsFragment newIntance(String companyId) {
        CompanyAccountsFragment fragment = new CompanyAccountsFragment();
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
        super.onInitView(savedInstanceState);
        onBack();
        setToolbarTitle("企业帐号");
        mRefreshLayout = findViewById(R.id.sf_company_accountslist);
        mRecyclerView = findViewById(R.id.recy_company_accountslist);

        empty_view = findViewById(R.id.empty_view);

        mRecyclerView.addOnItemTouchListener(new OnRecycleItemClickListener() {
            @Override
            public void onItemChildClick(BaseRecyleAdapter adapter, View view, int position) {
                CompanyAccountsModel.DataBean.ListBean bean = (CompanyAccountsModel.DataBean.ListBean) adapter.getPositionModel(position);
                super.onItemChildClick(adapter, view, position);
                switch (view.getId()) {
                    case R.id.img_item_com_accounts_head:
                        if (App.getInstance().userId.equals(bean.getUserId())) {
                            start(CardFragment.newIntance(1, bean.getUserId()));
                        } else {
                            start(CardFragment.newIntance(2, bean.getUserId()));
                        }
                        break;
                }
            }

            @Override
            public void onItemClick(BaseRecyleAdapter adapter, View view, int position) {
                super.onItemClick(adapter, view, position);
                CompanyAccountsModel.DataBean.ListBean bean = (CompanyAccountsModel.DataBean.ListBean) adapter.getPositionModel(position);
                startForResult(EditCompanyAccountsFragment.newIntance(bean.getCompanyId(), bean.getUserId(), position), EDIT_CODE);
            }
        });
        mRefreshLayout.setOnRefreshLoadMoreListener(this);

    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        mCompanyAccountsAdapter = new CompanyAccountsAdapter(getContext(), null);
        mCompanyAccountsAdapter.setLayoutManager(mRecyclerView);
        mRecyclerView.setAdapter(mCompanyAccountsAdapter);
        mPresenter.getList(true, mCompanyId);
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_company_accounts;
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        switch (requestCode) {
            case EDIT_CODE:
                CompanyAccountBody body = (CompanyAccountBody) data.getSerializable("CompanyMembersBody");
                CompanyAccountsModel.DataBean.ListBean bean = mCompanyAccountsAdapter.getPositionModel(data.getInt("position"));
                bean.setAccountSt(body.getStatus());
                bean.setPosition(body.getPosition());
                mCompanyAccountsAdapter.change(data.getInt("position"), bean);
                break;
        }
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
    public void onList(boolean isRefresh, List<CompanyAccountsModel.DataBean.ListBean> list) {
        if (isRefresh) {
            mRefreshLayout.finishRefresh();
            if (list.size() < 15) {
                mRefreshLayout.finishLoadMoreWithNoMoreData();
            }
            mCompanyAccountsAdapter.update(list);
        } else {
            if (list.size() < 15) {
                mRefreshLayout.finishLoadMoreWithNoMoreData();
            } else {
                mRefreshLayout.finishLoadMore();
            }
            mCompanyAccountsAdapter.addAll(list);
        }
        if (mCompanyAccountsAdapter.getItemCount() == 0) {
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
}
