package com.lyzb.jbx.fragment.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener;
import com.like.longshaolib.base.BaseToolbarFragment;
import com.like.longshaolib.dialog.fragment.AlertDialogFragment;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.me.MyFootAdapter;
import com.lyzb.jbx.fragment.dynamic.DynamicDetailFragment;
import com.lyzb.jbx.fragment.me.card.CardFragment;
import com.lyzb.jbx.model.me.MeFootModel;
import com.lyzb.jbx.mvp.presenter.me.MyFootPresenter;
import com.lyzb.jbx.mvp.view.me.IMyFootView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.szy.yishopcustomer.Activity.GoodsActivity;
import com.szy.yishopcustomer.Constant.Key;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的足迹
 *
 * @author shidengzhong
 */
public class MyFootFragment extends BaseToolbarFragment<MyFootPresenter> implements IMyFootView, Toolbar.OnMenuItemClickListener,
        OnRefreshLoadMoreListener {

    private SmartRefreshLayout foot_refresh;
    private RecyclerView foot_recycle;
    private View empty_view;

    private MyFootAdapter mAdapter;

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        super.onInitView(savedInstanceState);
        onBack();
        setToolbarTitle("我的足迹");
        mToolbar.inflateMenu(R.menu.delete_menu);
        mToolbar.setOnMenuItemClickListener(this);

        foot_refresh = findViewById(R.id.foot_refresh);
        foot_recycle = findViewById(R.id.foot_recycle);
        empty_view = findViewById(R.id.empty_view);

        foot_refresh.setOnRefreshLoadMoreListener(this);
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        mAdapter = new MyFootAdapter(getContext(), null);
        mAdapter.setLayoutManager(foot_recycle);
        foot_recycle.setAdapter(mAdapter);

        foot_recycle.addOnItemTouchListener(new OnRecycleItemClickListener() {
            @Override
            public void onItemClick(BaseRecyleAdapter adapter, View view, int position) {
                MeFootModel model = mAdapter.getPositionModel(position);
                switch (model.getType()) {
                    //名片
                    case 1:
                        start(CardFragment.newIntanceByCardID(model.getId()));
                        break;
                    //动态
                    case 2:
                        start(DynamicDetailFragment.Companion.newIntance(model.getId()));
                        break;
                    //商品
                    case 3:
                        Intent intent = new Intent(getActivity(), GoodsActivity.class);
                        intent.putExtra(Key.KEY_GOODS_ID.getValue(), model.getId());
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        });

        mPresenter.getFootList(true);
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_my_foot;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            //清空列表
            case R.id.btn_delete:
                AlertDialogFragment.newIntance()
                        .setContent("确定清空我的足迹吗？")
                        .setCancleBtn(null)
                        .setSureBtn(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mPresenter.clearFoot();
                            }
                        })
                        .show(getFragmentManager(), "clearMyFoot");
                break;
        }
        return true;
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getFootList(false);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getFootList(true);
    }

    @Override
    public void onFootListResult(boolean isRefresh, List<MeFootModel> list) {
        if (isRefresh) {
            foot_refresh.finishRefresh();
            if (list.size() < 10) {
                foot_refresh.finishLoadMoreWithNoMoreData();
            }
            mAdapter.update(list);
        } else {
            if (list.size() < 10) {
                foot_refresh.finishLoadMoreWithNoMoreData();
            } else {
                foot_refresh.finishLoadMore();
            }
            mAdapter.addAll(list);
        }
        if (mAdapter.getItemCount() == 0) {
            empty_view.setVisibility(View.VISIBLE);
            mToolbar.getMenu().clear();
        } else {
            empty_view.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClearFootSuccess() {
        mAdapter.update(new ArrayList<MeFootModel>());
        if (mAdapter.getItemCount() == 0) {
            empty_view.setVisibility(View.VISIBLE);
            mToolbar.getMenu().clear();
        } else {
            empty_view.setVisibility(View.GONE);
        }
    }
}
