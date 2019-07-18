package com.lyzb.jbx.fragment.me.customerManage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener;
import com.like.longshaolib.base.BaseFragment;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.me.customerManage.CustomerManageCompanyAdapter;
import com.lyzb.jbx.model.me.customerManage.CustomerManageCompanyModel;
import com.lyzb.jbx.mvp.presenter.me.customerManage.CustomerManageCompanyPresenter;
import com.lyzb.jbx.mvp.view.me.ICustomerManageCompanyView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author wyx
 * @role 客户管理-企业
 * @time 2019 2019/5/10 10:14
 */

public class CustomerManageCompanyFragment extends BaseFragment<CustomerManageCompanyPresenter>
        implements OnRefreshLoadMoreListener, ICustomerManageCompanyView {
    @BindView(R.id.statistics_title_tv)
    TextView mStatisticsTitleTv;
    @BindView(R.id.common_refresh_recyclerview)
    RecyclerView mCommonRefreshRecyclerview;
    @BindView(R.id.common_refresh_smartrefreshlayout)
    SmartRefreshLayout mCommonRefreshSmartrefreshlayout;
    @BindView(R.id.common_refresh_notdata)
    View mNotDataView;
    Unbinder unbinder;

    private String mcompanyId;
    private CustomerManageCompanyAdapter mAdapter;

    public static CustomerManageCompanyFragment newIntance(String companyId) {
        CustomerManageCompanyFragment fragment = new CustomerManageCompanyFragment();
        Bundle bundle = new Bundle();
        bundle.putString(CustomerManageFragment.INTENTKEY_ID, companyId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        mStatisticsTitleTv.setText("客户管理");
        mAdapter = new CustomerManageCompanyAdapter(_mActivity, null);
        mAdapter.setLayoutManager(mCommonRefreshRecyclerview);
        mCommonRefreshRecyclerview.setAdapter(mAdapter);
        mCommonRefreshRecyclerview.addOnItemTouchListener(new OnRecycleItemClickListener() {
            @Override
            public void onItemClick(BaseRecyleAdapter adapter, View view, int position) {
                super.onItemClick(adapter, view, position);
                //进入客户详情
                String id = mAdapter.getList().get(position).getUserId();
                String accountName = mAdapter.getList().get(position).getAccountName();
                String userName = mAdapter.getList().get(position).getUserName();
                if (TextUtils.isEmpty(userName)) {
                    start(CustomerManageFragment.newIntance(id, accountName));
                } else {
                    //使用者姓名   后台返回的数据是加了括号的，这里要去掉
                    userName = userName.substring(1, userName.length());
                    userName = userName.substring(0, userName.length() - 1);
                    start(CustomerManageFragment.newIntance(id, userName));
                }
            }

        });

        mCommonRefreshSmartrefreshlayout.setOnRefreshLoadMoreListener(this);
        mPresenter.getData(true, mcompanyId);
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
    }

    @Override
    public Object getResId() {
        return R.layout.layout_common_refresh;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mcompanyId = bundle.getString(CustomerManageFragment.INTENTKEY_ID);
        }
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getData(false, mcompanyId);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getData(true, mcompanyId);
    }

    @Override
    public void onLoadMore(CustomerManageCompanyModel.DataBean b) {
        if (b.getList().size() < 10) {
            mCommonRefreshSmartrefreshlayout.finishLoadMoreWithNoMoreData();
        } else {
            mCommonRefreshSmartrefreshlayout.finishLoadMore();
        }
        mAdapter.addAll(b.getList());
        mCommonRefreshRecyclerview.setVisibility(View.VISIBLE);
        mNotDataView.setVisibility(View.GONE);
    }

    @Override
    public void onReFresh(CustomerManageCompanyModel.DataBean b) {
        mAdapter.update(b.getList());
        mCommonRefreshRecyclerview.setVisibility(View.VISIBLE);
        mNotDataView.setVisibility(View.GONE);
    }

    @Override
    public void onNotData() {
        mCommonRefreshSmartrefreshlayout.finishRefresh();
        mCommonRefreshSmartrefreshlayout.finishLoadMoreWithNoMoreData();
        if (mAdapter.getItemCount() < 1) {
            mCommonRefreshRecyclerview.setVisibility(View.GONE);
            mNotDataView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onFail(String msg) {
        onNotData();
        showToast(msg);
    }


    @OnClick(R.id.statistics_back_iv)
    public void onViewClicked() {
        pop();
    }
}
