package com.lyzb.jbx.fragment.dynamic;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.hyphenate.easeui.EaseConstant;
import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.DividerItemDecoration;
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener;
import com.like.longshaolib.base.BaseFragment;
import com.like.utilslib.image.BitmapUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.circle.DynamicCircleListAdapter;
import com.lyzb.jbx.dialog.FollowRemindDialog;
import com.lyzb.jbx.fragment.circle.CircleDetailFragment;
import com.lyzb.jbx.fragment.me.card.CardFragment;
import com.lyzb.jbx.model.dynamic.DynamicModel;
import com.lyzb.jbx.model.eventbus.DynamicItemStatusEventBus;
import com.lyzb.jbx.mvp.presenter.dynamic.DynamicListPresenter;
import com.lyzb.jbx.mvp.view.dynamic.IDynamicListView;
import com.lyzb.jbx.util.AppCommonUtil;
import com.lyzb.jbx.util.AppPreference;
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
 * 动态列表
 * Created by shidengzhong on 2019/3/4.
 */

public class DynamicListFragment extends BaseFragment<DynamicListPresenter> implements IDynamicListView, OnRefreshLoadMoreListener {

    //参数
    private static String PARAM_KEY = "PARAM_KEY";
    private String mSearchValue = "";

    private SmartRefreshLayout refresh_dynamic;
    private RecyclerView recycle_dynamic_list;
    private View empty_view;
    private DynamicCircleListAdapter mAdapter;

    private int mPosition = -1;

    public static DynamicListFragment newIntance(String value) {
        DynamicListFragment fragment = new DynamicListFragment();
        Bundle args = new Bundle();
        args.putString(PARAM_KEY, value);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mSearchValue = args.getString(PARAM_KEY);
        }
        EventBus.getDefault().register(this);
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_dynamic_list;
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        refresh_dynamic = findViewById(R.id.refresh_dynamic);
        recycle_dynamic_list = findViewById(R.id.recycle_dynamic_list);
        empty_view = findViewById(R.id.empty_view);

        refresh_dynamic.setOnRefreshLoadMoreListener(this);

        mAdapter = new DynamicCircleListAdapter(getContext(), null);
        mAdapter.setLayoutManager(recycle_dynamic_list);
        recycle_dynamic_list.addItemDecoration(new DividerItemDecoration(DividerItemDecoration.VERTICAL_LIST, R.drawable.listdivider_gray_10));
        recycle_dynamic_list.setAdapter(mAdapter);
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        recycle_dynamic_list.addOnItemTouchListener(new OnRecycleItemClickListener() {
            @Override
            public void onItemChildClick(BaseRecyleAdapter adapter, View view, int position) {
                DynamicModel itemModel = mAdapter.getPositionModel(position);
                switch (view.getId()) {
                    //评论
                    case R.id.layout_comment:
                        if (App.getInstance().isLogin()) {
                            mPosition = position;
                            childStart(DynamicDetailFragment.Companion.newIntance(itemModel.getId(), true));
                        } else {
                            startActivity(new Intent(getContext(), LoginActivity.class));
                        }
                        break;
                    //赞
                    case R.id.layout_zan_number:
                        if (App.getInstance().isLogin()) {
                            if (mAdapter.getPositionModel(position).getGiveLike() > 0) {
                                mPresenter.onCancleZan(itemModel.getId(), position);
                            } else {
                                mPresenter.onZan(itemModel.getId(), position);
                                view.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.zan_anim));
                            }
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
                        childStart(CircleDetailFragment.newIntance(itemModel.getGroupId()));
                        break;
                    //跳转到个人名片
                    case R.id.img_header:
                        childStart(CardFragment.newIntance(2, itemModel.getUserId()));
                        break;
                    //分享
                    case R.id.layout_share_number:
                        LinearLayout parentView = (LinearLayout) view.getParent().getParent();
                        Bitmap bitmap = BitmapUtil.createViewBitmap(parentView);
                        AppCommonUtil.startAdapterShare(getContext(), itemModel.getId(), itemModel.getCreateMan(), itemModel.getGsName(), BitmapUtil.bitmap2Bytes(bitmap));
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
                                childStart(DynamicDetailFragment.Companion.newIntance(mAdapter.getPositionModel(position).getId()));
                            }
                        }
                    }, 50);

                }
            }
        });

        recycle_dynamic_list.setRecyclerListener(new RecyclerView.RecyclerListener() {
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
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getList(false, mSearchValue);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getList(true, mSearchValue);
    }

    /**
     * 搜索发生改变时候调用
     *
     * @param value
     */
    public void notifySeacrhValue(String value) {
        mSearchValue = value;
        mPresenter.getList(true, mSearchValue);
    }

    @Override
    public void onFinshRefresh(boolean isfresh) {
        if (isfresh) {
            refresh_dynamic.finishRefresh();
        } else {
            refresh_dynamic.finishLoadMore();
        }
    }

    @Override
    public void onDynamicList(boolean isfresh, List<DynamicModel> list) {
        if (isfresh) {
            refresh_dynamic.finishRefresh();
            mAdapter.update(list);
            if (list.size() < 10) {
                refresh_dynamic.finishLoadMoreWithNoMoreData();
            }
        } else {
            mAdapter.addAll(list);
            if (list.size() < 10) {
                refresh_dynamic.finishLoadMoreWithNoMoreData();
            } else {
                refresh_dynamic.finishLoadMore();
            }
        }
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void notifyItemChange(DynamicItemStatusEventBus eventBus) {
        if (mPosition > -1) {
            DynamicModel itemModel = mAdapter.getPositionModel(mPosition);
            itemModel.setRelationNum(eventBus.isFollow() ? 1 : 0);
            itemModel.setGiveLike(eventBus.isZan() ? 1 : 0);
            itemModel.setUpCount(eventBus.isZan() ? itemModel.getUpCount() + 1 : itemModel.getUpCount() - 1);
            itemModel.setCommentCount(eventBus.getCommentNumber());
            mAdapter.change(mPosition, itemModel);
        }
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        mPosition = -1;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
