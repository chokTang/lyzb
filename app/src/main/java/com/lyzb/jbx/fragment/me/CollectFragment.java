package com.lyzb.jbx.fragment.me;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.DividerItemDecoration;
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener;
import com.like.utilslib.image.BitmapUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.me.CollectAdapter;
import com.lyzb.jbx.fragment.base.BaseVideoToolbarFrgament;
import com.lyzb.jbx.fragment.dynamic.DynamicDetailFragment;
import com.lyzb.jbx.fragment.me.card.CardFragment;
import com.lyzb.jbx.model.dynamic.DynamicFileModel;
import com.lyzb.jbx.model.dynamic.DynamicModel;
import com.lyzb.jbx.model.me.CollectModel;
import com.lyzb.jbx.mvp.presenter.me.CollectPresenter;
import com.lyzb.jbx.mvp.view.me.ICollectView;
import com.lyzb.jbx.util.AppCommonUtil;
import com.lyzb.jbx.util.ImageUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wyx
 * @role 我的收藏
 * @time 2019 2019/3/7 16:56
 */

public class CollectFragment extends BaseVideoToolbarFrgament<CollectPresenter> implements ICollectView, OnRefreshLoadMoreListener {

    @BindView(R.id.view_colle_empty)
    View emptyView;

    @BindView(R.id.sf_union_me_collect)
    SmartRefreshLayout collectLayout;
    @BindView(R.id.recy_union_me_collect)
    RecyclerView collectRecy;

    private CollectAdapter mAdapter = null;

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        super.onInitView(savedInstanceState);
        onBack();
        setToolbarTitle("我的收藏");
        ButterKnife.bind(this, mView);

        collectLayout.setOnRefreshListener(this);
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        mAdapter = new CollectAdapter(getContext(), null);
        mAdapter.setLayoutManager(collectRecy);
        collectRecy.addItemDecoration(new DividerItemDecoration(DividerItemDecoration.VERTICAL_LIST, R.drawable.listdivider_window_10));

        collectRecy.setAdapter(mAdapter);
        collectRecy.addOnItemTouchListener(new OnRecycleItemClickListener() {
            @Override
            public void onItemClick(BaseRecyleAdapter adapter, final View view, int position) {
                final CollectModel model = mAdapter.getPositionModel(position);
                view.setTag("");
                view.postDelayed(new Runnable() {//处理item中的特殊点击
                    @Override
                    public void run() {
                        Object tag = view.getTag();
                        if (tag == null || TextUtils.isEmpty(tag.toString())) {
                            start(DynamicDetailFragment.Companion.newIntance(model.getTopicId()));
                        }
                    }
                }, 50);
            }

            @Override
            public void onItemChildClick(BaseRecyleAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    //赞
                    case R.id.layout_zan_number:
                        if (mAdapter.getPositionModel(position).getGiveLike() > 0) {
                            mPresenter.onCancleZan(mAdapter.getPositionModel(position).getTopicId(), position);
                        } else {
                            mPresenter.onZan(mAdapter.getPositionModel(position).getTopicId(), position);
                        }
                        break;
                    //一张图的时候 加载大图
                    case R.id.layout_first:
                        List<String> urllist = new ArrayList<>();
                        for (int i = 0; i < mAdapter.getPositionModel(position).getGsTopicFileList().size(); i++) {
                            DynamicFileModel model = (DynamicFileModel) mAdapter.getPositionModel(position).getGsTopicFileList().get(i);
                            urllist.add(model.getFile());
                        }
                        ImageUtil.Companion.statPhotoViewActivity(getContext(), 0, urllist);
                        break;
                    //已关注
                    case R.id.tv_follow:
                        mPresenter.onDynamciFollowUser(mAdapter.getPositionModel(position).getUserId(), 0, position);
                        break;
                    //未关注
                    case R.id.tv_no_follow:
                        mPresenter.onDynamciFollowUser(mAdapter.getPositionModel(position).getUserId(), 1, position);
                        break;
                    //点击头像
                    case R.id.img_header:
                        start(CardFragment.newIntance(2, mAdapter.getPositionModel(position).getUserId()));
                        break;
                    //分享
                    case R.id.layout_share_number:
                        LinearLayout parentView = (LinearLayout) view.getParent().getParent();
                        Bitmap bitmap = BitmapUtil.createViewBitmap(parentView);
                        AppCommonUtil.startAdapterShare(getContext(), mAdapter.getPositionModel(position).getTopicId(), mAdapter.getPositionModel(position).getCreateMan(),
                                mAdapter.getPositionModel(position).getGsName(), BitmapUtil.bitmap2Bytes(bitmap));
                        break;
                    //播放视频
                    case R.id.video_play:
                        break;
                    default:
                        break;
                }
            }
        });
        mPresenter.getFollowDynamicList(true);
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_union_me_collect;
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getFollowDynamicList(false);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getFollowDynamicList(true);
    }

    @Override
    public void onFinshRefresh(boolean isfresh) {
        if (isfresh) {
            collectLayout.finishRefresh();
        } else {
            collectLayout.finishLoadMore();
        }
    }

    @Override
    public void onDynamicList(boolean isfresh, List<DynamicModel> list) {
        //弃用
    }

    @Override
    public void onCollectList(boolean isfresh, List<CollectModel> dynamicModelList) {
        if (isfresh) {
            mAdapter.update(dynamicModelList);
            collectLayout.finishRefresh();
            if (dynamicModelList.size() < 10) {
                collectLayout.finishLoadMoreWithNoMoreData();
            }
        } else {
            mAdapter.addAll(dynamicModelList);
            if (dynamicModelList.size() < 10) {
                collectLayout.finishLoadMoreWithNoMoreData();
            } else {
                collectLayout.finishLoadMore();
            }
        }

        if (mAdapter.getItemCount() == 0) {
            emptyView.setVisibility(View.VISIBLE);
        } else {
            emptyView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onZanResult(int position) {
        CollectModel model = mAdapter.getPositionModel(position);
        model.setGiveLike(model.getGiveLike() == 0 ? 1 : 0);
        model.setUpCount(model.getGiveLike() > 0 ? model.getUpCount() + 1 : model.getUpCount() - 1);
        mAdapter.change(position, model);
    }

    @Override
    public void onFollowItemResult(int position) {
        CollectModel model = mAdapter.getPositionModel(position);
        model.setRelationNum(model.getRelationNum() == 0 ? 1 : 0);
        mAdapter.change(position, model);
    }
}
