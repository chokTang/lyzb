package com.lyzb.jbx.fragment.me.hot;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener;
import com.like.longshaolib.base.BaseFragment;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.me.hot.HotAccessAdapter;
import com.lyzb.jbx.api.UrlConfig;
import com.lyzb.jbx.mvp.presenter.me.HotAccessPresenter;
import com.lyzb.jbx.mvp.view.me.IHotAccessView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.szy.yishopcustomer.Activity.ProjectH5Activity;
import com.szy.yishopcustomer.Constant.Key;

import java.util.List;

/**
 * 我的-访问分析-热文分析
 */
public class HotAccessFragment extends BaseFragment<HotAccessPresenter> implements IHotAccessView, View.OnClickListener,
        OnRefreshLoadMoreListener {

    private TextView tv_hot_share_number;
    private TextView tv_hot_access_number;
    private SmartRefreshLayout refresh_hot;
    private RecyclerView recycle_hot;
    private TextView tv_fiter;
    private LinearLayout layout_no_vip;
    private TextView tv_value;
    private TextView btn_go_vip;

    private HotAccessAdapter mAdapter;

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        tv_hot_share_number = findViewById(R.id.tv_hot_share_number);
        tv_hot_access_number = findViewById(R.id.tv_hot_access_number);
        refresh_hot = findViewById(R.id.refresh_hot);
        recycle_hot = findViewById(R.id.recycle_hot);
        tv_fiter = findViewById(R.id.tv_fiter);
        layout_no_vip = findViewById(R.id.layout_no_vip);
        tv_value = findViewById(R.id.tv_value);
        btn_go_vip = findViewById(R.id.btn_go_vip);

        tv_fiter.setOnClickListener(this);
        btn_go_vip.setOnClickListener(this);

        refresh_hot.setOnRefreshLoadMoreListener(this);
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        mAdapter = new HotAccessAdapter(getContext(), null);
        mAdapter.setGridLayoutManager(recycle_hot, 3);
        recycle_hot.setAdapter(mAdapter);

        recycle_hot.addOnItemTouchListener(new OnRecycleItemClickListener(){
            @Override
            public void onItemClick(BaseRecyleAdapter adapter, View view, int position) {
                childStart(new HotAccessDetailFragment());
            }
        });

        layout_no_vip.setVisibility(View.GONE);
        mPresenter.getListData(true);
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_hot_access;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //筛选
            case R.id.tv_fiter:
                childStart(new HotAccessSearchFragment());
                break;
            //开通VIP
            case R.id.btn_go_vip:
                Intent tgIntent = new Intent(getContext(), ProjectH5Activity.class);
                tgIntent.putExtra(Key.KEY_URL.getValue(), UrlConfig.H5_OPEN_VIP);
                startActivity(tgIntent);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean isDelayedData() {
        return false;
    }

    @Override
    public void onRefreshOrLoadMore(boolean isRefresh) {
        if (isRefresh) {
            refresh_hot.finishRefresh();
        } else {
            refresh_hot.finishLoadMore();
        }
    }

    @Override
    public void onListResult(boolean isRefresh, List<String> list) {
        if (isRefresh) {
            refresh_hot.finishRefresh();
            mAdapter.update(list);
            if (list.size() < 10) {
                refresh_hot.finishLoadMoreWithNoMoreData();
            }
        } else {
            mAdapter.addAll(list);
            if (list.size() < 10) {
                refresh_hot.finishLoadMoreWithNoMoreData();
            } else {
                refresh_hot.finishLoadMore();
            }
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getListData(false);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getListData(true);
    }
}
