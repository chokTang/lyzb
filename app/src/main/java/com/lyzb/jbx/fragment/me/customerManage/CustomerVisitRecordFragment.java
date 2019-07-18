package com.lyzb.jbx.fragment.me.customerManage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.like.longshaolib.base.BaseFragment;
import com.like.utilslib.screen.DensityUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.me.customerManage.CustomerVisitRecordAdapter;
import com.lyzb.jbx.model.me.customerManage.CustomerInfoModel;
import com.lyzb.jbx.model.me.customerManage.CustomerVisitRecordModel;
import com.lyzb.jbx.mvp.presenter.me.customerManage.CustomerVisitRecordPresenter;
import com.lyzb.jbx.mvp.view.me.ICustomerVisitRecordView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author wyx
 * @role 访问记录
 * @time 2019 2019/5/8 9:36
 */

public class CustomerVisitRecordFragment extends BaseFragment<CustomerVisitRecordPresenter>
        implements OnRefreshLoadMoreListener, ICustomerVisitRecordView {
    @BindView(R.id.common_refresh_recyclerview)
    RecyclerView mCommonRefreshRecyclerview;
    @BindView(R.id.common_refresh_smartrefreshlayout)
    SmartRefreshLayout mCommonRefreshSmartrefreshlayout;
    @BindView(R.id.common_refresh_notdata)
    View mNotDataView;
    @BindView(R.id.common_refresh_title)
    View mTitleView;
    Unbinder unbinder;

    private CustomerVisitRecordAdapter mAdapter;
    private String mUserId;

    public static CustomerVisitRecordFragment newIntance(String customerUserId) {
        CustomerVisitRecordFragment fragment = new CustomerVisitRecordFragment();
        Bundle bundle = new Bundle();
        bundle.putString(CustomerManageFragment.INTENTKEY_ID, customerUserId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        mTitleView.setVisibility(View.GONE);
        mAdapter = new CustomerVisitRecordAdapter(_mActivity, null);
        mAdapter.setLayoutManager(mCommonRefreshRecyclerview);
        mCommonRefreshRecyclerview.setAdapter(mAdapter);
        mCommonRefreshRecyclerview.setPadding(0, DensityUtil.dpTopx(10), 0, 0);
        mCommonRefreshSmartrefreshlayout.setOnRefreshLoadMoreListener(this);
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        mPresenter.getVisitRecord(true, mUserId);
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
            mUserId = bundle.getString(CustomerManageFragment.INTENTKEY_ID);
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
        mPresenter.getVisitRecord(false, mUserId);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getVisitRecord(true, mUserId);
    }

    @Override
    public void onReFreshSuccess(List<CustomerVisitRecordModel.ListBean> list) {
        mCommonRefreshSmartrefreshlayout.finishRefresh();
        mAdapter.update(list);
        mNotDataView.setVisibility(View.GONE);
        mCommonRefreshRecyclerview.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadMoreSuccess(List<CustomerVisitRecordModel.ListBean> list) {
        if (list.size() < 50) {
            mCommonRefreshSmartrefreshlayout.finishLoadMoreWithNoMoreData();
        } else {
            mCommonRefreshSmartrefreshlayout.finishLoadMore();
        }
        mAdapter.addAll(list);
        mNotDataView.setVisibility(View.GONE);
        mCommonRefreshRecyclerview.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNotData() {
        mCommonRefreshSmartrefreshlayout.finishRefresh();
        mCommonRefreshSmartrefreshlayout.finishLoadMoreWithNoMoreData();
        if (mAdapter.getItemCount() < 1) {
            mNotDataView.setVisibility(View.VISIBLE);
            mCommonRefreshRecyclerview.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFail(String msg) {
        onNotData();
        showToast(msg);
    }

}
