package com.lyzb.jbx.fragment.me.customerManage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener;
import com.like.longshaolib.base.BaseFragment;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.me.customerManage.CustomerManageAdapter;
import com.lyzb.jbx.fragment.me.card.CardFragment;
import com.lyzb.jbx.model.eventbus.CustomerDeleteEventBus;
import com.lyzb.jbx.model.me.customerManage.CustomerManageModel;
import com.lyzb.jbx.mvp.presenter.me.customerManage.CustomerManagePresenter;
import com.lyzb.jbx.mvp.view.me.ICustomerManageView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author wyx
 * @role 客户管理
 * @time 2019 2019/5/6 10:21
 */

public class CustomerManageFragment extends BaseFragment<CustomerManagePresenter>
        implements OnRefreshLoadMoreListener, ICustomerManageView {
    public static final String INTENTKEY_ID = "intentkey_id";
    public static final String INTENTKEY_NAME = "intentkey_name";
    public static final String INTENTKEY_CUSTOMERINFO = "intentkey_customerinfo";

    @BindView(R.id.customer_manage_notdata)
    View mNotDataView;
    @BindView(R.id.statistics_title_tv)
    TextView mStatisticsTitleTv;
    @BindView(R.id.customer_manage_number_tv)
    TextView mCustomerManageNumberTv;
    @BindView(R.id.customer_manage_screen_tv)
    TextView mCustomerManageScreenTv;
    @BindView(R.id.customer_manage_recyclerview)
    RecyclerView mCustomerManageRecyclerview;
    @BindView(R.id.customer_manage_smartrefreshlayout)
    SmartRefreshLayout mCustomerManageSmartrefreshlayout;
    Unbinder unbinder;
    @BindView(R.id.empty_tv)
    TextView mEmptyTv;

    private CustomerManageAdapter mAdapter;
    private String mUserId;
    private String mUserName;

    //关于删除
    private int mPoistion = -1;

    public static CustomerManageFragment newIntance(String id, String name) {
        CustomerManageFragment fragment = new CustomerManageFragment();
        Bundle bundle = new Bundle();
        bundle.putString(INTENTKEY_ID, id);
        bundle.putString(INTENTKEY_NAME, name);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        if (TextUtils.isEmpty(mUserId)) {
            mStatisticsTitleTv.setText("客户管理");
        } else {
            mStatisticsTitleTv.setText(String.format("%s的客户详细", mUserName));
        }
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        mAdapter = new CustomerManageAdapter(_mActivity, !TextUtils.isEmpty(mUserName), null);
        mAdapter.setLayoutManager(mCustomerManageRecyclerview);
        mCustomerManageRecyclerview.setAdapter(mAdapter);
        mCustomerManageRecyclerview.addOnItemTouchListener(new OnRecycleItemClickListener() {
            @Override
            public void onItemClick(BaseRecyleAdapter adapter, View view, int position) {
                //进入客户详情
                mPoistion = position;
                start(CustomerDetailsFragment.newIntance(mAdapter.getList().get(position).getId(), mUserName));
            }

            @Override
            public void onItemChildClick(BaseRecyleAdapter adapter, View view, int position) {
                super.onItemChildClick(adapter, view, position);
                switch (view.getId()) {
                    //添加跟进
                    case R.id.customer_manage_addtrack_tv:
                        start(CustomerAddTrackFragment.newIntance(mAdapter.getPositionModel(position)));
                        break;
                    //进入客户名片
                    case R.id.customer_manage_head_iv:
                        start(CardFragment.newIntance(2, mAdapter.getList().get(position).getCustomerUserId()));
                        break;
                    default:
                }
            }
        });

        mCustomerManageSmartrefreshlayout.setOnRefreshLoadMoreListener(this);
        mPresenter.getData(true, mUserId, "");
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_customer_manage;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mUserId = bundle.getString(INTENTKEY_ID);
            mUserName = bundle.getString(INTENTKEY_NAME);
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
        mPresenter.getData(false, mUserId, "");
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getData(true, mUserId, "");
    }

    @Override
    public void onLoadMore(CustomerManageModel b) {
        if (b.getList().size() < 10) {
            mCustomerManageSmartrefreshlayout.finishLoadMoreWithNoMoreData();
        } else {
            mCustomerManageSmartrefreshlayout.finishLoadMore();
        }
        mCustomerManageNumberTv.setText(String.format("共%d位客户", b.getTotal()));
        mAdapter.addAll(b.getList());
        mCustomerManageRecyclerview.setVisibility(View.VISIBLE);
        mNotDataView.setVisibility(View.GONE);
    }

    @Override
    public void onReFresh(CustomerManageModel b) {
        if (b.getList().size() < 1 && mAdapter.getList().size() < 1) {
            onNotData();
            return;
        }
        mCustomerManageSmartrefreshlayout.finishRefresh();
        mCustomerManageNumberTv.setText(String.format("共%d位客户", b.getTotal()));
        mAdapter.update(b.getList());
        mCustomerManageRecyclerview.setVisibility(View.VISIBLE);
        mNotDataView.setVisibility(View.GONE);
    }

    @Override
    public void onNotData() {
        mCustomerManageSmartrefreshlayout.finishRefresh();
        mCustomerManageSmartrefreshlayout.finishLoadMoreWithNoMoreData();

        mCustomerManageNumberTv.setText("共0位客户");

        mCustomerManageRecyclerview.setVisibility(View.GONE);
        mNotDataView.setVisibility(View.VISIBLE);

        if (TextUtils.isEmpty(mUserName)) {
            mEmptyTv.setText("您还未设置意向客户哦~\n请在访客统计-访问数据/分享数据\n-访客明细列表中把您感兴趣的用户设为意向");
        } else {
            mEmptyTv.setText(String.format("%s还未设置意向客户哦~", mUserName));
        }
        mEmptyTv.setGravity(Gravity.CENTER);
        mNotDataView.setBackgroundColor(ContextCompat.getColor(_mActivity, R.color.white));
    }

    @Override
    public void onFail(String msg) {
        onNotData();
        showToast(msg);
    }

    @OnClick({R.id.statistics_back_iv, R.id.customer_manage_screen_tv, R.id.customer_manage_search_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //返回键
            case R.id.statistics_back_iv:
                pop();
                break;
            //排序方式
            case R.id.customer_manage_screen_tv:
                break;
            //搜索
            case R.id.customer_manage_search_tv:
                start(CustomerSearchFragment.newIntance(mUserId, null));
                break;
            default:
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void notifyItemChange(CustomerDeleteEventBus eventBus) {
        if (mPoistion > -1) {
            mAdapter.remove(mPoistion);
            mPoistion = -1;
            if (mAdapter.getItemCount() == 0) {
                onNotData();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
