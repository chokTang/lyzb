package com.lyzb.jbx.fragment.me.basic;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener;
import com.like.longshaolib.base.BaseToolbarFragment;
import com.like.longshaolib.widget.ClearEditText;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.me.basic.CardCilpAdapter;
import com.lyzb.jbx.fragment.me.card.CardFragment;
import com.lyzb.jbx.model.me.basic.CardCilpModel;
import com.lyzb.jbx.mvp.presenter.me.basic.CardCilpPresenter;
import com.lyzb.jbx.mvp.view.me.basic.ICardCilpView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

/**
 * 名片夹
 */
public class CardCilpFrgament extends BaseToolbarFragment<CardCilpPresenter> implements ICardCilpView, OnRefreshLoadMoreListener {

    private ClearEditText cet_search;
    private SmartRefreshLayout sf_union_me_focus;
    private RecyclerView recy_union_me_focus;
    private View inc_focus_empty;

    private CardCilpAdapter mAdapter;

    private String mSearchKey = "";

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        super.onInitView(savedInstanceState);
        onBack();

        cet_search = findViewById(R.id.cet_search);
        sf_union_me_focus = findViewById(R.id.sf_union_me_focus);
        recy_union_me_focus = findViewById(R.id.recy_union_me_focus);
        inc_focus_empty = findViewById(R.id.inc_focus_empty);

        sf_union_me_focus.setOnRefreshLoadMoreListener(this);
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
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
                mPresenter.getList(true, mSearchKey);
            }
        });

        mAdapter = new CardCilpAdapter(getContext(), null);
        mAdapter.setLayoutManager(recy_union_me_focus);
        recy_union_me_focus.setAdapter(mAdapter);
        recy_union_me_focus.addOnItemTouchListener(new OnRecycleItemClickListener() {
            @Override
            public void onItemClick(BaseRecyleAdapter adapter, View view, int position) {
                start(CardFragment.newIntance(2, mAdapter.getPositionModel(position).getUserId()));
            }
        });

        mPresenter.getList(true, mSearchKey);
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_card_cilp;
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getList(false, mSearchKey);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getList(true, mSearchKey);
    }

    @Override
    public void onListResult(boolean isRefresh, int total, List<CardCilpModel> list) {
        if (TextUtils.isEmpty(mSearchKey)) {
            setToolbarTitle(String.format("名片夹(%d)", total));
        }
        if (isRefresh) {
            if (list.size() < 10) {
                sf_union_me_focus.finishLoadMoreWithNoMoreData();
            }
            sf_union_me_focus.finishRefresh();
            mAdapter.update(list);
        } else {
            if (list.size() < 10) {
                sf_union_me_focus.finishLoadMoreWithNoMoreData();
            } else {
                sf_union_me_focus.finishLoadMore();
            }
            mAdapter.addAll(list);
        }
        if (mAdapter.getItemCount() == 0) {
            inc_focus_empty.setVisibility(View.VISIBLE);
        } else {
            inc_focus_empty.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFinshOrLoadMore(boolean isRefresh) {
        if (isRefresh) {
            sf_union_me_focus.finishRefresh();
        } else {
            sf_union_me_focus.finishLoadMore();
        }
    }
}
