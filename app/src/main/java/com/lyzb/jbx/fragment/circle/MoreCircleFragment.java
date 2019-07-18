package com.lyzb.jbx.fragment.circle;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.DividerItemDecoration;
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener;
import com.like.longshaolib.base.BaseToolbarFragment;
import com.like.longshaolib.dialog.fragment.AlertDialogFragment;
import com.like.utilslib.app.CommonUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.circle.SearchCircleAdapter;
import com.lyzb.jbx.model.circle.CircleModel;
import com.lyzb.jbx.model.params.ApplyCircleBody;
import com.lyzb.jbx.mvp.presenter.circle.MoreCirclePresenter;
import com.lyzb.jbx.mvp.view.circle.IMoreCircleView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

/**
 * 更多圈子列表
 */
public class MoreCircleFragment extends BaseToolbarFragment<MoreCirclePresenter>
        implements IMoreCircleView, OnRefreshLoadMoreListener {
    public final static String FROM_MORE_CIRCLE = "from_more_circle";

    private SmartRefreshLayout refresh_more_circle;
    private RecyclerView recycle_more_circle;
    private View empty_view;

    private SearchCircleAdapter mAdapter;

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        super.onInitView(savedInstanceState);
        onBack();
        setToolbarTitle("更多圈子");

        refresh_more_circle = findViewById(R.id.refresh_more_circle);
        recycle_more_circle = findViewById(R.id.recycle_more_circle);
        empty_view = findViewById(R.id.empty_view);

        refresh_more_circle.setOnRefreshLoadMoreListener(this);
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        mAdapter = new SearchCircleAdapter(getContext(), null);
        mAdapter.setLayoutManager(recycle_more_circle);
        recycle_more_circle.addItemDecoration(new DividerItemDecoration(DividerItemDecoration.VERTICAL_LIST,
                R.drawable.listdivider_window_10));
        recycle_more_circle.setAdapter(mAdapter);

        recycle_more_circle.addOnItemTouchListener(new OnRecycleItemClickListener() {
            @Override
            public void onItemClick(BaseRecyleAdapter adapter, View view, int position) {
                super.onItemClick(adapter, view, position);
                //进入圈子
                start(CircleDetailFragment.newIntance(mAdapter.getPositionModel(position).getId()));
            }

            @Override
            public void onItemChildClick(BaseRecyleAdapter adapter, View view, final int position) {
                super.onItemChildClick(adapter, view, position);
                switch (view.getId()) {
                    case R.id.circle_more_item_status_tv:
                        //未加入的才能加入
                        if (CommonUtil.converToT(mAdapter.getList().get(position).getIsJoin(), 0) != 3) {
                            return;
                        }
                        //申请加入圈子
                        AlertDialogFragment.newIntance()
                                .setContent("是否加入该圈子？")
                                .setCancleBtn(null)
                                .setSureBtn(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mPresenter.applyCir(new ApplyCircleBody(mAdapter.getPositionModel(position).getId()));
                                    }
                                })
                                .show(getFragmentManager(), "ApplyCircleTag");
                        break;
                    default:
                }
            }
        });

        findViewById(R.id.tv_circle_more_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(SearchCircleFragment.newIntance(FROM_MORE_CIRCLE));
            }
        });

        mPresenter.getMoreCircle(true);
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_more_circle;
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getMoreCircle(false);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getMoreCircle(true);
    }

    @Override
    public void onMoreCircle(boolean isrefresh, List<CircleModel> list) {
        if (isrefresh) {
            refresh_more_circle.finishRefresh();
            mAdapter.update(list);
            if (list.size() < 10) {
                refresh_more_circle.finishLoadMoreWithNoMoreData();
            }
        } else {
            mAdapter.addAll(list);
            if (list.size() < 10) {
                refresh_more_circle.finishLoadMoreWithNoMoreData();
            } else {
                refresh_more_circle.finishLoadMore();
            }
        }
        if (mAdapter.getItemCount() == 0) {
            empty_view.setVisibility(View.VISIBLE);
        } else {
            empty_view.setVisibility(View.GONE);
        }
    }

    @Override
    public void onApply(String id) {
        //有需要审核的圈子也有不需要审核的   做下判断,真为不需要
        for (int i = 0; i < mAdapter.getList().size(); i++) {
            CircleModel b = mAdapter.getList().get(i);
            if (b.getId().equals(id)) {
                if (b.isPublicStr()) {
                    b.setIsJoin("2");
                } else {
                    b.setIsJoin("4");
                }
                mAdapter.notifyItemChanged(i);
            }
        }
    }
}
