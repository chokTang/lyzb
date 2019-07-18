package com.lyzb.jbx.fragment.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.DividerItemDecoration;
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener;
import com.like.longshaolib.dialog.fragment.AlertDialogFragment;
import com.like.longshaolib.widget.ClearEditText;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.home.search.SearchAccountDynamicItemAdapter;
import com.lyzb.jbx.adapter.me.card.FansAdapter;
import com.lyzb.jbx.fragment.base.BaseVideoToolbarFrgament;
import com.lyzb.jbx.fragment.me.card.CardFragment;
import com.lyzb.jbx.model.dynamic.DynamicModel;
import com.lyzb.jbx.model.me.FansModel;
import com.lyzb.jbx.mvp.presenter.me.FansPresenter;
import com.lyzb.jbx.mvp.view.me.IFansView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.szy.yishopcustomer.Activity.LoginActivity;
import com.szy.yishopcustomer.Util.App;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;

import java.util.List;

/**
 * @author wyx
 * @role 我的粉丝
 * @time 2019 2019/3/7 16:56
 */

public class FansFragment extends BaseVideoToolbarFrgament<FansPresenter> implements IFansView, OnRefreshLoadMoreListener {

    private TextView emptyText;
    private ClearEditText cet_search;

    private SmartRefreshLayout fansLayout;
    private RecyclerView fansRecy;
    private View emptyLin;

    private FansAdapter mDynamicAdapter = null;
    private int mTotal = 0;//粉丝数

    public static final String KEY_USER_ID = "keyuserId";
    private String userId = "";
    private String mSearchKey = "";

    public static FansFragment newIntance(String userId) {
        FansFragment fansFragment = new FansFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_USER_ID, userId);
        fansFragment.setArguments(bundle);
        return fansFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            userId = bundle.getString(KEY_USER_ID);
        }
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        super.onInitView(savedInstanceState);
        onBack();
        emptyText = findViewById(R.id.empty_tv);
        cet_search = findViewById(R.id.cet_search);

        fansLayout = findViewById(R.id.sf_union_me_fans);
        fansRecy = findViewById(R.id.recy_union_me_fans);
        emptyLin = findViewById(R.id.inc_fans_empty);

        fansLayout.setOnRefreshLoadMoreListener(this);
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        mDynamicAdapter = new FansAdapter(getContext(), null);
        mDynamicAdapter.setFragment(this);
        mDynamicAdapter.setMe(TextUtils.isEmpty(userId) ||TextUtils.equals(App.getInstance().userId,userId));
        mDynamicAdapter.setLayoutManager(fansRecy);
        fansRecy.setAdapter(mDynamicAdapter);

        fansRecy.setRecyclerListener(new RecyclerView.RecyclerListener() {
            @Override
            public void onViewRecycled(RecyclerView.ViewHolder holder) {
                if (holder instanceof SearchAccountDynamicItemAdapter.VideoHolder) {
                    NiceVideoPlayer niceVideoPlayer = ((SearchAccountDynamicItemAdapter.VideoHolder) holder).videoPlayer;
                    if (niceVideoPlayer == NiceVideoPlayerManager.instance().getCurrentNiceVideoPlayer()) {
                        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
                    }
                }
            }
        });

        fansRecy.addItemDecoration(new DividerItemDecoration(DividerItemDecoration.VERTICAL_LIST, R.drawable.listdivider_window_10));

        mPresenter.getList(true, 1, userId, mSearchKey);

        fansRecy.addOnItemTouchListener(new OnRecycleItemClickListener() {
            @Override
            public void onItemChildClick(BaseRecyleAdapter adapter, View view, final int position) {
                final FansModel model = mDynamicAdapter.getPositionModel(position);
                switch (view.getId()) {
                    //没关注  去关注
                    case R.id.tv_no_follow:
                        if (App.getInstance().isLogin()) {
                            mPresenter.onDynamciFollowUser(model.getFromUserId(), 1, position);
                        } else {
                            startActivity(new Intent(getActivity(), LoginActivity.class));
                        }
                        break;
                    //已经关注   点击取消关注
                    case R.id.tv_follow:
                        if (App.getInstance().isLogin()) {
                            AlertDialogFragment.newIntance()
                                    .setSureBtn(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            mPresenter.onDynamciFollowUser(model.getFromUserId(), 0, position);
                                        }
                                    })
                                    .setCancleBtn(null)
                                    .setContent("确定不再关注该用户？")
                                    .show(getFragmentManager(), "follow_tag");
                        } else {
                            startActivity(new Intent(getActivity(), LoginActivity.class));
                        }
                        break;
                    case R.id.tv_dynamic_number:
                        start(CardFragment.myIntance(2, 1, model.getFromUserId()));
                        break;
                    //点击头像
                    case R.id.img_header:
                        start(CardFragment.newIntance(2, model.getFromUserId()));
                        break;
                    default:
                        break;
                }
            }
        });

        cet_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mSearchKey = s.toString();
                mPresenter.getList(true, 1, userId, mSearchKey);
            }
        });
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_union_me_fans;
    }

    @Override
    public void onFansList(boolean isRefresh, int total, List<FansModel> model) {
        mTotal = total;
        if (TextUtils.isEmpty(mSearchKey)){
            setToolbarTitle(String.format("%s的粉丝(%d)", TextUtils.isEmpty(userId) ? "我" : "他", mTotal));
        }
        if (isRefresh) {
            fansLayout.finishRefresh();
            if (model.size() < 10) {
                fansLayout.finishLoadMoreWithNoMoreData();
            }
            mDynamicAdapter.update(model);
        } else {

            mDynamicAdapter.addAll(model);
            if (model.size() < 10) {
                fansLayout.finishLoadMoreWithNoMoreData();
            } else {
                fansLayout.finishLoadMore();
            }
        }

        if (mDynamicAdapter.getItemCount() == 0) {
            emptyLin.setVisibility(View.VISIBLE);
            emptyText.setText("暂时还没有人关注你哦~");
        } else {
            emptyLin.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getList(false, 1, userId, mSearchKey);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getList(true, 1, userId, mSearchKey);
    }

    @Override
    public void onFinshRefresh(boolean isfresh) {
        if (isfresh) {
            fansLayout.finishRefresh();
        } else {
            fansLayout.finishLoadMore();
        }
    }

    @Override
    public void onDynamicList(boolean isfresh, List<DynamicModel> list) {
        //不要次方法
    }

    @Override
    public void onZanResult(int position) {
    }

    @Override
    public void onFollowItemResult(int position) {
        if (mDynamicAdapter.getPositionModel(position).getRelationNum() > 0) {
            mDynamicAdapter.getPositionModel(position).setRelationNum(0);
        } else {
            mDynamicAdapter.getPositionModel(position).setRelationNum(1);
        }
        mDynamicAdapter.change(position, mDynamicAdapter.getPositionModel(position));
        if (mDynamicAdapter.getItemCount() == 0) {
            emptyLin.setVisibility(View.VISIBLE);
        } else {
            emptyLin.setVisibility(View.GONE);
        }
    }
}
