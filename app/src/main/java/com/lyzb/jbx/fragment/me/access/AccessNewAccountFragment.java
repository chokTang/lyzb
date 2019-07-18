package com.lyzb.jbx.fragment.me.access;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.DividerItemDecoration;
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener;
import com.like.longshaolib.base.BaseToolbarFragment;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.me.access.AccessAccountAdapter;
import com.lyzb.jbx.fragment.me.card.CardFragment;
import com.lyzb.jbx.model.cenum.DayEnum;
import com.lyzb.jbx.model.me.access.AccessNewAccountModel;
import com.lyzb.jbx.mvp.presenter.me.AccessNewAccountPresenter;
import com.lyzb.jbx.mvp.view.me.IAccessNewAccountView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.szy.yishopcustomer.Util.App;

import java.util.List;

/**
 * 引流新用户
 */
public class AccessNewAccountFragment extends BaseToolbarFragment<AccessNewAccountPresenter> implements IAccessNewAccountView, OnRefreshLoadMoreListener {

    private String mUserId = "";
    private String mDayType = DayEnum.DAY_THIRTY.getValue();
    private String mUserName = "";
    private static final String PARAMS_ID = "params_id";
    private static final String PARAMS_NAME = "params_name";
    private static final String PARAMS_DAY = "params_day";

    private TextView tv_total_number;
    private SmartRefreshLayout refresh_new_account;
    private RecyclerView recycle_new_account;
    private View empty_view;

    private AccessAccountAdapter mAdapter;

    public static AccessNewAccountFragment newIntance(String userId, String userName, String dayType) {
        AccessNewAccountFragment fragment = new AccessNewAccountFragment();
        Bundle args = new Bundle();
        args.putString(PARAMS_ID, userId);
        args.putString(PARAMS_DAY, dayType);
        args.putString(PARAMS_NAME, userName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mUserId = args.getString(PARAMS_ID);
            mDayType = args.getString(PARAMS_DAY);
            mUserName = args.getString(PARAMS_NAME);
        }
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        super.onInitView(savedInstanceState);
        onBack();
        if (TextUtils.isEmpty(mUserId) || mUserId.equals(App.getInstance().userId)) {
            setToolbarTitle("引流用户列表");
        } else {
            setToolbarTitle(String.format("%s的引流用户列表", mUserName));
        }

        tv_total_number = findViewById(R.id.tv_total_number);
        refresh_new_account = findViewById(R.id.refresh_new_account);
        recycle_new_account = findViewById(R.id.recycle_new_account);
        empty_view = findViewById(R.id.empty_view);

        refresh_new_account.setOnRefreshLoadMoreListener(this);
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        mAdapter = new AccessAccountAdapter(getContext(), null);
        mAdapter.setLayoutManager(recycle_new_account);
        recycle_new_account.addItemDecoration(new DividerItemDecoration(DividerItemDecoration.VERTICAL_LIST));
        recycle_new_account.setAdapter(mAdapter);

        recycle_new_account.addOnItemTouchListener(new OnRecycleItemClickListener() {
            @Override
            public void onItemChildClick(BaseRecyleAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    //点击头像
                    case R.id.img_member_header:
                        start(CardFragment.newIntance(2,mAdapter.getPositionModel(position).getBrowserUserId()));
                        break;
                    default:
                        break;
                }
            }
        });

        mPresenter.getAccountList(true, mUserId, mDayType);
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_access_new_account;
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getAccountList(false, mUserId, mDayType);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getAccountList(true, mUserId, mDayType);
    }

    @Override
    public void onAccountResultList(boolean isRefresh, int total, List<AccessNewAccountModel> list) {
        tv_total_number.setText(String.format("%s共引流新用户%d人", mDayType, total));
        if (isRefresh) {
            refresh_new_account.finishRefresh();
            mAdapter.update(list);
            if (list.size() < 10) {
                refresh_new_account.finishLoadMoreWithNoMoreData();
            }
        } else {
            mAdapter.addAll(list);
            if (list.size() < 10) {
                refresh_new_account.finishLoadMoreWithNoMoreData();
            } else {
                refresh_new_account.finishLoadMore();
            }
        }
        if (mAdapter.getItemCount() == 0) {
            empty_view.setVisibility(View.VISIBLE);
        } else {
            empty_view.setVisibility(View.GONE);
        }
    }
}
