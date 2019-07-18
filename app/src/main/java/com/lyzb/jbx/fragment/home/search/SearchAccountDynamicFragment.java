package com.lyzb.jbx.fragment.home.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.DividerItemDecoration;
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.home.search.SearchAccountDynamicAdapter;
import com.lyzb.jbx.fragment.base.BaseVideoFrgament;
import com.lyzb.jbx.fragment.me.card.CardFragment;
import com.lyzb.jbx.model.account.SearchAccountModel;
import com.lyzb.jbx.mvp.presenter.home.search.AccountDynamicPresenter;
import com.lyzb.jbx.mvp.view.home.search.IAccountDynamicView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.szy.yishopcustomer.Activity.LoginActivity;
import com.szy.yishopcustomer.Util.App;

import java.util.List;

/**
 * 搜索-用户动态页面
 */
public class SearchAccountDynamicFragment extends BaseVideoFrgament<AccountDynamicPresenter> implements IAccountDynamicView, OnRefreshLoadMoreListener {

    private String mSearchValue="";
    private static final String PARAMS_VALUE = "PARAMS_VALUE";

    private SmartRefreshLayout refresh_account_dynamic;
    private RecyclerView recycle_account_dynamic;
    private View empty_view;
    private SearchAccountDynamicAdapter mAdapter;

    public static SearchAccountDynamicFragment newIntance(String value) {
        SearchAccountDynamicFragment fragment = new SearchAccountDynamicFragment();
        Bundle args = new Bundle();
        args.putString(PARAMS_VALUE, value);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mSearchValue = args.getString(PARAMS_VALUE);
        }
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        refresh_account_dynamic = findViewById(R.id.refresh_account_dynamic);
        recycle_account_dynamic = findViewById(R.id.recycle_account_dynamic);
        empty_view = findViewById(R.id.empty_view);

        refresh_account_dynamic.setOnRefreshLoadMoreListener(this);

        mAdapter = new SearchAccountDynamicAdapter(getContext(), null);
        mAdapter.setFragment(this);
        mAdapter.setLayoutManager(recycle_account_dynamic);
        recycle_account_dynamic.addItemDecoration(new DividerItemDecoration(DividerItemDecoration.VERTICAL_LIST, R.drawable.listdivider_window_10));
        recycle_account_dynamic.setAdapter(mAdapter);

        notifySeacrhValue(mSearchValue);
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        recycle_account_dynamic.addOnItemTouchListener(new OnRecycleItemClickListener() {
            @Override
            public void onItemChildClick(BaseRecyleAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    //已关注
                    case R.id.tv_follow:
                        if (App.getInstance().isLogin()) {
                            mPresenter.onDynamciFollowUser(mAdapter.getPositionModel(position).getUserId(), 0, position);
                        } else {
                            startActivity(new Intent(getContext(), LoginActivity.class));
                        }
                        break;
                    //未关注
                    case R.id.tv_no_follow:
                        if (App.getInstance().isLogin()) {
                            mPresenter.onDynamciFollowUser(mAdapter.getPositionModel(position).getUserId(), 1, position);
                        } else {
                            startActivity(new Intent(getContext(), LoginActivity.class));
                        }
                        break;
                    //进入个人名片页面
                    case R.id.tv_dynamic_number:
                    case R.id.img_header:
                        childStart(CardFragment.newIntance(2, mAdapter.getPositionModel(position).getUserId()));
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_search_account_dynamic;
    }

    @Override
    public void finshLoadData(boolean isfresh) {
        if (isfresh) {
            refresh_account_dynamic.finishRefresh();
        } else {
            refresh_account_dynamic.finishLoadMore();
        }
    }

    @Override
    public void onListResult(boolean isRefrsh, List<SearchAccountModel> list) {
        if (isRefrsh) {
            refresh_account_dynamic.finishRefresh();
            mAdapter.update(list);
            if (list.size() < 10) {
                refresh_account_dynamic.finishLoadMoreWithNoMoreData();
            }
        } else {
            mAdapter.addAll(list);
            if (list.size() < 10) {
                refresh_account_dynamic.finishLoadMoreWithNoMoreData();
            } else {
                refresh_account_dynamic.finishLoadMore();
            }
        }
        empty_view.setVisibility(mAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
    }

    //点击关注以后回调
    @Override
    public void onFollowItemResult(int position) {
        SearchAccountModel model = mAdapter.getPositionModel(position);
        model.setRelationNum(model.getRelationNum() == 0 ? 1 : 0);
        mAdapter.change(position, model);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getList(false, mSearchValue);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getList(true, mSearchValue);
    }

    /**
     * 搜索发生改变时候调用
     *
     * @param value
     */
    public void notifySeacrhValue(String value) {
        mSearchValue = value;
        mPresenter.getList(true, mSearchValue);
    }

    @Override
    public boolean isDelayedData() {
        return false;
    }
}
