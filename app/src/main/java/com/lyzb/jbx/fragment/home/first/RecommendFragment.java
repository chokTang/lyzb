package com.lyzb.jbx.fragment.home.first;

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
import com.like.utilslib.app.AppUtil;
import com.like.utilslib.image.BitmapUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.circle.DynamicCircleListAdapter;
import com.lyzb.jbx.adapter.home.first.RecommendAdapter;
import com.lyzb.jbx.api.UrlConfig;
import com.lyzb.jbx.dialog.FollowRemindDialog;
import com.lyzb.jbx.fragment.base.BaseVideoStatusFrgament;
import com.lyzb.jbx.fragment.circle.CircleDetailFragment;
import com.lyzb.jbx.fragment.dynamic.DynamicDetailFragment;
import com.lyzb.jbx.fragment.home.HomeFragment;
import com.lyzb.jbx.fragment.me.card.CardFragment;
import com.lyzb.jbx.fragment.school.BusinessSchoolListFragment;
import com.lyzb.jbx.inter.IRecycleAnyClickListener;
import com.lyzb.jbx.model.account.FunctionModel;
import com.lyzb.jbx.model.common.RecommentBannerModel;
import com.lyzb.jbx.model.dynamic.DynamicModel;
import com.lyzb.jbx.model.eventbus.DynamicItemStatusEventBus;
import com.lyzb.jbx.model.follow.InterestMemberModel;
import com.lyzb.jbx.mvp.presenter.home.first.HomeRecommendPresenter;
import com.lyzb.jbx.mvp.view.home.first.IHomeRecommendView;
import com.lyzb.jbx.util.AppCommonUtil;
import com.lyzb.jbx.util.AppPreference;
import com.lyzb.jbx.util.MediaPlayerUtil;
import com.lyzb.jbx.webscoket.BaseClient;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.szy.yishopcustomer.Activity.LoginActivity;
import com.szy.yishopcustomer.Activity.ProjectH5Activity;
import com.szy.yishopcustomer.Activity.RootActivity;
import com.szy.yishopcustomer.Activity.YSCWebViewActivity;
import com.szy.yishopcustomer.Activity.im.ImCommonActivity;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.BrowserUrlManager;
import com.szy.yishopcustomer.ViewModel.im.ImHeaderGoodsModel;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * 首页-推荐列表
 */
public class RecommendFragment extends BaseVideoStatusFrgament<HomeRecommendPresenter> implements IHomeRecommendView,
        OnRefreshLoadMoreListener {

    private SmartRefreshLayout refresh_recommend;
    private RecyclerView recycle_recommond_list;
    private RecommendAdapter mAdapter;

    private HomeFragment parentFragment;
    private int mPosition = -1;

    @Override
    public Integer getMainResId() {
        return R.layout.fragment_recommend_home;
    }

    @Override
    public void onLazyRequest() {
        onAgainRequest();
    }

    @Override
    public void onAgainRequest() {
        mPresenter.getInterestList(true);
        mPresenter.getBannerList();
        mPresenter.getFollowDynamicList(true);
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        super.onInitView(savedInstanceState);
        refresh_recommend = (SmartRefreshLayout) main_view;
        recycle_recommond_list = findViewById(R.id.recycle_recommond_list);

        refresh_recommend.setOnRefreshLoadMoreListener(this);
        recycle_recommond_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        parentFragment = (HomeFragment) getParentFragment();

        //关注列表
        mAdapter = new RecommendAdapter(getContext(), null);
        mAdapter.setFastLayoutManager(recycle_recommond_list);
        recycle_recommond_list.addItemDecoration(new DividerItemDecoration(DividerItemDecoration.VERTICAL_LIST, R.drawable.listdivider_window_10));
        recycle_recommond_list.setAdapter(mAdapter);

        recycle_recommond_list.setRecyclerListener(new RecyclerView.RecyclerListener() {
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

        recycle_recommond_list.addOnItemTouchListener(new OnRecycleItemClickListener() {
            @Override
            public void onItemChildClick(BaseRecyleAdapter adapter, View view, int position) {
                DynamicModel itemModel = mAdapter.getPositionModel(position);
                switch (view.getId()) {
                    //换一换
                    case R.id.tv_recommond_change:
                        mPresenter.getInterestList(false);
                        break;
                    //评论
                    case R.id.layout_comment:
                        if (App.getInstance().isLogin()) {
                            mPosition = position;
                            childDoubleStart(DynamicDetailFragment.Companion.newIntance(itemModel.getId(), true));
                        } else {
                            startActivity(new Intent(getContext(), LoginActivity.class));
                        }
                        break;
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
                    //跳转到圈子详情
                    case R.id.tv_circle_dynamic:
                        childDoubleStart(CircleDetailFragment.newIntance(itemModel.getGroupId()));
                        break;
                    //跳转到个人名片
                    case R.id.img_header:
                        childDoubleStart(CardFragment.newIntance(2, itemModel.getUserId()));
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
                    //分享
                    case R.id.layout_share_number:
                        LinearLayout parentView = (LinearLayout) view.getParent().getParent();
                        Bitmap bitmap = BitmapUtil.createViewBitmap(parentView);
                        AppCommonUtil.startAdapterShare(getContext(), itemModel.getId(), itemModel.getCreateMan(), itemModel.getGsName(), BitmapUtil.bitmap2Bytes(bitmap));
                        break;
                    //播放语音
                    case R.id.img_voice_play:
//                        String url="https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/client/2019/04/27/gZ108Ggqy6.amr";
//                        MediaPlayerUtil.getInstance().play(url);
                        MediaPlayerUtil.getInstance().play(itemModel.getIntroductionAudioFile().get(0).getFilePath(), (ImageView) view);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onItemClick(BaseRecyleAdapter adapter, final View view, final int position) {
                if (position > 1) {
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

        mAdapter.setClickListener(new IRecycleAnyClickListener() {
            @Override
            public void onItemClick(View view, int position, Object item) {
                if (item instanceof FunctionModel) {
                    switch (((FunctionModel) item).getId()) {
                        //会员服务
                        case 1:
                            Intent tgIntent = new Intent(getContext(), ProjectH5Activity.class);
                            tgIntent.putExtra(Key.KEY_URL.getValue(), UrlConfig.H5_EXTENSION);
                            startActivity(tgIntent);
                            break;
                        //共商学院
                        case 2:
                            childDoubleStart(new BusinessSchoolListFragment());
                            break;
                        //集宝箱商家
                        case 3:
                            if (AppUtil.isAvilible("com.lyzb.jbxsj", getContext())) {
                                Intent intent = getContext().getPackageManager()
                                        .getLaunchIntentForPackage("com.lyzb.jbxsj");
                                startActivity(intent);
                            } else {
                                Intent pfIntent = new Intent(getContext(), ProjectH5Activity.class);
                                pfIntent.putExtra(Key.KEY_URL.getValue(), UrlConfig.H5_MERCHANTS);
                                startActivity(pfIntent);
                            }
                            break;
                        //集宝箱商城
                        case 4:
                            startActivity(new Intent(getContext(), RootActivity.class));
                            break;
                        //批发市场
                        case 5:
                            Intent pfIntent = new Intent(getContext(), YSCWebViewActivity.class);
                            pfIntent.putExtra(Key.KEY_URL.getValue(), UrlConfig.H5_MARKET);
                            startActivity(pfIntent);
                            break;
                        default:
                            break;
                    }
                } else if (item instanceof InterestMemberModel) {//可能感兴趣的人
                    switch (view.getId()) {
                        //点击语音
                        case R.id.img_voice_play:
                            MediaPlayerUtil.getInstance().play(((InterestMemberModel) item).getIntroductionAudioFile().get(0).getFilePath(), (ImageView) view);
                            break;
                        default:
                            childDoubleStart(CardFragment.newIntance(2, ((InterestMemberModel) item).getUserId()));
                            break;
                    }
                } else {//表示轮播
                    RecommentBannerModel model = (RecommentBannerModel) item;
                    if (TextUtils.isEmpty(model.getLinkUrl())) {
                        return;
                    }
                    new BrowserUrlManager(model.getLinkUrl()).parseUrl(getActivity(), model.getLinkUrl());
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
    public void onInterestMember(List<InterestMemberModel> list) {
        mAdapter.noticeHeaderSecondData(list);
    }

    @Override
    public void onBannerList(List<RecommentBannerModel> list) {
        mAdapter.noticeHeaderFirstData(list, parentFragment.entranceList);
    }

    /**
     * 通知功能模块刷新
     *
     * @param models
     */
    public void onNoticeBannerList(List<FunctionModel> models) {
        mAdapter.noticeFunctionList(models);
    }

    @Override
    public void onFinshRefresh(boolean isfresh) {
        if (isfresh) {
            refresh_recommend.finishRefresh();
        } else {
            refresh_recommend.finishLoadMore();
        }
    }

    @Override
    public void onDynamicList(boolean isfresh, List<DynamicModel> list) {
        if (isfresh) {
            refresh_recommend.finishRefresh();
            mAdapter.update(list);
            if (list.size() < 10) {
                refresh_recommend.finishLoadMoreWithNoMoreData();
            }
        } else {
            mAdapter.addAll(list);
            if (list.size() < 10) {
                refresh_recommend.finishLoadMoreWithNoMoreData();
            } else {
                refresh_recommend.finishLoadMore();
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
    public void onStart() {
        super.onStart();
        if (mAdapter.getmBanner() != null) {
            mAdapter.getmBanner().startAutoPlay();
        }
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        MediaPlayerUtil.getInstance().stop();
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        mView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (parentFragment != null) {
                    parentFragment.isShowRefresh(!isTop);
                }
            }
        }, 500);
        mPosition = -1;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAdapter.getmBanner() != null) {
            mAdapter.getmBanner().stopAutoPlay();
        }
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
        recycle_recommond_list.smoothScrollToPosition(0);
        recycle_recommond_list.postDelayed(new Runnable() {
            @Override
            public void run() {
                onAgainRequest();
            }
        }, 500);
    }
}
