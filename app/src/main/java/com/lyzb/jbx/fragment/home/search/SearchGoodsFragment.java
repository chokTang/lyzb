package com.lyzb.jbx.fragment.home.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.DividerGridItemDecoration;
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener;
import com.like.longshaolib.base.BaseFragment;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.home.search.SearchGoodsAdapter;
import com.lyzb.jbx.mvp.presenter.home.search.GoodSearchPresenter;
import com.lyzb.jbx.mvp.view.home.search.ISearchGoodsView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.szy.yishopcustomer.Activity.GoodsActivity;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.ResponseModel.GoodsModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索商品结果页面
 */
public class SearchGoodsFragment extends BaseFragment<GoodSearchPresenter> implements ISearchGoodsView, OnRefreshLoadMoreListener {

    private String mSearchValue;
    private static final String PARAMS_VALUE = "PARAMS_VALUE";

    private SmartRefreshLayout refresh_goods;
    private RecyclerView recycle_search_goods;
    private View empty_view;
    private SearchGoodsAdapter mAdapter;

    public static SearchGoodsFragment newIntance(String value) {
        SearchGoodsFragment fragment = new SearchGoodsFragment();
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
        refresh_goods = findViewById(R.id.refresh_goods);
        recycle_search_goods = findViewById(R.id.recycle_search_goods);
        empty_view = findViewById(R.id.empty_view);

        refresh_goods.setOnLoadMoreListener(this);

        mAdapter = new SearchGoodsAdapter(getContext(), null);
        recycle_search_goods.addItemDecoration(new DividerGridItemDecoration(R.drawable.listdivider_window_10));
        mAdapter.setGridLayoutManager(recycle_search_goods, 2);
        recycle_search_goods.setAdapter(mAdapter);

        recycle_search_goods.addOnItemTouchListener(new OnRecycleItemClickListener(){
            @Override
            public void onItemClick(BaseRecyleAdapter adapter, View view, int position) {
                Intent intent = new Intent(getContext(), GoodsActivity.class);
                intent.putExtra(Key.KEY_GOODS_ID.getValue(), mAdapter.getPositionModel(position).goods_id);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_search_goods;
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getList(false, mSearchValue);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getList(true, mSearchValue);
    }

    @Override
    public void finshLoadData(boolean isrefresh) {
        if (isrefresh) {
            refresh_goods.finishRefresh();
        } else {
            refresh_goods.finishLoadMore();
        }
    }

    @Override
    public void onGoodsListResult(boolean isRefrsh, List<GoodsModel> list) {
        if (list == null) list = new ArrayList<>();
        if (isRefrsh) {
            refresh_goods.finishRefresh();
            mAdapter.update(list);
            if (list.size() < 10) {
                refresh_goods.finishLoadMoreWithNoMoreData();
            }
            if (list.size() == 0) {
                empty_view.setVisibility(View.VISIBLE);
            } else {
                empty_view.setVisibility(View.GONE);
            }
        } else {
            mAdapter.addAll(list);
            if (list.size() < 10) {
                refresh_goods.finishLoadMoreWithNoMoreData();
            } else {
                refresh_goods.finishLoadMore();
            }
        }
    }

    /**
     * 搜索发生改变时候调用
     *
     * @param value
     */
    public void notifySeacrhValue(String value) {
        mSearchValue = value;
        mPresenter.getList(true, value);
    }

    @Override
    public boolean isDelayedData() {
        return false;
    }
}
