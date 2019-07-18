package com.lyzb.jbx.fragment.home.first;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hyphenate.easeui.EaseConstant;
import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.DividerItemDecoration;
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener;
import com.like.utilslib.image.BitmapUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.circle.DynamicCircleListAdapter;
import com.lyzb.jbx.adapter.home.first.CircleAdapter;
import com.lyzb.jbx.dialog.FollowRemindDialog;
import com.lyzb.jbx.fragment.base.BaseVideoStatusFrgament;
import com.lyzb.jbx.fragment.circle.CircleDetailFragment;
import com.lyzb.jbx.fragment.circle.MoreCircleFragment;
import com.lyzb.jbx.fragment.dynamic.DynamicDetailFragment;
import com.lyzb.jbx.fragment.home.HomeFragment;
import com.lyzb.jbx.fragment.me.card.CardFragment;
import com.lyzb.jbx.inter.IRecycleAnyClickListener;
import com.lyzb.jbx.model.circle.CircleModel;
import com.lyzb.jbx.model.dynamic.DynamicModel;
import com.lyzb.jbx.model.eventbus.DynamicItemStatusEventBus;
import com.lyzb.jbx.mvp.presenter.circle.HomeCirclePresenter;
import com.lyzb.jbx.mvp.view.circle.IHomeCircleView;
import com.lyzb.jbx.util.AppCommonUtil;
import com.lyzb.jbx.util.AppPreference;
import com.lyzb.jbx.util.MediaPlayerUtil;
import com.lyzb.jbx.webscoket.BaseClient;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.szy.yishopcustomer.Activity.LoginActivity;
import com.szy.yishopcustomer.Activity.im.ImCommonActivity;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.ViewModel.im.ImHeaderGoodsModel;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * 主页--圈子
 */
public class HomeCircleFragment extends BaseVideoStatusFrgament<HomeCirclePresenter> implements IHomeCircleView,
        OnRefreshLoadMoreListener {

    private SmartRefreshLayout refresh_circle;
    private RecyclerView recycle_circle_list;
    private CircleAdapter mAdapter;
    private int mPosition = -1;

    private HomeFragment parentFragment;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        parentFragment = (HomeFragment) getParentFragment();
    }

    @Override
    public Integer getMainResId() {
        return R.layout.fragment_circle_home;
    }

    @Override
    public void onLazyRequest() {
        mPresenter.getFollowDynamicList(true);
    }

    @Override
    public void onAgainRequest() {
        mPresenter.getMyInjoinCirlce();
        mPresenter.getFollowDynamicList(true);
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        super.onInitView(savedInstanceState);
        refresh_circle = (SmartRefreshLayout) main_view;
        recycle_circle_list = findViewById(R.id.recycle_circle_list);

        refresh_circle.setOnRefreshLoadMoreListener(this);
        recycle_circle_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (parentFragment != null) {
                    if (layoutManager.findFirstVisibleItemPosition() > 0) {
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
        EventBus.getDefault().register(this);
        mAdapter = new CircleAdapter(getContext(), null);
        recycle_circle_list.setAdapter(mAdapter);
        mAdapter.setFastLayoutManager(recycle_circle_list);
        recycle_circle_list.addItemDecoration(new DividerItemDecoration(DividerItemDecoration.VERTICAL_LIST, R.drawable.listdivider_window_10));

        recycle_circle_list.setRecyclerListener(new RecyclerView.RecyclerListener() {
            @Override
            public void onViewRecycled(RecyclerView.ViewHolder holder) {
                if (holder instanceof DynamicCircleListAdapter.VideoHolder) {
                    NiceVideoPlayer niceVideoPlayer = ((DynamicCircleListAdapter.VideoHolder) holder).videoPlayer;
                    if (niceVideoPlayer == NiceVideoPlayerManager.instance().getCurrentNiceVideoPlayer()) {
                        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
                    }
                }
            }
        });

        recycle_circle_list.addOnItemTouchListener(new OnRecycleItemClickListener() {
            @Override
            public void onItemChildClick(BaseRecyleAdapter adapter, View view, int position) {
                DynamicModel itemModel = mAdapter.getPositionModel(position);
                switch (view.getId()) {
                    //赞
                    case R.id.layout_zan_number:
                        if (App.getInstance().isLogin()) {
                            if (itemModel.getGiveLike() > 0) {
                                mPresenter.onCancleZan(itemModel.getId(), position);
                            } else {
                                view.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.zan_anim));
                                mPresenter.onZan(itemModel.getId(), position);
                            }
                        } else {
                            startActivity(new Intent(getContext(), LoginActivity.class));
                        }
                        break;
                    //评论
                    case R.id.layout_comment:
                        if (App.getInstance().isLogin()) {
                            mPosition =position;
                            childDoubleStart(DynamicDetailFragment.Companion.newIntance(itemModel.getId(), true));
                        } else {
                            startActivity(new Intent(getContext(), LoginActivity.class));
                        }
                        break;
                    //已关注
                    case R.id.tv_follow:
                        if (App.getInstance().isLogin()) {
                            mPresenter.onDynamciFollowUser(itemModel.getCreateMan(), 0, position);
                        } else {
                            startActivity(new Intent(getContext(), LoginActivity.class));
                        }
                        break;
                    //未关注
                    case R.id.tv_no_follow:
                        if (App.getInstance().isLogin()) {
                            if (itemModel.getRelationNum() > 0) {//在线沟通
                                Intent intent = new Intent(getContext(), ImCommonActivity.class);
                                ImHeaderGoodsModel model = new ImHeaderGoodsModel();

                                model.setChatType(EaseConstant.CHATTYPE_SINGLE);
                                model.setShopImName("jbx" + itemModel.getCreateMan());
                                model.setShopName(itemModel.getGsName());
                                model.setShopHeadimg(itemModel.getHeadimg());
                                model.setShopId("");

                                Bundle args = new Bundle();
                                args.putSerializable(ImCommonActivity.PARAMS_GOODS, model);
                                intent.putExtras(args);
                                startActivity(intent);
                            } else {//关注某人
                                mPresenter.onDynamciFollowUser(itemModel.getCreateMan(), 1, position);
                            }
                        } else {
                            startActivity(new Intent(getContext(), LoginActivity.class));
                        }
                        break;
                    //跳转到圈子详情
                    case R.id.tv_circle_dynamic:
                        childDoubleStart(CircleDetailFragment.newIntance(itemModel.getGroupId()));
                        break;
                    //跳转到个人名片
                    case R.id.img_header:
                        childDoubleStart(CardFragment.newIntance(2, itemModel.getUserId()));
                        break;
                    //分享
                    case R.id.layout_share_number:
                        LinearLayout parentView = (LinearLayout) view.getParent().getParent();
                        Bitmap bitmap = BitmapUtil.createViewBitmap(parentView);
                        AppCommonUtil.startAdapterShare(getContext(), itemModel.getId(), itemModel.getCreateMan(), itemModel.getGsName(), BitmapUtil.bitmap2Bytes(bitmap));
                        break;
                    //播放语音
                    case R.id.img_voice_play:
                        MediaPlayerUtil.getInstance().play(itemModel.getIntroductionAudioFile().get(0).getFilePath(), (ImageView) view);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onItemClick(BaseRecyleAdapter adapter, final View view, final int position) {
                if (position > 0) {
                    view.setTag("");
                    view.postDelayed(new Runnable() {//处理item中的特殊点击
                        @Override
                        public void run() {
                            Object tag = view.getTag();
                            if (tag == null || TextUtils.isEmpty(tag.toString())) {
                                mPosition = position;
                                childDoubleStart(DynamicDetailFragment.Companion.newIntance(mAdapter.getPositionModel(position).getId()));
                            }
                        }
                    }, 50);
                }
            }
        });

        mAdapter.setAnyClickListener(new IRecycleAnyClickListener() {
            @Override
            public void onItemClick(View view, int position, Object item) {
                if (position == 0) {
                    childDoubleStart(new MoreCircleFragment());
                } else {//跳转到圈子主页
                    CircleModel circleModel = (CircleModel) item;
                    childDoubleStart(CircleDetailFragment.newIntance(circleModel.getId()));
                }
            }
        });
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getFollowDynamicList(false);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        onAgainRequest();
    }

    @Override
    public void onMyCircleList(List<CircleModel> list) {
        mAdapter.noticeHeader(list);
    }

    @Override
    public void onFinshRefresh(boolean isfresh) {
        if (isfresh) {
            refresh_circle.finishRefresh();
        } else {
            refresh_circle.finishLoadMore();
        }
    }

    @Override
    public void onDynamicList(boolean isfresh, List<DynamicModel> list) {
        if (isfresh) {
            refresh_circle.finishRefresh();
            mAdapter.update(list);
            if (list.size() < 10) {
                refresh_circle.finishLoadMoreWithNoMoreData();
            }
        } else {
            mAdapter.addAll(list);
            if (list.size() < 10) {
                refresh_circle.finishLoadMoreWithNoMoreData();
            } else {
                refresh_circle.finishLoadMore();
            }
        }
    }

    @Override
    public void onZanResult(int position) {
        DynamicModel model = mAdapter.getPositionModel(position);
        model.setGiveLike(model.getGiveLike() == 0 ? 1 : 0);
        model.setUpCount(model.getGiveLike() > 0 ? model.getUpCount() + 1 : model.getUpCount() - 1);
        mAdapter.change(position, model);

        //记录次数
        if (model.getGiveLike() == 1) {
            model.setViewCount(model.getViewCount() + 1);
            BaseClient.getInstance().setMessage(2, model.getId(), model.getCreateMan());
        }
    }

    @Override
    public void onFollowItemResult(int position) {
        DynamicModel model = mAdapter.getPositionModel(position);
        model.setRelationNum(model.getRelationNum() == 0 ? 1 : 0);
        mAdapter.change(position, model);
        //设置第一次关注成功以后的提示
        if (AppPreference.getIntance().getFirstFollow()) {
            new FollowRemindDialog()
                    .show(getFragmentManager(), "showFollowHint");
        }
    }

    @Override
    public boolean isDelayedData() {
        return false;
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        mView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenter.getMyInjoinCirlce();
                if (parentFragment != null) {
                    parentFragment.isShowRefresh(!isTop);
                }
            }
        }, 500);
        mPosition = -1;
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        MediaPlayerUtil.getInstance().stop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void notifyItemChange(DynamicItemStatusEventBus eventBus) {
        if (eventBus.isLoginOut()) {
            onAgainRequest();
        } else {
            if (mPosition > -1) {
                DynamicModel itemModel = mAdapter.getPositionModel(mPosition);
                itemModel.setRelationNum(eventBus.isFollow() ? 1 : 0);
                itemModel.setGiveLike(eventBus.isZan() ? 1 : 0);
                itemModel.setUpCount(eventBus.isZan() ? itemModel.getUpCount() + 1 : itemModel.getUpCount() - 1);
                itemModel.setCommentCount(eventBus.getCommentNumber());
                mAdapter.change(mPosition, itemModel);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void scrollToTop() {
        recycle_circle_list.smoothScrollToPosition(0);
        recycle_circle_list.postDelayed(new Runnable() {
            @Override
            public void run() {
                onAgainRequest();
            }
        },500);
    }
}
