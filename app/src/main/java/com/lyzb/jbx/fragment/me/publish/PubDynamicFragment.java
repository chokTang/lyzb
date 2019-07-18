package com.lyzb.jbx.fragment.me.publish;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.DividerItemDecoration;
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.me.publish.PubDycAdapter;
import com.lyzb.jbx.fragment.base.BaseVideoFrgament;
import com.lyzb.jbx.fragment.dynamic.DynamicDetailFragment;
import com.lyzb.jbx.model.dynamic.DynamicFileModel;
import com.lyzb.jbx.model.dynamic.DynamicModel;
import com.lyzb.jbx.model.dynamic.RemoveModel;
import com.lyzb.jbx.model.dynamic.RequestRemoveDynamic;
import com.lyzb.jbx.mvp.presenter.me.PubDynamicPresenter;
import com.lyzb.jbx.mvp.view.me.IPubDynamicView;
import com.lyzb.jbx.util.ImageUtil;
import com.lyzb.jbx.widget.CusPopWindow;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.szy.yishopcustomer.Activity.LoginActivity;
import com.szy.yishopcustomer.Util.App;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wyx
 * @role 我的发表-动态
 * @time 2019 2019/3/6 17:40
 */

public class PubDynamicFragment extends BaseVideoFrgament<PubDynamicPresenter> implements IPubDynamicView, OnRefreshLoadMoreListener {

    @BindView(R.id.sr_un_me_pub_dynamic)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.recy_un_me_pub_dynamic)
    RecyclerView dynamicRecy;
    @BindView(R.id.empty_view)
    View empty_view;

    CusPopWindow popWindow;
    TextView cancel, delete;
    private PubDycAdapter mAdapter = null;

    @Override
    public Object getResId() {
        return R.layout.fragment_un_me_pub_dynamic;
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, mView);
        if (popWindow == null) {
            View contenView = LayoutInflater.from(getContext()).inflate(R.layout.layout_remove, null, false);
            cancel = (TextView) contenView.findViewById(R.id.tv_cancel);
            delete = (TextView) contenView.findViewById(R.id.tv_delete);

            popWindow = new CusPopWindow.PopupWindowBuilder(getActivity())
                    .setView(contenView)
                    .setOutsideTouchable(true)
                    .create();
        }
        mRefreshLayout.setOnLoadMoreListener(this);

        mAdapter = new PubDycAdapter(getContext(), null);
        mAdapter.setLayoutManager(dynamicRecy);
        dynamicRecy.addItemDecoration(new DividerItemDecoration(DividerItemDecoration.VERTICAL_LIST, R.drawable.listdivider_window_10));
        dynamicRecy.setAdapter(mAdapter);

        dynamicRecy.setRecyclerListener(new RecyclerView.RecyclerListener() {
            @Override
            public void onViewRecycled(RecyclerView.ViewHolder holder) {
                if (holder instanceof PubDycAdapter.VideoHolder) {
                    NiceVideoPlayer niceVideoPlayer = ((PubDycAdapter.VideoHolder) holder).videoPlayer;
                    if (niceVideoPlayer == NiceVideoPlayerManager.instance().getCurrentNiceVideoPlayer()) {
                        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
                    }
                }
            }
        });
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        mPresenter.getList(true);
        dynamicRecy.addOnItemTouchListener(new OnRecycleItemClickListener() {
            @Override
            public void onItemClick(BaseRecyleAdapter adapter, View view, int position) {
                super.onItemClick(adapter, view, position);

                DynamicModel model = (DynamicModel) adapter.getPositionModel(position);
                childStart(DynamicDetailFragment.Companion.newIntance(model.getId()));
            }

            @Override
            public void onItemChildClick(final BaseRecyleAdapter adapter, View view, final int position) {
                switch (view.getId()) {
                    case R.id.ll_pop:
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            popWindow.showAsDropDown(view,0, 0, Gravity.TOP);
                        }

                        delete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                RequestRemoveDynamic bean = new RequestRemoveDynamic();
                                bean.setId(((DynamicModel) adapter.getPositionModel(position)).getId());
                                if (App.getInstance().isLogin()) {
                                    mPresenter.removeDynamic(bean, position);
                                } else {
                                    startActivity(new Intent(getActivity(), LoginActivity.class));
                                }
                            }
                        });
                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popWindow.dissmiss();
                            }
                        });
                        break;

                    //一张图的时候 加载大图
                    case R.id.layout_first:
                        List<String> urllist = new ArrayList<>();
                        for (int i = 0; i < mAdapter.getPositionModel(position).getFileList().size(); i++) {
                            DynamicFileModel model = (DynamicFileModel) mAdapter.getPositionModel(position).getFileList().get(i);
                            urllist.add(model.getFile());
                        }
                        ImageUtil.Companion.statPhotoViewActivity(getContext(), 0, urllist);
                        break;
                    //点赞
                    case R.id.tv_zan_number:
                        if (mAdapter.getPositionModel(position).getGiveLike() > 0) {
                            mPresenter.onCancleZan(mAdapter.getPositionModel(position).getId(), position);
                        } else {
                            view.startAnimation(AnimationUtils.loadAnimation(
                                    getContext(), R.anim.zan_anim));
                            mPresenter.onZan(mAdapter.getPositionModel(position).getId(), position);
                        }
                        break;
                }
            }
        });
    }

    @Override
    public void onDataList(boolean isRefresh, List<DynamicModel> dynamicModelList) {
        if (isRefresh) {
            mAdapter.update(dynamicModelList);
            mRefreshLayout.finishRefresh();
            if (dynamicModelList.size() < 10) {
                mRefreshLayout.finishLoadMoreWithNoMoreData();
            }
        } else {
            mAdapter.addAll(dynamicModelList);
            if (dynamicModelList.size() < 10) {
                mRefreshLayout.finishLoadMoreWithNoMoreData();
            } else {
                mRefreshLayout.finishLoadMore();
            }
        }
        if (mAdapter.getItemCount() == 0) {
            empty_view.setVisibility(View.VISIBLE);
        } else {
            empty_view.setVisibility(View.GONE);
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
    public void onRemoveResult(RemoveModel bean, int position) {
        mAdapter.remove(position);
        popWindow.dissmiss();
        if (mAdapter.getItemCount() == 0) {
            empty_view.setVisibility(View.VISIBLE);
        } else {
            empty_view.setVisibility(View.GONE);
        }
    }

    @Override
    public void onZanResult(int position) {
        DynamicModel model = mAdapter.getPositionModel(position);
        model.setGiveLike(model.getGiveLike() == 0 ? 1 : 0);
        model.setUpCount(model.getGiveLike() > 0 ? model.getUpCount() + 1 : model.getUpCount() - 1);
        mAdapter.change(position, model);
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
    public boolean isDelayedData() {
        return false;
    }
}
