package com.lyzb.jbx.fragment.me.card;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.DividerItemDecoration;
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener;
import com.like.utilslib.image.BitmapUtil;
import com.like.utilslib.other.LogUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.dynamic.DynamicListAdapter;
import com.lyzb.jbx.adapter.dynamic.MyCardDynamicListAdapter;
import com.lyzb.jbx.api.UrlConfig;
import com.lyzb.jbx.fragment.base.BaseVideoFrgament;
import com.lyzb.jbx.fragment.dynamic.DynamicDetailFragment;
import com.lyzb.jbx.model.dynamic.DynamicModel;
import com.lyzb.jbx.model.me.CardModel;
import com.lyzb.jbx.mvp.presenter.me.card.CdDynamicPresenter;
import com.lyzb.jbx.mvp.view.me.ICdDynamicView;
import com.lyzb.jbx.util.AppCommonUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.szy.yishopcustomer.Activity.LoginActivity;
import com.szy.yishopcustomer.Activity.ProjectH5Activity;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Util.App;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wyx
 * @role 我(TA)的名片-动态
 * @time 2019 2019/3/4 14:40
 */

public class CardDynamicFragment extends BaseVideoFrgament<CdDynamicPresenter> implements ICdDynamicView, OnLoadMoreListener {

    @BindView(R.id.inc_fans_empty)
    LinearLayout emptyLin;
    @BindView(R.id.empty_tv)
    TextView emptyText;
    private boolean isScore = false;

    @BindView(R.id.lin_dync_number)
    LinearLayout linDync;
    @BindView(R.id.scorllview)
    NestedScrollView scorllview;

    @BindView(R.id.lin_other_dynamic_empty)
    LinearLayout lin_other_dynamic_empty;

    @BindView(R.id.dync_fans_number)
    TextView fansNumber;
    @BindView(R.id.dync_focus_number)
    TextView focusNumber;
    @BindView(R.id.dync_dync_number)
    TextView dyncNumber;

    @BindView(R.id.sf_un_me_card_dynamic)
    SmartRefreshLayout mRefreshLayout;


    @BindView(R.id.recy_un_me_card_dynamic)
    RecyclerView cardDynamicRecy;


    @BindView(R.id.tv_card_upgrade_vip)
    TextView openVip;

    private MyCardDynamicListAdapter mDynamicAdapter = null;

    private CardFragment parentFragment = null;

    @Override
    public Object getResId() {
        return R.layout.fragment_union_card_dynamic;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentFragment = (CardFragment) getParentFragment();
    }


    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, mView);
        mRefreshLayout.setOnLoadMoreListener(this);
        scoreView();
        cardDynamicRecy.setNestedScrollingEnabled(false);
        cardDynamicRecy.setFocusable(false);

        mDynamicAdapter = new MyCardDynamicListAdapter(getContext(), null);
        mDynamicAdapter.setFollow(false);
        mDynamicAdapter.setLayoutManager(cardDynamicRecy);
        cardDynamicRecy.addItemDecoration(new DividerItemDecoration(DividerItemDecoration.VERTICAL_LIST, R.drawable.listdivider_window_10));

        cardDynamicRecy.setAdapter(mDynamicAdapter);

        cardDynamicRecy.addOnItemTouchListener(new OnRecycleItemClickListener() {
            @Override
            public void onItemChildClick(BaseRecyleAdapter adapter, View view, int position) {
                DynamicModel itemModel = mDynamicAdapter.getPositionModel(position);
                switch (view.getId()) {
                    //点赞
                    case R.id.layout_zan_number:
                        if (App.getInstance().isLogin()) {
                            if (parentFragment.getFromType() != 1) {
                                if (mDynamicAdapter.getPositionModel(position).getGiveLike() > 0) {
                                    mPresenter.onCancleZan(mDynamicAdapter.getPositionModel(position).getId(), position);
                                } else {
                                    mPresenter.onZan(mDynamicAdapter.getPositionModel(position).getId(), position);
                                }
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
                    default:
                        break;
                }
            }

            @Override
            public void onItemClick(BaseRecyleAdapter adapter, final View view, final int position) {
                view.setTag("");
                view.postDelayed(new Runnable() {//处理item中的特殊点击
                    @Override
                    public void run() {
                        Object tag = view.getTag();
                        if (tag == null || TextUtils.isEmpty(tag.toString())) {
                            childStart(DynamicDetailFragment.Companion.newIntance(mDynamicAdapter.getPositionModel(position).getId()));
                        }
                    }
                }, 50);
            }
        });

        cardDynamicRecy.setRecyclerListener(new RecyclerView.RecyclerListener() {
            @Override
            public void onViewRecycled(RecyclerView.ViewHolder holder) {
                if (holder instanceof DynamicListAdapter.VideoHolder) {
                    NiceVideoPlayer niceVideoPlayer = ((DynamicListAdapter.VideoHolder) holder).videoPlayer;
                    if (niceVideoPlayer == NiceVideoPlayerManager.instance().getCurrentNiceVideoPlayer()) {
                        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
                    }
                }
            }
        });

        //1:我的智能名片  2:TA的智能名片
        if (parentFragment.getFromType() == 1) {
            mPresenter.getList(true, true, null);

            linDync.setVisibility(View.GONE);
            fansNumber.setText("粉丝" + App.getInstance().cardFansNumber);
            focusNumber.setText("关注" + App.getInstance().cardFocusNumber);
            dyncNumber.setText("动态" + App.getInstance().cardDycNumber);

        } else {
            linDync.setVisibility(View.GONE);
            mPresenter.getList(true, false, parentFragment.getCardUserId());
        }

        openVip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tgIntent = new Intent(getContext(), ProjectH5Activity.class);
                tgIntent.putExtra(Key.KEY_URL.getValue(), UrlConfig.H5_OPEN_VIP);
                startActivity(tgIntent);
            }
        });

        if (parentFragment.getFromType() == 1) {
            if (App.getInstance().isUserVip) {
                openVip.setVisibility(View.GONE);
            } else {
                openVip.setVisibility(View.VISIBLE);
            }
        }else {
            openVip.setVisibility(View.GONE);
        }
    }

    private void scoreView() {
        parentFragment.getAppBar().addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                //当顶部view滑到最底部时候 这时候不管下面view  是否为0 都得允许 上面appbar滑动
                if (parentFragment.getLayout_view().getHeight() == -verticalOffset) {
                    isScore = true;
                } else {
                    isScore = false;
                }
                LogUtil.loge("view的高度" + parentFragment.getLayout_view().getHeight() + "偏移高度" + verticalOffset);
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            scorllview.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    LogUtil.loge(oldScrollY + "里面的高度" + scrollY);
                    if (scrollY <= 0) {//当下面的fragment拉到顶部的时候 才允许上层的 拉动 0为顶部
                        parentFragment.banAppBarScroll(true);
                    } else {
                        if (isScore) {
                            parentFragment.banAppBarScroll(true);
                        } else {
                            parentFragment.banAppBarScroll(false);
                        }
                    }
                }
            });
        }
    }

    /**
     * 刷新子view的数据
     *
     * @param model
     */
    public void notifyModel(CardModel model) {
//        mPresenter.getList(true, true, null);
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
    }

    @Override
    public void onDataList(boolean isRefresh, List<DynamicModel> dynamicModelList) {
        if (isRefresh) {
            mDynamicAdapter.update(dynamicModelList);
            mRefreshLayout.finishRefresh();
        } else {
            mDynamicAdapter.addAll(dynamicModelList);

            if (dynamicModelList.size() < 10) {
                mRefreshLayout.finishLoadMoreWithNoMoreData();
            } else {
                mRefreshLayout.finishLoadMore();
            }
        }

        if (mDynamicAdapter.getItemCount() == 0) {
            if (parentFragment.getFromType() == 1) {
                emptyLin.setVisibility(View.VISIBLE);
                lin_other_dynamic_empty.setVisibility(View.GONE);
//                emptyText.setText("你还未发布动态哦,返回首页点击➕发布属于你的动态吧");
            } else {
                lin_other_dynamic_empty.setVisibility(View.VISIBLE);
                emptyLin.setVisibility(View.GONE);
            }
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
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (parentFragment.getFromType() == 1) {
            mPresenter.getList(false, true, null);
        } else {
            mPresenter.getList(false, false, parentFragment.getCardUserId());
        }
    }

    @Override
    public void onFinshRefresh(boolean isfresh) {

    }

    @Override
    public void onDynamicList(boolean isfresh, List<DynamicModel> list) {

    }

    @Override
    public void onZanResult(int position) {
        DynamicModel model = mDynamicAdapter.getPositionModel(position);
        model.setGiveLike(model.getGiveLike() == 0 ? 1 : 0);
        model.setUpCount(model.getGiveLike() > 0 ? model.getUpCount() + 1 : model.getUpCount() - 1);
        mDynamicAdapter.change(position, model);

    }

    @Override
    public void onFollowItemResult(int position) {

    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        mView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (parentFragment.getFromType() == 1) {
                    mPresenter.getList(true, true, null);
                } else {
                    mPresenter.getList(true, false, parentFragment.getCardUserId());
                }
            }
        }, 500);
    }

    @Override
    public boolean isDelayedData() {
        return false;
    }
}
