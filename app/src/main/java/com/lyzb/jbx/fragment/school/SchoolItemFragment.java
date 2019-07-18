package com.lyzb.jbx.fragment.school;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.DividerItemDecoration;
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener;
import com.like.longshaolib.base.BaseFragment;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.school.ArticleAdapter;
import com.lyzb.jbx.model.school.SchoolModel;
import com.lyzb.jbx.mvp.presenter.school.SchoolItemPresenter;
import com.lyzb.jbx.mvp.view.school.ISchoolItemView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.szy.yishopcustomer.Activity.LoginActivity;
import com.szy.yishopcustomer.Activity.ProjectH5Activity;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Util.App;

import java.util.List;

public class SchoolItemFragment extends BaseFragment<SchoolItemPresenter> implements ISchoolItemView, OnRefreshLoadMoreListener {

    //参数
    private static final String PARAMS_TYPE_ID = "params_type_id";
    private static final String PARAMS_TYPE = "params_type";
    private String mTypeId = "";
    private int mCatType = 1;//分类：1.文章 2.帮助中心

    private SmartRefreshLayout refresh_school;
    private RecyclerView recycle_school;
    private View empty_view;

    private ArticleAdapter mAdapter;

    public static SchoolItemFragment newIntance(String typeId, int catType) {
        SchoolItemFragment fragment = new SchoolItemFragment();
        Bundle args = new Bundle();
        args.putString(PARAMS_TYPE_ID, typeId);
        args.putInt(PARAMS_TYPE, catType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mTypeId = args.getString(PARAMS_TYPE_ID);
            mCatType = args.getInt(PARAMS_TYPE, 1);
        }
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        refresh_school = findViewById(R.id.refresh_school);
        recycle_school = findViewById(R.id.recycle_school);
        empty_view = findViewById(R.id.empty_view);

        refresh_school.setOnRefreshLoadMoreListener(this);
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        mAdapter = new ArticleAdapter(getContext(), null);
        mAdapter.setLayoutManager(recycle_school);
        recycle_school.addItemDecoration(new DividerItemDecoration(DividerItemDecoration.VERTICAL_LIST));
        recycle_school.setAdapter(mAdapter);

        mPresenter.getList(true, mTypeId,mCatType);


        recycle_school.addOnItemTouchListener(new OnRecycleItemClickListener() {
            @Override
            public void onItemClick(BaseRecyleAdapter adapter, View view, int position) {
                //跳转到详情
//                childStart(ArticleDetailFragment.newIntance(mAdapter.getPositionModel(position).getArticleId()));
                Intent intent = new Intent(getContext(), ProjectH5Activity.class);
                intent.putExtra(Key.KEY_URL.getValue(), String.format("%s/union/college/article?articleId=%s", Api.H5_CITYLIFE_URL
                        , mAdapter.getPositionModel(position).getArticleId()));
                startActivity(intent);
            }

            @Override
            public void onItemChildClick(BaseRecyleAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    //点赞操作
                    case R.id.tv_zan_number:
                        if (App.getInstance().isLogin()) {
                            view.startAnimation(AnimationUtils.loadAnimation(
                                    getContext(), R.anim.zan_anim));
                            mPresenter.zan(mAdapter.getPositionModel(position).getArticleId(),
                                    mAdapter.getPositionModel(position).isZan() ? 2 : 1, position);
                        } else {
                            startActivity(new Intent(getContext(), LoginActivity.class));
                        }
                        break;
                }
            }
        });
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_school_item;
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getList(false, mTypeId,mCatType);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getList(true, mTypeId,mCatType);
    }

    @Override
    public void onGetListByType(boolean isfresh, List<SchoolModel> list) {
        if (isfresh) {
            refresh_school.finishRefresh();
            mAdapter.update(list);
            if (list.size() < 10) {
                refresh_school.finishLoadMoreWithNoMoreData();
            }
        } else {
            mAdapter.addAll(list);
            if (list.size() < 10) {
                refresh_school.finishLoadMoreWithNoMoreData();
            } else {
                refresh_school.finishLoadMore();
            }
        }
        if (mAdapter.getItemCount() == 0) {
            empty_view.setVisibility(View.VISIBLE);
        } else {
            empty_view.setVisibility(View.GONE);
        }
    }

    @Override
    public void onZanReuslt(int position) {
        SchoolModel model = mAdapter.getPositionModel(position);
        model.setZan(!model.isZan());
        model.setArticleThumb(model.isZan() ? model.getArticleThumb() + 1 : model.getArticleThumb() - 1);
        mAdapter.change(position, model);
    }

    @Override
    public boolean isDelayedData() {
        return false;
    }
}
