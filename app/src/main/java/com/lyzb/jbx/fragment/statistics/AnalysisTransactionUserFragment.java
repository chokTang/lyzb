package com.lyzb.jbx.fragment.statistics;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.like.longshaolib.base.BaseFragment;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.statistics.AnalysisTransactionAdapter;
import com.lyzb.jbx.model.statistics.AnalysisTransactionModel;
import com.lyzb.jbx.mvp.presenter.statistics.AnalysisTransactionPresenter;
import com.lyzb.jbx.mvp.view.statistics.IAnalysisTransactionView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author wyx
 * @role 分析-用户交易记录
 * @time 2019 2019/4/16 17:24
 */

public class AnalysisTransactionUserFragment extends BaseFragment<AnalysisTransactionPresenter>
        implements IAnalysisTransactionView, OnRefreshListener {
    Unbinder unbinder;
    @BindView(R.id.analysis_transaction_title_tv)
    TextView mAnalysisTransactionTitleTv;
    @BindView(R.id.analysis_transaction_nodata_in)
    View mAnalysisTransactionNodataIn;
    @BindView(R.id.analysis_transaction_recyclerview)
    RecyclerView mAnalysisTransactionRecyclerview;
    @BindView(R.id.statistics_title_tv)
    TextView mStatisticsTitleTv;
    @BindView(R.id.analysis_transaction_smartrefreshlayout)
    SmartRefreshLayout mAnalysisTransactionSmartrefreshlayout;

    private List<MultiItemEntity> mData;
    private AnalysisTransactionAdapter mAdapter;
    private String mTime;
    private String mUserName;
    private String mUserId;
    private int mDateType;

    /**
     * 名片商城交易记录-个人
     *
     * @param userId
     * @param userName
     * @param dateType
     * @return
     */
    public static AnalysisTransactionUserFragment newIntance(String userId, String userName, int dateType) {
        AnalysisTransactionUserFragment fragment = new AnalysisTransactionUserFragment();
        Bundle args = new Bundle();
        args.putString(StatisticsHomeFragment.INTENTKEY_USERID, userId);
        args.putString(StatisticsHomeFragment.INTENTKEY_USERNAME, userName);
        args.putInt(StatisticsHomeFragment.INTENTKEY_DATE, dateType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        mAnalysisTransactionSmartrefreshlayout.setOnRefreshListener(this);
        mStatisticsTitleTv.setText(mUserName + "的名片商城交易记录");
        switch (mDateType) {
            case StatisticsHomeFragment.DAY_ALL:
                mTime = "全部";
                break;
            case StatisticsHomeFragment.DAY_30:
                mTime = "近30天";
                break;
            case StatisticsHomeFragment.DAY_7:
                mTime = "近7天";
                break;
            case StatisticsHomeFragment.DAY_CURR:
                mTime = "今日";
                break;
            default:
        }
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        mData = new ArrayList<>();
        mAdapter = new AnalysisTransactionAdapter(mData);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mAnalysisTransactionRecyclerview.setLayoutManager(manager);
        mAnalysisTransactionRecyclerview.setAdapter(mAdapter);
        mAdapter.expandAll();

        mPresenter.getAnalysisTeansactionUser(mUserId, mDateType);
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_analysis_transaction;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mUserName = bundle.getString(StatisticsHomeFragment.INTENTKEY_USERNAME);
            mUserId = bundle.getString(StatisticsHomeFragment.INTENTKEY_USERID);
            mDateType = bundle.getInt(StatisticsHomeFragment.INTENTKEY_DATE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick({R.id.statistics_back_iv, R.id.statistics_right_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.statistics_back_iv:
                pop();
                break;
            default:
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onData(AnalysisTransactionModel b) {
        mAnalysisTransactionSmartrefreshlayout.finishRefresh();
        mAnalysisTransactionNodataIn.setVisibility(View.GONE);
        mAnalysisTransactionRecyclerview.setVisibility(View.VISIBLE);

        mAnalysisTransactionTitleTv.setText(getActivity().getString(R.string.analysis_transaction, mTime, b.getOrderCount(), b.getOrderAmount()));
        mData.clear();
        mData.addAll(b.getOrderByDay());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFile(String msg) {
        mAnalysisTransactionSmartrefreshlayout.finishRefresh();
        mAnalysisTransactionTitleTv.setText(getActivity().getString(R.string.analysis_transaction_user, mTime, 0, 0));
        mAnalysisTransactionNodataIn.setVisibility(View.VISIBLE);
        mAnalysisTransactionRecyclerview.setVisibility(View.GONE);
        showToast(msg);
    }

    @Override
    public void onNotData() {
        mAnalysisTransactionSmartrefreshlayout.finishRefresh();
        mAnalysisTransactionTitleTv.setText(getActivity().getString(R.string.analysis_transaction_user, mTime, 0, 0));
        mAnalysisTransactionNodataIn.setVisibility(View.VISIBLE);
        mAnalysisTransactionRecyclerview.setVisibility(View.GONE);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getAnalysisTeansactionUser(mUserId, mDateType);
    }

}
