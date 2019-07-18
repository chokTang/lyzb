package com.lyzb.jbx.fragment.circle;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.DividerItemDecoration;
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener;
import com.like.longshaolib.base.BaseToolbarFragment;
import com.lyzb.jbx.R;
import com.lyzb.jbx.activity.GoodToMeActivity;
import com.lyzb.jbx.adapter.me.ApplyListAdapter;
import com.lyzb.jbx.fragment.me.card.CardFragment;
import com.lyzb.jbx.model.me.AgreedCircleModel;
import com.lyzb.jbx.model.me.ApplyListModel;
import com.lyzb.jbx.mvp.presenter.me.ApplyListPresenter;
import com.lyzb.jbx.mvp.view.me.IApplyListView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wyx
 * @role 申请列表
 * @time 2019 2019/3/13 17:53
 */

public class ApplyListFragment extends BaseToolbarFragment<ApplyListPresenter>
        implements IApplyListView, OnRefreshLoadMoreListener {

    private final static String GROUP_ID = "GROUP_ID";
    private String mGroupId = null;

    @BindView(R.id.sf_circle_applist)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.recy_circle_applist)
    RecyclerView mRecyclerView;

    private ApplyListAdapter mApplyListAdapter = null;

    public static ApplyListFragment newTance(String groupId) {
        ApplyListFragment fragment = new ApplyListFragment();
        Bundle args = new Bundle();
        args.putString(GROUP_ID, groupId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mGroupId = bundle.getString(GROUP_ID);
        }
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        super.onInitView(savedInstanceState);
        mToolbar.setNavigationIcon(com.like.longshaolib.R.drawable.toolbar_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getBaseActivity() instanceof GoodToMeActivity) {
                    getBaseActivity().finish();
                } else {
                    pop();
                }
            }
        });
        setToolbarTitle("申请列表");
        ButterKnife.bind(this, mView);

        mRecyclerView.addOnItemTouchListener(new OnRecycleItemClickListener() {
            @Override
            public void onItemChildClick(BaseRecyleAdapter adapter, View view, int position) {
                ApplyListModel.ListBean bean = mApplyListAdapter.getPositionModel(position);
                switch (view.getId()) {
                    //他的名片
                    case R.id.img_item_cir_appl_head:
                        start(CardFragment.newIntance(2, bean.getUserId()));
                        break;
                    //同意
                    case R.id.tv_item_cir_appl_agreed:
                        if (bean.getPass() != 3) {//如果已处理 则返回
                            return;
                        }
                        mPresenter.onAgreed(1, new AgreedCircleModel(bean.getId()), position);
                        break;
                    //拒绝
                    case R.id.tv_item_cir_appl_refuse:
                        if (bean.getPass() != 3) {//如果已处理 则返回
                            return;
                        }
                        mPresenter.onAgreed(0, new AgreedCircleModel(bean.getId()), position);
                        break;
                    default:
                }
            }

            @Override
            public void onItemClick(BaseRecyleAdapter adapter, View view, int position) {
                //他的名片
                ApplyListModel.ListBean bean = mApplyListAdapter.getPositionModel(position);
                start(CardFragment.newIntance(2, bean.getUserId()));
            }
        });

        mRefreshLayout.setOnRefreshLoadMoreListener(this);
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        mApplyListAdapter = new ApplyListAdapter(getContext(), null);
        mApplyListAdapter.setLayoutManager(mRecyclerView);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(DividerItemDecoration.VERTICAL_LIST, R.drawable.listdivider_window_10));
        mRecyclerView.setAdapter(mApplyListAdapter);

        mPresenter.getList(true, mGroupId);
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_apply_list;
    }

    @Override
    public void onApplyList(boolean isRefresh, List<ApplyListModel.ListBean> list) {
        if (isRefresh) {
            mRefreshLayout.finishRefresh();
            if (list.size() < 10) {
                mRefreshLayout.finishLoadMoreWithNoMoreData();
            }
            mApplyListAdapter.update(list);
        } else {
            if (list.size() < 10) {
                mRefreshLayout.finishLoadMoreWithNoMoreData();
            } else {
                mRefreshLayout.finishLoadMore();
            }
            mApplyListAdapter.addAll(list);
        }
    }

    @Override
    public void onAgreed(int position) {
        ApplyListModel.ListBean model = mApplyListAdapter.getPositionModel(position);
        model.setPass(1);
        mApplyListAdapter.change(position, model);
    }

    @Override
    public void onRefuse(int position) {
        ApplyListModel.ListBean model = mApplyListAdapter.getPositionModel(position);
        model.setPass(0);
        mApplyListAdapter.change(position, model);
    }

    @Override
    public void onFinshOrLoadMore(boolean isRefrsh) {
        if (isRefrsh) {
            mRefreshLayout.finishRefresh();
        } else {
            mRefreshLayout.finishLoadMore();
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getList(false, mGroupId);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getList(true, mGroupId);
    }
}
