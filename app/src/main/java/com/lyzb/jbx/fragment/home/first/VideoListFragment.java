package com.lyzb.jbx.fragment.home.first;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.DividerItemDecoration;
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener;
import com.like.utilslib.image.BitmapUtil;
import com.like.utilslib.json.GSONUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.activity.GoodToMeActivity;
import com.lyzb.jbx.activity.SendMessageActivity;
import com.lyzb.jbx.adapter.dynamic.CommentAdapter;
import com.lyzb.jbx.adapter.home.first.VideoListAdapter;
import com.lyzb.jbx.api.UrlConfig;
import com.lyzb.jbx.dialog.SendMessageDialog;
import com.lyzb.jbx.fragment.base.BaseVideoFrgament;
import com.lyzb.jbx.fragment.me.card.CardFragment;
import com.lyzb.jbx.inter.IRecycleAnyClickListener;
import com.lyzb.jbx.model.dynamic.AddCommentOrReplyModel;
import com.lyzb.jbx.model.dynamic.AddLikeOrFollowModel;
import com.lyzb.jbx.model.dynamic.DynamicCommentModel;
import com.lyzb.jbx.model.dynamic.DynamicFocusModel;
import com.lyzb.jbx.model.dynamic.RequestBodyComment;
import com.lyzb.jbx.model.video.HomeVideoModel;
import com.lyzb.jbx.mvp.presenter.home.first.VideoListPresenter;
import com.lyzb.jbx.mvp.view.home.first.IVideoListView;
import com.lyzb.jbx.util.AppCommonUtil;
import com.lyzb.jbx.widget.link.AutoLinkTextView;
import com.lyzb.jbx.widget.slidinguppanel.SlidingUpPanelLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.szy.yishopcustomer.Activity.GoodsActivity;
import com.szy.yishopcustomer.Activity.LoginActivity;
import com.szy.yishopcustomer.Activity.YSCWebViewActivity;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Util.App;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * 视频播放详情页面
 */
public class VideoListFragment extends BaseVideoFrgament<VideoListPresenter> implements IVideoListView {

    //参数
    public static String PARAM_KEY = "PARAM_KEY";
    private static String POSITION = "POSITION";

    private int position;
    private String value;//原数据

    private ImageView btnBack;
    private ImageView btnShare;

    private RecyclerView recycle_video;

    private SlidingUpPanelLayout slidingLayout;

    private PagerSnapHelper mPagerSnapHelper;
    private LinearLayoutManager layoutManager;

    private VideoListAdapter mVideoListAdapter = null;

    //评论
    private SmartRefreshLayout refresh_commit;
    private RecyclerView recycle_commit;
    private CommentAdapter commentAdapter;
    private List<DynamicCommentModel.ListBean> listBeans;
    private SendMessageDialog sendMessageDialog;
    private TextView tvCommentNumber;

    private String topicId;


    private TextView btnCommit;
    private OnVideoListFragmentForResult mOnVideoListFragmentForResult;

    public static VideoListFragment newIntance(String value, int position) {
        VideoListFragment fragment = new VideoListFragment();
        Bundle args = new Bundle();
        args.putString(PARAM_KEY, value);
        args.putInt(POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    public void setOnVideoListFragmentForResult(OnVideoListFragmentForResult mOnVideoListFragmentForResult) {
        this.mOnVideoListFragmentForResult = mOnVideoListFragmentForResult;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            position = bundle.getInt(POSITION, -1);
            value = bundle.getString(PARAM_KEY);
        }
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_video_list;
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        btnBack = findViewById(R.id.btnBack);
        btnShare = findViewById(R.id.btnShare);
        recycle_video = findViewById(R.id.recycle_video);
        slidingLayout = findViewById(R.id.slidingLayout);

        refresh_commit = findViewById(R.id.refresh_video);
        refresh_commit.setEnableRefresh(false);
        recycle_commit = findViewById(R.id.recycle_commit);
        tvCommentNumber = findViewById(R.id.tv_comment_number);//评论数量
        btnCommit = findViewById(R.id.btn_commit);//发布评论
        //初始化评论
        commentAdapter = new CommentAdapter();
        listBeans = new ArrayList<>();

        recycle_commit.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recycle_commit.addItemDecoration(new DividerItemDecoration(DividerItemDecoration.VERTICAL_LIST));
        recycle_commit.setAdapter(commentAdapter);
        refresh_commit.setOnLoadMoreListener(new OnLoadMoreListener() {//评论列表加载更多
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.getCommentList(false, topicId);
            }
        });
        commentAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {//点击事件
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {

                final DynamicCommentModel.ListBean bean = (DynamicCommentModel.ListBean) adapter.getItem(position);
                switch (view.getId()) {
                    case R.id.tv_like:
                        if (App.getInstance().isLogin()) {
                            if (bean.getGiveLike() < 1) {//未选中  点赞
                                mPresenter.addLikeOrFollow(4, bean.getId(), position, 0);
                            } else {
                                mPresenter.cancelLikeOrFollow(4, bean.getId(), position, 0);
                            }
                        } else {
                            startActivity(new Intent(getContext(), LoginActivity.class));
                        }
                        break;
                    case R.id.tv_reply:

                        if (!App.getInstance().isLogin()) {
                            startActivity(new Intent(getContext(), LoginActivity.class));
                        } else {
                            sendMessageDialog = new SendMessageDialog();
                            sendMessageDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                    recycle_video.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            hideSoftInput();
                                        }
                                    }, 100);
                                }
                            });
                            sendMessageDialog.showNow(getFragmentManager(), "sendTag");
                            sendMessageDialog.invoke(new SendMessageDialog.ClickListener() {
                                @Override
                                public void onClick(@NotNull String s) {
                                    if (TextUtils.isEmpty(s)) {
                                        showToast("请输入内容");
                                        return;
                                    }
                                    RequestBodyComment body = new RequestBodyComment();
                                    body.setFileList(null);
                                    RequestBodyComment.GsTopicCommentBean topicCommentBean = new RequestBodyComment.GsTopicCommentBean();
                                    topicCommentBean.setContent(s);
                                    topicCommentBean.setTopicId(bean.getTopicId());
                                    topicCommentBean.setType(1);
                                    topicCommentBean.setReplyId(bean.getId());
                                    body.setGsTopicComment(topicCommentBean);

                                    mPresenter.addCommentOrReply(position, body);
                                }
                            });
                        }
                        break;
                }


            }
        });
        commentAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {//点击进入名片
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                String userId = ((DynamicCommentModel.ListBean) adapter.getItem(position)).getUserId();
                if (userId.equals(App.getInstance().userId)) {
                    start(CardFragment.newIntance(1, userId));
                } else {
                    start(CardFragment.newIntance(2, userId));
                }
            }
        });

        slidingLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                Log.i(TAG, "onPanelSlide, offset " + slideOffset);
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                Log.i(TAG, "onPanelStateChanged " + newState);
                if (newState == SlidingUpPanelLayout.PanelState.EXPANDED || newState == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                    dealVideoPlay(newState);
                }
            }
        });

        slidingLayout.setFadeOnClickListener(new View.OnClickListener() {//关闭下拉框
            @Override
            public void onClick(View v) {
                slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {//返回
            @Override
            public void onClick(View v) {
                if (getBaseActivity() instanceof SendMessageActivity || getBaseActivity() instanceof GoodToMeActivity) {
                    getBaseActivity().finish();
                } else {
                    pop();
                }
            }
        });
        btnShare.setOnClickListener(new View.OnClickListener() {//分享
            @Override
            public void onClick(View v) {
                Log.i(TAG, "currentPosition = " + findRecyclerViewCurrentPosition(recycle_video));
                dealShare();
            }
        });
        btnCommit.setOnClickListener(new View.OnClickListener() {//点击发布评
            @Override
            public void onClick(View v) {
                if (!App.getInstance().isLogin()) {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                    return;
                }
                startActivityForResult(new Intent(getContext(), SendMessageActivity.class), 0x11);
            }
        });

        mPagerSnapHelper = new PagerSnapHelper();
        mPagerSnapHelper.attachToRecyclerView(recycle_video);
        //  初始化视频列表
        mVideoListAdapter = new VideoListAdapter(getContext(), null);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recycle_video.setLayoutManager(layoutManager);
        recycle_video.setAdapter(mVideoListAdapter);
        mVideoListAdapter.setOnPlayingListener(new VideoListAdapter.OnPlayingListener() {//处理还未播放的时弹出评论框
            @Override
            public void onPlaying(int positions) {
                if (slidingLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) {//表法当前是展示状态
                    dealVideoPlay(slidingLayout.getPanelState());
                }
            }
        });
        mVideoListAdapter.setiRecycleAnyClickListener(new IRecycleAnyClickListener() {
            @Override
            public void onItemClick(View view, int position, Object item) {
                if (item instanceof HomeVideoModel.ListBean.GoodsListBean) {//商品点击事件
                    if (App.getInstance().isLogin()) {
                        if (((HomeVideoModel.ListBean.GoodsListBean) item).getCan_buy() == 1) {//能买
                            //跳转商品详情页面
                            Intent mIntent = new Intent();
                            mIntent.setClass(getContext(), GoodsActivity.class);
                            mIntent.putExtra(Key.KEY_GOODS_ID.getValue(), ((HomeVideoModel.ListBean.GoodsListBean) item).getGoods_id());
                            mIntent.putExtra("card_user_id", App.getInstance().userId);
                            startActivity(mIntent);
                        } else {
                            Intent pfIntent = new Intent(getContext(), YSCWebViewActivity.class);
                            pfIntent.putExtra(Key.KEY_URL.getValue(), UrlConfig.GOODS_URL + ((HomeVideoModel.ListBean.GoodsListBean) item).getGoods_id() + "&card_user_id=" + App.getInstance().userId);
                            startActivity(pfIntent);
                        }
                    } else {
                        startActivity(new Intent(getContext(), LoginActivity.class));
                    }
                }
            }
        });

        recycle_video.setRecyclerListener(new RecyclerView.RecyclerListener() {
            @Override
            public void onViewRecycled(RecyclerView.ViewHolder holder) {
                if (holder instanceof VideoListAdapter.VideoHolder) {
                    NiceVideoPlayer niceVideoPlayer = ((VideoListAdapter.VideoHolder) holder).videoPlayer;
                    if (niceVideoPlayer == NiceVideoPlayerManager.instance().getCurrentNiceVideoPlayer()) {
                        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
                    }
                }
            }
        });

        recycle_video.addOnItemTouchListener(new OnRecycleItemClickListener() {
            @Override
            public void onItemChildClick(BaseRecyleAdapter adapter, View view, int position) {
                super.onItemChildClick(adapter, view, position);
                HomeVideoModel.ListBean bean = (HomeVideoModel.ListBean) adapter.getPositionModel(position);
                switch (view.getId()) {
                    case R.id.tv_comment_number://评论

                        if (!bean.getId().equals(topicId)) {
                            mPresenter.getCommentList(true, bean.getId());
                            listBeans.clear();
                            commentAdapter.setNewData(listBeans);
                        }
                        topicId = bean.getId();
                        tvCommentNumber.setText("共" + bean.getCommentCount() + "条评论");
                        slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                        break;
                    case R.id.tv_follow_number://收藏
                        if (App.getInstance().isLogin()) {
                            if (bean.getCollect() < 1) {//收藏
                                mPresenter.addLikeOrFollow(3, bean.getId(), position, 1);
                            } else {
                                mPresenter.cancelLikeOrFollow(3, bean.getId(), position, 1);
                            }
                        } else {
                            startActivity(new Intent(getContext(), LoginActivity.class));
                        }
                        break;
                    case R.id.tv_zan_number://点赞
                        if (App.getInstance().isLogin()) {
                            if (bean.getGiveLike() < 1) {//未选中  点赞
                                mPresenter.addLikeOrFollow(2, bean.getId(), position, 1);
                            } else {
                                mPresenter.cancelLikeOrFollow(2, bean.getId(), position, 1);
                            }
                        } else {
                            startActivity(new Intent(getContext(), LoginActivity.class));
                        }

                        break;
                    case R.id.ic_image://点击头像和名字
                    case R.id.tv_name:
                        String userId = bean.getUserId();
                        if (!TextUtils.isEmpty(userId) && userId.equals(App.getInstance().userId)) {
                            start(CardFragment.newIntance(1, userId));
                        } else {
                            start(CardFragment.newIntance(2, userId));
                        }
                        break;
                    case R.id.tv_attention://关注
                        if (bean.getRelationNum() > 0) {//已经关注  显示已经关注  点击取消关注
                            mPresenter.onFocusUser(0, bean.getCreateMan(), position);
                        } else {//未关注   显示添加关注  点击添加关注
                            mPresenter.onFocusUser(1, bean.getCreateMan(), position);
                        }
                        break;
                    case R.id.btn_expand://点击展开
                        VideoListAdapter.VideoHolder videoHolder = getVidewHolder(position);
                        AutoLinkTextView textView = videoHolder.cdFindViewById(R.id.tv_content_value);
                        TextView btnExpand = videoHolder.cdFindViewById(R.id.btn_expand);
                        HomeVideoModel.ListBean b = mVideoListAdapter.getPositionModel(position);
                        if (b.isOpen()) {
                            textView.setMaxLines(2);
                            btnExpand.setText("展开");
                        } else {
                            btnExpand.setText("收起");
                            textView.setMaxLines(Integer.MAX_VALUE);
                        }
                        b.setOpen(!b.isOpen());
                        break;


                }
            }
        });
        recycle_video.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE://停止滚动
                        View view = mPagerSnapHelper.findSnapView(layoutManager);
                        RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(view);
                        if (viewHolder != null && viewHolder instanceof VideoListAdapter.VideoHolder) {
                            ((VideoListAdapter.VideoHolder) viewHolder).controller.resetShowView();//重新设置显示图层
                            NiceVideoPlayer videoPlayer = ((VideoListAdapter.VideoHolder) viewHolder).videoPlayer;
                            if (!videoPlayer.isPlaying()) {
                                videoPlayer.start();
                                ((VideoListAdapter.VideoHolder) viewHolder).changeProgress.setProgress(0);
                                ((VideoListAdapter.VideoHolder) viewHolder).changeProgress.setSecondaryProgress(0);
                            }
                        }
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING://拖动
                        break;
                    case RecyclerView.SCROLL_STATE_SETTLING://惯性滑动
                        break;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }


    /**
     * 评论列表弹出后处理当前视频，以及分享按钮的显示与隐藏
     *
     * @param newState
     */
    private void dealVideoPlay(SlidingUpPanelLayout.PanelState newState) {
        VideoListAdapter.VideoHolder videoHolder = getVidewHolder(findRecyclerViewCurrentPosition(recycle_video));
        if (videoHolder == null) {
            return;
        }
        if (newState == SlidingUpPanelLayout.PanelState.COLLAPSED) {//评论列表关闭
            btnShare.setVisibility(View.VISIBLE);
            videoHolder.videoPlayer.restart();
        } else if (newState == SlidingUpPanelLayout.PanelState.EXPANDED) {//评论列表展开
            btnShare.setVisibility(View.GONE);
            videoHolder.videoPlayer.pause();
        }
    }


    /**
     * 处理分享
     */
    private void dealShare() {
        int position = findRecyclerViewCurrentPosition(recycle_video);
        if (position == -1) {
            return;
        }
        HomeVideoModel.ListBean bean = mVideoListAdapter.getPositionModel(position);
        VideoListAdapter.VideoHolder videoHolder = getVidewHolder(position);
        if (videoHolder == null) {
            return;
        }
        Bitmap bitmap = BitmapUtil.createViewBitmap(videoHolder.get_view());
        AppCommonUtil.startAdapterShare(getContext(), bean.getId(), bean.getCreateMan(), bean.getGsName(), BitmapUtil.bitmap2Bytes(bitmap));
    }

    /**
     * 查找当前下标显示的的item
     *
     * @param position
     * @return
     */
    private VideoListAdapter.VideoHolder getVidewHolder(int position) {
        if (recycle_video.getChildCount() <= 0) {
            return null;
        }
        View v = recycle_video.getLayoutManager().findViewByPosition(position);
        if (v == null) {
            return null;
        }
        return (VideoListAdapter.VideoHolder) recycle_video.getChildViewHolder(v);

    }


    /**
     * 查找当前显示的的item下标
     *
     * @param recyclerView
     * @return
     */
    private int findRecyclerViewCurrentPosition(RecyclerView recyclerView) {
        if (recyclerView.getChildCount() <= 0) {
            return -1;
        }
        try {
            return ((RecyclerView.LayoutParams) recyclerView.getChildAt(0).getLayoutParams()).getViewAdapterPosition();
        } catch (Exception e) {

        }
        return -1;
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        VideoListAdapter.VideoHolder videoHolder = getVidewHolder(findRecyclerViewCurrentPosition(recycle_video));
        if (videoHolder == null) {
            return;
        }
        videoHolder.videoPlayer.pause();
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        VideoListAdapter.VideoHolder videoHolder = getVidewHolder(findRecyclerViewCurrentPosition(recycle_video));
        if (videoHolder == null) {
            return;
        }
        videoHolder.videoPlayer.start();
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        List<HomeVideoModel.ListBean> listBeans = new ArrayList<>();
        listBeans = GSONUtil.getEntityList(value, HomeVideoModel.ListBean.class);
        mVideoListAdapter.addAll(listBeans);
        recycle_video.scrollToPosition(position);
        recycle_video.postDelayed(new Runnable() {
            @Override
            public void run() {
                VideoListAdapter.VideoHolder videoHolder = getVidewHolder(position);
                if (videoHolder == null) {
                    return;
                }
                videoHolder.videoPlayer.start();
            }
        }, 100);

    }

    @Override
    public void onSuccess(boolean isRefrsh, DynamicCommentModel model) {
        if (isRefrsh) {//刷新
            refresh_commit.finishRefresh();
            listBeans.clear();
            listBeans.addAll(model.getList());
        } else {//加载
            refresh_commit.finishLoadMore();
            listBeans.addAll(model.getList());
            if (model.getList().size() < model.getPageSize()) {//添加完数据
                refresh_commit.finishLoadMoreWithNoMoreData();
            }
        }
        if (listBeans.size() == 0) {//没有数据
            View emptyView = LayoutInflater.from(getContext()).inflate(R.layout.empty_layout, null, false);
            commentAdapter.setEmptyView(emptyView);
        } else {//有数据
            commentAdapter.setNewData(model.getList());
        }
    }

    @Override
    public void onFinshOrLoadMore(boolean isRefrsh) {
        if (isRefrsh) {
            refresh_commit.finishRefresh();
        } else {
            refresh_commit.finishLoadMore();
        }
    }


    @Override
    public void addLikeOrFollow(int status, AddLikeOrFollowModel model, int position, int type) {
        if (type == 0) {//评论列表
            DynamicCommentModel.ListBean bean = commentAdapter.getItem(position);
            bean.setGiveLike(1);
            bean.setGiveNum(bean.getGiveNum() + 1);
            commentAdapter.setData(position, bean);
            commentAdapter.refreshNotifyItemChanged(position);
        } else {
            HomeVideoModel.ListBean bean = mVideoListAdapter.getPositionModel(position);
            VideoListAdapter.VideoHolder videoHolder = getVidewHolder(position);
            if (videoHolder == null) {
                return;
            }
            if (status == 2) {//点赞
                TextView tvZan = videoHolder.cdFindViewById(R.id.tv_zan_number);
                tvZan.setSelected(true);
                bean.setGiveLike(1);
                bean.setUpCount(bean.getUpCount() + 1);
                tvZan.setText(bean.getUpCount() + "");
            } else {//收藏
                TextView tvfollow = videoHolder.cdFindViewById(R.id.tv_follow_number);
                bean.setCollect(1);
                bean.setCollectCount(bean.getCollectCount() + 1);
                tvfollow.setSelected(true);
                tvfollow.setText(bean.getCollectCount() + "");
            }
        }

    }

    @Override
    public void cancleLikeOrFollow(int status, AddLikeOrFollowModel model, int position, int type) {
        if (type == 0) {
            DynamicCommentModel.ListBean bean = commentAdapter.getItem(position);
            bean.setGiveLike(0);
            bean.setGiveNum(bean.getGiveNum() - 1);
            commentAdapter.setData(position, bean);
            commentAdapter.refreshNotifyItemChanged(position);
        } else {
            HomeVideoModel.ListBean bean = mVideoListAdapter.getPositionModel(position);
            VideoListAdapter.VideoHolder videoHolder = getVidewHolder(position);
            if (videoHolder == null) {
                return;
            }
            if (status == 2) {//点赞
                TextView tvZan = videoHolder.cdFindViewById(R.id.tv_zan_number);
                tvZan.setSelected(false);
                bean.setGiveLike(0);
                bean.setUpCount(bean.getUpCount() - 1);
                tvZan.setText(bean.getUpCount() + "");
            } else {//收藏
                TextView tvfollow = videoHolder.cdFindViewById(R.id.tv_follow_number);
                tvfollow.setSelected(false);
                bean.setCollect(0);
                bean.setCollectCount(bean.getCollectCount() - 1);
                tvfollow.setText(bean.getCollectCount() + "");
            }
        }
    }

    @Override
    public void resultFocus(DynamicFocusModel model, int position) {
        if (!App.getInstance().isLogin()) {
            startActivity(new Intent(getContext(), LoginActivity.class));
            return;
        }
        Log.e(TAG, "dealAttention position = " + position);
        VideoListAdapter.VideoHolder videoHolder = getVidewHolder(position);
        if (videoHolder == null) {
            Log.e(TAG, "videoHolder = null");
            return;
        }
        HomeVideoModel.ListBean bean = mVideoListAdapter.getPositionModel(position);
        TextView tvAttention = videoHolder.cdFindViewById(R.id.tv_attention);
        if (model.getEnabled() == 1) {//1 关注成功 0 取消关注成功
            tvAttention.setVisibility(View.GONE);
            bean.setRelationNum(1);
        } else {
            bean.setRelationNum(0);
            tvAttention.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void addCommentOrReply(int position, AddCommentOrReplyModel model) {
        if (model.getGsTopicComment().getType() == 1) {//回复

            sendMessageDialog.dismiss();
            DynamicCommentModel.ListBean.ChiledrenListBean listBean = new DynamicCommentModel.ListBean.ChiledrenListBean();
            listBean.setId(model.getGsTopicComment().getId());
            listBean.setContent(model.getGsTopicComment().getContent());
            listBean.setTopicId(model.getGsTopicComment().getTopicId());
            listBean.setReplyId(model.getGsTopicComment().getReplyId());
            listBean.setType(model.getGsTopicComment().getType());
            listBean.setUserName(model.getGsUserExt().getGsName());
            listBean.setHeadimg(model.getGsUserExt().getHeadimg());
            listBean.setCompanyInfo(model.getGsUserExt().getCompanyInfo());
            listBean.setCreateDate(model.getGsTopicComment().getCreateDate());
            listBean.setUserActionVos(model.getUserVipAction());

            if (commentAdapter.getItem(position).getChiledrenList().size() == 0) {
                List<DynamicCommentModel.ListBean.ChiledrenListBean> listBeans = new ArrayList<>();
                listBeans.clear();
                listBeans.add(0, listBean);
                commentAdapter.getItem(position).setChiledrenList(listBeans);
            } else {
                commentAdapter.getItem(position).getChiledrenList().add(0, listBean);
            }
            commentAdapter.refreshNotifyItemChanged(position);

        } else {
            DynamicCommentModel.ListBean listBean = new DynamicCommentModel.ListBean();
            listBean.setId(model.getGsTopicComment().getId());
            listBean.setContent(model.getGsTopicComment().getContent());
            listBean.setTopicId(model.getGsTopicComment().getTopicId());
            listBean.setReplyId(model.getGsTopicComment().getReplyId());
            listBean.setType(model.getGsTopicComment().getType());
            listBean.setUserName(model.getGsUserExt().getGsName());
            listBean.setHeadimg(model.getGsUserExt().getHeadimg());
            listBean.setCompanyInfo(model.getGsUserExt().getCompanyInfo());
            listBean.setCreateDate(model.getGsTopicComment().getCreateDate());
            listBean.setUserActionVos(model.getUserVipAction());
            listBean.setUserId(App.getInstance().userId);
            if (model.getFileList() != null && model.getFileList().size() > 0) {
                List<DynamicCommentModel.ListBean.FileVoList> fileVoLists = new ArrayList<>();
                for (int i = 0; i < model.getFileList().size(); i++) {
                    DynamicCommentModel.ListBean.FileVoList fileVoList = new DynamicCommentModel.ListBean.FileVoList();
                    fileVoList.setFile(model.getFileList().get(i).getFile());
                    fileVoLists.add(fileVoList);
                }
                listBean.setFileVoList(fileVoLists);
            }
            commentAdapter.addData(0, listBean);
            commentAdapter.refreshNotifyItemChanged(0);
            HomeVideoModel.ListBean bean = mVideoListAdapter.getPositionModel(findRecyclerViewCurrentPosition(recycle_video));
            bean.setCommentCount(bean.getCommentCount() + 1);
            VideoListAdapter.VideoHolder videoHolder = getVidewHolder(findRecyclerViewCurrentPosition(recycle_video));
            if (videoHolder == null) {
                return;
            }
            TextView commit = videoHolder.cdFindViewById(R.id.tv_comment_number);
            commit.setText(String.valueOf(bean.getCommentCount()));
            tvCommentNumber.setText("共" + bean.getCommentCount() + "条评论");

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        Log.e(TAG, "RESULT_OK");
        hideSoftInput();
        switch (requestCode) {
            case 0x11:
                HomeVideoModel.ListBean bean = mVideoListAdapter.getPositionModel(findRecyclerViewCurrentPosition(recycle_video));
                RequestBodyComment bodyComment = (RequestBodyComment) data.getSerializableExtra("RequestBodyComment");
                bodyComment.getGsTopicComment().setType(0);
                bodyComment.getGsTopicComment().setTopicId(bean.getId());
                mPresenter.addCommentOrReply(0, bodyComment);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mOnVideoListFragmentForResult != null) {
            mOnVideoListFragmentForResult.onVideoListFragmentForResult(mVideoListAdapter.getList());
        }
    }

    public interface OnVideoListFragmentForResult {
        void onVideoListFragmentForResult(List<HomeVideoModel.ListBean> list);
    }
}
