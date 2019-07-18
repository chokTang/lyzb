package com.lyzb.jbx.fragment.home.first;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener;
import com.like.longshaolib.base.BaseFragment;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.home.first.HomeVideoAdapter;
import com.lyzb.jbx.fragment.home.HomeFragment;
import com.lyzb.jbx.fragment.me.card.CardFragment;
import com.lyzb.jbx.model.video.HomeVideoModel;
import com.lyzb.jbx.mvp.presenter.home.first.HomeVideoPresenter;
import com.lyzb.jbx.mvp.view.home.first.IHomeVideoView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.szy.yishopcustomer.Activity.LoginActivity;
import com.szy.yishopcustomer.Util.App;

import java.util.List;

/**
 * 首页小视频列表
 */
public class HomeVideoFragment extends BaseFragment<HomeVideoPresenter> implements IHomeVideoView, OnRefreshLoadMoreListener {

    private SmartRefreshLayout refresh_video;
    private RecyclerView recycle_video;

    private HomeVideoAdapter mHomeVideoAdapter = null;
    private HomeFragment parentFragment;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        parentFragment = (HomeFragment) getParentFragment();
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        refresh_video = findViewById(R.id.refresh_video);
        recycle_video = findViewById(R.id.recycle_video);

        recycle_video.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
                if (parentFragment != null) {
                    if (layoutManager.findFirstVisibleItemPositions(new int[2])[0] > 1) {
                        parentFragment.isShowRefresh(true);
                        isTop = false;
                    } else {
                        parentFragment.isShowRefresh(false);
                        isTop = true;
                    }
                }
            }
        });
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {

        refresh_video.setOnRefreshLoadMoreListener(this);
        mHomeVideoAdapter = new HomeVideoAdapter(getContext(), null);
//        mHomeVideoAdapter.setLayoutManager(recycle_video);
        recycle_video.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recycle_video.setAdapter(mHomeVideoAdapter);

        mPresenter.getList(true);

        recycle_video.addOnItemTouchListener(new OnRecycleItemClickListener() {
            @Override
            public void onItemClick(BaseRecyleAdapter adapter, View view, int position) {
                super.onItemClick(adapter, view, position);
                //进入视频列表页，自己写了个回调来刷新页面
                VideoListFragment videoListFragment = VideoListFragment.newIntance(new Gson().toJson(adapter.getList()), position);
                videoListFragment.setOnVideoListFragmentForResult(new VideoListFragment.OnVideoListFragmentForResult() {
                    @Override
                    public void onVideoListFragmentForResult(List<HomeVideoModel.ListBean> list) {
                        mHomeVideoAdapter.update(list);
                    }
                });
                childDoubleStart(videoListFragment);
            }

            @Override
            public void onItemChildClick(BaseRecyleAdapter adapter, View view, int position) {
                super.onItemChildClick(adapter, view, position);
                HomeVideoModel.ListBean bean = (HomeVideoModel.ListBean) adapter.getPositionModel(position);

                switch (view.getId()) {
                    case R.id.ic_image:
                    case R.id.tv_name://名片
                        if (bean.getUserId().equals(App.getInstance().userId)) {
                            childDoubleStart(CardFragment.newIntance(1, bean.getUserId()));
                        } else {
                            childDoubleStart(CardFragment.newIntance(2, bean.getUserId()));
                        }

                        break;
                    case R.id.tv_zan_number://点赞
                        if (App.getInstance().isLogin()) {
                            if (bean.getGiveLike() > 0) {
                                mPresenter.onCancleZan(bean.getId(), position);
                            } else {
                                mPresenter.onZan(bean.getId(), position);
                            }
                        } else {
                            startActivity(new Intent(getContext(), LoginActivity.class));
                        }
                        break;
                }
            }
        });


    }

    @Override
    public Object getResId() {
        return R.layout.fragment_home_video;
    }

    @Override
    public boolean isDelayedData() {
        return false;
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
    public void onApplyList(boolean isRefresh, List<HomeVideoModel.ListBean> list) {
        if (isRefresh) {
            refresh_video.finishRefresh();
            if (list.size() < 15) {
                refresh_video.finishLoadMoreWithNoMoreData();
            }
            mHomeVideoAdapter.update(list);
        } else {
            if (list.size() < 15) {
                refresh_video.finishLoadMoreWithNoMoreData();
            } else {
                refresh_video.finishLoadMore();
            }
            mHomeVideoAdapter.addAll(list);
        }

//        if (mCompanyApplyListAdapter.getItemCount() == 0) {
//            empty_view.setVisibility(View.VISIBLE);
//        } else {
//            empty_view.setVisibility(View.GONE);
//        }
    }

    @Override
    public void onZanResult(int position) {
        HomeVideoModel.ListBean model = mHomeVideoAdapter.getPositionModel(position);
        model.setGiveLike(model.getGiveLike() == 0 ? 1 : 0);
        model.setUpCount(model.getGiveLike() > 0 ? model.getUpCount() + 1 : model.getUpCount() - 1);
        BaseViewHolder baseViewHolder = getVidewHolder(position);
        if (baseViewHolder == null) {
            return;
        }
        TextView tvZan = baseViewHolder.cdFindViewById(R.id.tv_zan_number);
        tvZan.setText(String.valueOf(model.getUpCount()));
        tvZan.setSelected(model.getGiveLike() > 0);
    }

    private BaseViewHolder getVidewHolder(int position) {
        if (recycle_video.getChildCount() <= 0) {
            return null;
        }
        View v = recycle_video.getLayoutManager().findViewByPosition(position);
        if (v == null) {
            return null;
        }
        return (BaseViewHolder) recycle_video.getChildViewHolder(v);

    }

    @Override
    public void onFinshOrLoadMore(boolean isRefrsh) {
        if (isRefrsh) {
            refresh_video.finishRefresh();
        } else {
            refresh_video.finishLoadMore();
        }
    }

    public void scrollToTop() {
        recycle_video.smoothScrollToPosition(0);
        recycle_video.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenter.getList(true);
            }
        },500);
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        mView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (parentFragment!=null){
                    parentFragment.isShowRefresh(!isTop);
                }
            }
        },500);
    }
}
