package com.lyzb.jbx.fragment.me.customerManage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener;
import com.like.longshaolib.base.BaseFragment;
import com.like.longshaolib.widget.ClearEditText;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.me.customerManage.CustomerManageAdapter;
import com.lyzb.jbx.fragment.me.card.CardFragment;
import com.lyzb.jbx.model.me.customerManage.CustomerManageModel;
import com.lyzb.jbx.mvp.presenter.me.customerManage.CustomerManagePresenter;
import com.lyzb.jbx.mvp.view.me.ICustomerManageView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author wyx
 * @role 客户管理-搜索
 * @time 2019 2019/5/6 10:21
 */

public class CustomerSearchFragment extends BaseFragment<CustomerManagePresenter>
        implements OnRefreshLoadMoreListener, ICustomerManageView {
    public static final String INTENTKEY_ID = "intentkey_id";
    public static final String INTENTKEY_NAME = "intentkey_name";


    @BindView(R.id.statistics_title_tv)
    TextView mStatisticsTitleTv;
    @BindView(R.id.customer_search_edit)
    ClearEditText mCustomerSearchEdit;
    @BindView(R.id.customer_search_iv)
    ImageView mCustomerSearchIv;
    @BindView(R.id.customer_search_recyclerview)
    RecyclerView mCustomerSearchRecyclerview;
    @BindView(R.id.customer_search_smartrefreshlayout)
    SmartRefreshLayout mCustomerSearchSmartrefreshlayout;
    @BindView(R.id.customer_search_notdata)
    View mNotDataView;
    Unbinder unbinder;

    private CustomerManageAdapter mAdapter;
    private String mUserId;
    private String mUserName;
    private String mSearchValue;

    public static CustomerSearchFragment newIntance(String id, String name) {
        CustomerSearchFragment fragment = new CustomerSearchFragment();
        Bundle bundle = new Bundle();
        bundle.putString(CustomerManageFragment.INTENTKEY_ID, id);
        bundle.putString(CustomerManageFragment.INTENTKEY_NAME, name);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        if (TextUtils.isEmpty(mUserName)) {
            mStatisticsTitleTv.setText("客户管理");
        } else {
            mStatisticsTitleTv.setText(mUserName + "的客户");
        }
        mAdapter = new CustomerManageAdapter(_mActivity, !TextUtils.isEmpty(mUserName), null);
        mAdapter.setLayoutManager(mCustomerSearchRecyclerview);
        mCustomerSearchRecyclerview.setAdapter(mAdapter);
        mCustomerSearchRecyclerview.addOnItemTouchListener(new OnRecycleItemClickListener() {
            @Override
            public void onItemClick(BaseRecyleAdapter adapter, View view, int position) {
                super.onItemClick(adapter, view, position);
                //进入客户详情
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
        //键盘上的搜索
        mCustomerSearchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
                if (arg1 == EditorInfo.IME_ACTION_SEARCH) {
                    String value = mCustomerSearchEdit.getText().toString().trim();
                    if (TextUtils.isEmpty(value)) {
                        showToast("请输入搜索内容");
                        return true;
                    }
                    mSearchValue = value;
                    mPresenter.getData(true, mUserId, mSearchValue);
                }
                return true;
            }
        });
        mCustomerSearchSmartrefreshlayout.setOnRefreshLoadMoreListener(this);
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        showSoftInput(mCustomerSearchEdit);
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_customer_search;
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
        mPresenter.getData(false, mUserId, mSearchValue);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getData(true, mUserId, mSearchValue);
    }

    @Override
    public void onLoadMore(CustomerManageModel b) {
        if (b.getList().size() < 10) {
            mCustomerSearchSmartrefreshlayout.finishLoadMoreWithNoMoreData();
        } else {
            mCustomerSearchSmartrefreshlayout.finishLoadMore();
        }
        mAdapter.addAll(b.getList());
        mCustomerSearchRecyclerview.setVisibility(View.VISIBLE);
        mNotDataView.setVisibility(View.GONE);
    }

    @Override
    public void onReFresh(CustomerManageModel b) {
        if (b.getList().size() < 1 && mAdapter.getList().size() < 1) {
            onNotData();
            return;
        }
        mCustomerSearchSmartrefreshlayout.finishRefresh();
        mAdapter.update(b.getList());
        mCustomerSearchRecyclerview.setVisibility(View.VISIBLE);
        mNotDataView.setVisibility(View.GONE);
    }

    @Override
    public void onNotData() {
        mCustomerSearchSmartrefreshlayout.finishRefresh();
        mCustomerSearchSmartrefreshlayout.finishLoadMoreWithNoMoreData();

        mCustomerSearchRecyclerview.setVisibility(View.GONE);
        mNotDataView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFail(String msg) {
        onNotData();
        showToast(msg);
    }

    @OnClick({R.id.statistics_back_iv, R.id.customer_search_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.statistics_back_iv:
                pop();
                break;
            case R.id.customer_search_iv:
                if (mCustomerSearchEdit.getText().length() < 1) {
                    showToast("请输入搜索内容");
                    return;
                }
                mSearchValue = mCustomerSearchEdit.getText().toString();
                mPresenter.getData(true, mUserId, mSearchValue);
                break;
            default:
        }
    }
}
