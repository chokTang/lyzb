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
import com.lyzb.jbx.mvp.presenter.me.FocusPresenter;
import com.lyzb.jbx.mvp.view.me.IFocusView;
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
 * @role 我的关注  15723384399  aaabbb
 * @time 2019 2019/3/7 16:56
 */

public class FocusFragment extends BaseVideoToolbarFrgament<FocusPresenter> implements IFocusView, OnRefreshLoadMoreListener {

    private TextView emptyText;
    private ClearEditText cet_search;

    private SmartRefreshLayout focusLayout;
    private View emptyLin;
    private RecyclerView focusRecy;
    private int mTotal = 0;

    private FansAdapter mDynamicAdapter = null;

    private static final String PARAMS_USERID = "params_userid";
    private String mUserId = "";
    private String mSearchKey = "";

    public static FocusFragment newIntance(String userId) {
        FocusFragment fragment = new FocusFragment();
        Bundle args = new Bundle();
        args.putString(PARAMS_USERID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mUserId = args.getString(PARAMS_USERID);
        }
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        super.onInitView(savedInstanceState);
        onBack();

        emptyText = findViewById(R.id.empty_tv);
        emptyLin = findViewById(R.id.inc_focus_empty);
        focusLayout = findViewById(R.id.sf_union_me_focus);
        focusRecy = findViewById(R.id.recy_union_me_focus);
        cet_search = findViewById(R.id.cet_search);

        focusLayout.setOnRefreshLoadMoreListener(this);
        focusRecy.addOnItemTouchListener(new OnRecycleItemClickListener() {
            @Override
            public void onItemChildClick(BaseRecyleAdapter adapter, View view, final int position) {
                super.onItemChildClick(adapter, view, position);
                final FansModel model = mDynamicAdapter.getPositionModel(position);
                switch (view.getId()) {
                    case R.id.tv_follow://取消关注
                        if (App.getInstance().isLogin()) {
                            AlertDialogFragment.newIntance()
                                    .setSureBtn(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            mPresenter.onDynamciFollowUser(model.getToUserId(), 0, position);
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
                        start(CardFragment.myIntance(2, 1, model.getToUserId()));
                        break;
                    //点击头像
                    case R.id.img_header:
                        start(CardFragment.newIntance(2, model.getToUserId()));
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
                mPresenter.getList(true, 2, mUserId, mSearchKey);
            }
        });
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        mDynamicAdapter = new FansAdapter(getContext(), null);
        mDynamicAdapter.setFragment(this);
        mDynamicAdapter.setMe(TextUtils.isEmpty(mUserId) || TextUtils.equals(App.getInstance().userId, mUserId));
        mDynamicAdapter.setLayoutManager(focusRecy);
        focusRecy.setAdapter(mDynamicAdapter);

        focusRecy.setRecyclerListener(new RecyclerView.RecyclerListener() {
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

        focusRecy.addItemDecoration(new DividerItemDecoration(DividerItemDecoration.VERTICAL_LIST, R.drawable.listdivider_window_10));
        mPresenter.getList(true, 2, mUserId, mSearchKey);
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_union_me_focus;
    }

    @Override
    public void onFocusList(boolean isRefresh, int total, List<FansModel> model) {
        mTotal = total;
        if (TextUtils.isEmpty(mSearchKey)) {
            setToolbarTitle(String.format("%s的关注(%d)", TextUtils.isEmpty(mUserId) ? "我" : "他", total));
        }
        if (isRefresh) {
            mDynamicAdapter.update(model);
            focusLayout.finishRefresh();
        } else {
            mDynamicAdapter.addAll(model);
            if (model.size() < 10) {
                focusLayout.finishLoadMoreWithNoMoreData();
            } else {
                focusLayout.finishLoadMore();
            }
        }
        if (mDynamicAdapter.getItemCount() == 0) {
            emptyLin.setVisibility(View.VISIBLE);
            emptyText.setText("你暂时还没有关注任何人哦~");
        } else {
            emptyLin.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getList(false, 2, mUserId, mSearchKey);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getList(true, 2, mUserId, mSearchKey);
    }

    @Override
    public void onFinshRefresh(boolean isfresh) {
        if (isfresh) {
            focusLayout.finishRefresh();
        } else {
            focusLayout.finishLoadMore();
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
        mTotal = mTotal - 1;
        setToolbarTitle(String.format("%s的关注(%d)", TextUtils.isEmpty(mUserId) ? "我" : "他", mTotal));
        mDynamicAdapter.remove(position);
        if (mDynamicAdapter.getItemCount() == 0) {
            emptyLin.setVisibility(View.VISIBLE);
        } else {
            emptyLin.setVisibility(View.GONE);
        }
    }
}
