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

import com.like.longshaolib.base.BaseFragment;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.me.customerManage.CustomerTrackRecordAdapter;
import com.lyzb.jbx.model.me.customerManage.CustomerInfoModel;
import com.lyzb.jbx.model.me.customerManage.CustomerTrakRecordModel;
import com.lyzb.jbx.mvp.presenter.me.customerManage.CustomerTrackRecordPresenter;
import com.lyzb.jbx.mvp.view.me.ICustomerTrackRecordView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author wyx
 * @role 跟进记录
 * @time 2019 2019/5/8 9:35
 */

public class CustomerTrackRecordFragment extends BaseFragment<CustomerTrackRecordPresenter>
        implements OnRefreshLoadMoreListener, ICustomerTrackRecordView {
    @BindView(R.id.track_record_recyclerview)
    RecyclerView mTrackRecordRecyclerview;
    @BindView(R.id.track_record_smartrefreshlayout)
    SmartRefreshLayout mTrackRecordSmartrefreshlayout;
    @BindView(R.id.track_record_notdata)
    View mNotDataView;
    @BindView(R.id.track_record_addtrack_tv)
    TextView mTrackRecordAddtrackTv;
    Unbinder unbinder;

    private CustomerTrackRecordAdapter mAdapter;
    private CustomerInfoModel mUserInfo;
    private boolean isFirst = true;

    public static CustomerTrackRecordFragment newIntance(CustomerInfoModel b) {
        CustomerTrackRecordFragment fragment = new CustomerTrackRecordFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(CustomerManageFragment.INTENTKEY_CUSTOMERINFO, b);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        //企业管理员看的时候要隐藏添加跟进
        if (mUserInfo.isAdmin()) {
            mTrackRecordAddtrackTv.setVisibility(View.GONE);
        } else {
            mTrackRecordAddtrackTv.setVisibility(View.VISIBLE);
        }
        mTrackRecordSmartrefreshlayout.setOnRefreshLoadMoreListener(this);
        mAdapter = new CustomerTrackRecordAdapter(_mActivity, null);
        mAdapter.setLayoutManager(mTrackRecordRecyclerview);
        mTrackRecordRecyclerview.setAdapter(mAdapter);
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        isFirst = false;
        if (mUserInfo != null) {
            mPresenter.getData(true, mUserInfo.getId());
        }
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_customer_track_record;
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (mUserInfo != null && !isFirst) {
            mPresenter.getData(true, mUserInfo.getId());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mUserInfo = (CustomerInfoModel) bundle.getSerializable(CustomerManageFragment.INTENTKEY_CUSTOMERINFO);
        }
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.track_record_addtrack_tv)
    public void onViewClicked() {
        if (mUserInfo != null) {
            childStart(CustomerAddTrackFragment.newIntance(mUserInfo));
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (mUserInfo != null) {
            mPresenter.getData(false, mUserInfo.getId());
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        if (mUserInfo != null) {
            mPresenter.getData(true, mUserInfo.getId());
        }
    }

    @Override
    public void onReFreshSuccess(CustomerTrakRecordModel b) {
        mTrackRecordSmartrefreshlayout.finishRefresh();
        mAdapter.update(b.getList());
        mNotDataView.setVisibility(View.GONE);
        mTrackRecordRecyclerview.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadMoreSuccess(CustomerTrakRecordModel b) {
        if (b.getList().size() < 10) {
            mTrackRecordSmartrefreshlayout.finishLoadMoreWithNoMoreData();
        } else {
            mTrackRecordSmartrefreshlayout.finishLoadMore();
        }
        mAdapter.addAll(b.getList());
        mNotDataView.setVisibility(View.GONE);
        mTrackRecordRecyclerview.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNotData() {
        mTrackRecordSmartrefreshlayout.finishRefresh();
        mTrackRecordSmartrefreshlayout.finishLoadMoreWithNoMoreData();
        if (mAdapter.getItemCount() < 1) {
            mNotDataView.setVisibility(View.VISIBLE);
            mTrackRecordRecyclerview.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFail(String msg) {
        onNotData();
        showToast(msg);
    }

}
