package com.lyzb.jbx.fragment.me.hot;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.like.longshaolib.adapter.rvhelper.DividerItemDecoration;
import com.like.longshaolib.base.BaseToolbarFragment;
import com.like.longshaolib.dialog.fragment.DialogTimeFragment;
import com.like.longshaolib.dialog.inter.IDialogTimeListener;
import com.like.utilslib.app.CommonUtil;
import com.like.utilslib.date.DateStyle;
import com.like.utilslib.date.DateUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.me.access.HotAccessDetailAdapter;
import com.lyzb.jbx.mvp.presenter.me.HotAccessSearchPresenter;
import com.lyzb.jbx.mvp.view.me.IHotAccessSearchView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.Date;

/**
 * 我的-访客分析-热文分析-筛选
 */
public class HotAccessSearchFragment extends BaseToolbarFragment<HotAccessSearchPresenter> implements IHotAccessSearchView,
        View.OnClickListener,
        OnRefreshLoadMoreListener {

    private TextView tv_hint_value;
    private TextView tv_start_time;
    private TextView tv_end_time;
    private Button btn_search;
    private SmartRefreshLayout refresh_hot_search;
    private RecyclerView recycle_hot_search;
    private View empty_view;

    private HotAccessDetailAdapter mAdapter;

    //开始和结束时间
    private Date startDate = new Date();
    private Date endDate = new Date();

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        super.onInitView(savedInstanceState);
        onBack();
        setToolbarTitle("热文分析筛选");

        tv_hint_value = findViewById(R.id.tv_hint_value);
        tv_start_time = findViewById(R.id.tv_start_time);
        tv_end_time = findViewById(R.id.tv_end_time);
        btn_search = findViewById(R.id.btn_search);
        refresh_hot_search = findViewById(R.id.refresh_hot_search);
        recycle_hot_search = findViewById(R.id.recycle_hot_search);
        empty_view = findViewById(R.id.empty_view);

        tv_start_time.setOnClickListener(this);
        tv_end_time.setOnClickListener(this);
        btn_search.setOnClickListener(this);

        refresh_hot_search.setOnRefreshLoadMoreListener(this);
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        mAdapter = new HotAccessDetailAdapter(getContext(), null);
        mAdapter.setLayoutManager(recycle_hot_search);
        recycle_hot_search.addItemDecoration(new DividerItemDecoration(DividerItemDecoration.VERTICAL_LIST));
        recycle_hot_search.setAdapter(mAdapter);
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_hot_search_access;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //开始时间
            case R.id.tv_start_time:
                DialogTimeFragment.newIntance()
                        .setDateStyle(DialogTimeFragment.YYYY_MM_DD)
                        .setTitle("选择开始时间")
                        .setCurrentDate(startDate)
                        .setDialogListener(new IDialogTimeListener() {
                            @Override
                            public void onSure(Date date) {
                                startDate = date;
                                tv_start_time.setText(DateUtil.DateToString(date, DateStyle.YYYY_MM_DD));
                            }

                            @Override
                            public void onCancle() {

                            }
                        })
                        .show(getFragmentManager(), "hotStartTime");
                break;
            //结束时间
            case R.id.tv_end_time:
                DialogTimeFragment.newIntance()
                        .setDateStyle(DialogTimeFragment.YYYY_MM_DD)
                        .setTitle("选择结束时间")
                        .setCurrentDate(endDate)
                        .setDialogListener(new IDialogTimeListener() {
                            @Override
                            public void onSure(Date date) {
                                if (date.getTime() <= startDate.getTime()) {
                                    showToast("结束时间不能小于开始时间");
                                }
                                endDate = date;
                                tv_end_time.setText(DateUtil.DateToString(date, DateStyle.YYYY_MM_DD));
                            }

                            @Override
                            public void onCancle() {

                            }
                        })
                        .show(getFragmentManager(), "hotEndTime");
                break;
            //点击搜索
            case R.id.btn_search:
                String startTime = tv_start_time.getText().toString().trim();
                String endTime = tv_end_time.getText().toString().trim();
                if (CommonUtil.isNull(startTime) && CommonUtil.isNull(endTime)) {
                    showToast("请选择时间段");
                    return;
                }
                tv_hint_value.setVisibility(View.GONE);
                mPresenter.search(true, startTime, endTime);
                break;
            default:
                break;
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
    }

    @Override
    public void finshRefreshOrLoadMore(boolean isRefresh) {
        if (isRefresh) {
            refresh_hot_search.finishRefresh();
        } else {
            refresh_hot_search.finishLoadMore();
        }
    }
}
