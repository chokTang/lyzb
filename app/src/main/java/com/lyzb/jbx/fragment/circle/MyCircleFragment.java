package com.lyzb.jbx.fragment.circle;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.DividerItemDecoration;
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener;
import com.like.longshaolib.base.BaseFragment;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.me.MyCircleAdapter;
import com.lyzb.jbx.model.me.CircleModel;
import com.lyzb.jbx.mvp.presenter.me.MyCirclePresenter;
import com.lyzb.jbx.mvp.view.me.IMyCircleView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

/**
 * @author wyx
 * @role 我的圈子
 * @time 2019 2019/3/8 15:23
 */

public class MyCircleFragment extends BaseFragment<MyCirclePresenter> implements OnRefreshLoadMoreListener, IMyCircleView {

    private LinearLayout backImg;
    private TextView addCircle;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView circleRecy;
    private View emptyLin;

    private MyCircleAdapter mMyCircleAdapter = null;

    private boolean isCreate = true;

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {


        backImg = findViewById(R.id.ll_back);
        addCircle = findViewById(R.id.tv_union_me_add_circle);
        mRefreshLayout = findViewById(R.id.sf_un_me_circle);
        circleRecy = findViewById(R.id.recy_un_me_circle);
        emptyLin = findViewById(R.id.circle_empty_view);

        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });

        addCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                start(AddCircleFragment.newItance(2, null, null));

            }
        });

        circleRecy.addOnItemTouchListener(new OnRecycleItemClickListener() {
            @Override
            public void onItemClick(BaseRecyleAdapter adapter, View view, int position) {
                super.onItemClick(adapter, view, position);
                start(CircleDetailFragment.newIntance(mMyCircleAdapter.getPositionModel(position).getId()));
            }
        });

        mMyCircleAdapter = new MyCircleAdapter(getContext(), null);
        mMyCircleAdapter.setLayoutManager(circleRecy);
        circleRecy.addItemDecoration(new DividerItemDecoration(DividerItemDecoration.VERTICAL_LIST, R.drawable.listdivider_window_10));
        circleRecy.setAdapter(mMyCircleAdapter);

        mRefreshLayout.setOnRefreshListener(this);

        mPresenter.getStatus();
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public Object getResId() {
        return R.layout.fragment_ub_me_my_circle;
    }


    @Override
    public void onList(boolean isRefresh, List<CircleModel.ListBean> list) {

        if (isRefresh) {
            mRefreshLayout.finishRefresh();
            mMyCircleAdapter.update(list);
        } else {
            mMyCircleAdapter.addAll(list);
            if (list.size() < 10) {
                mRefreshLayout.finishLoadMoreWithNoMoreData();
            } else {
                mRefreshLayout.finishLoadMore();
            }
        }

        if (mMyCircleAdapter.getItemCount() == 0) {
            emptyLin.setVisibility(View.VISIBLE);
        } else {
            emptyLin.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFinshOrLoadMore(boolean isRefresh) {

        if (isRefresh) {
            mRefreshLayout.finishRefresh();
        } else {
            mRefreshLayout.finishLoadMore();
        }
    }

    @Override
    public void onCreate() {
        isCreate = true;
    }

    @Override
    public void onUnCreate() {
        isCreate = false;
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getList(false);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getList(true);
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        mPresenter.getList(true);
    }
}
