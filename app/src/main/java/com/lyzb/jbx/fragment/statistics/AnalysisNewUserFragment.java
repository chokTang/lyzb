package com.lyzb.jbx.fragment.statistics;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener;
import com.like.longshaolib.base.BaseFragment;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.statistics.AnalysisUserAdapter;
import com.lyzb.jbx.fragment.me.card.CardFragment;
import com.lyzb.jbx.model.statistics.AnalysisNewUserModel;
import com.lyzb.jbx.mvp.presenter.statistics.AnalysisNewUserPresenter;
import com.lyzb.jbx.mvp.view.statistics.IAnalysisNewUserView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author wyx
 * @role 分析-引流列表
 * @time 2019 2019/4/16 17:24
 */

public class AnalysisNewUserFragment extends BaseFragment<AnalysisNewUserPresenter>
        implements IAnalysisNewUserView, OnRefreshLoadMoreListener {

    Unbinder unbinder;
    @BindView(R.id.analysis_user_recyclerview)
    RecyclerView mAnalysisUserRecyclerview;
    @BindView(R.id.analysis_user_nodata_in)
    View mAnalysisUserNodataIn;
    @BindView(R.id.analysis_user_title_tv)
    TextView mAnalysisUserTitleTv;
    @BindView(R.id.statistics_title_tv)
    TextView mStatisticsTitleTv;
    @BindView(R.id.statistics_right_tv)
    TextView mStatisticsRightTv;
    @BindView(R.id.analysis_user_smartrefreshlayout)
    SmartRefreshLayout mAnalysisUserSmartrefreshlayout;
    @BindView(R.id.empty_tv)
    TextView mEmptyTv;


    private AnalysisUserAdapter mAdapter;
    private int mDateType;
    private String mCompanyId;
    private String mUserId;
    private String mUserName;
    private String mTime;

    /**
     * 引流列表-企业
     *
     * @param departmentId
     * @param dateType
     * @return
     */
    public static AnalysisNewUserFragment newIntance(String departmentId, int dateType) {
        AnalysisNewUserFragment fragment = new AnalysisNewUserFragment();
        Bundle args = new Bundle();
        args.putString(StatisticsHomeFragment.INTENTKEY_DEPARTMENTID, departmentId);
        args.putInt(StatisticsHomeFragment.INTENTKEY_DATE, dateType);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 引流列表-个人
     *
     * @param userId
     * @param userName
     * @param dateType
     * @return
     */
    public static AnalysisNewUserFragment newIntance(String userId, String userName, int dateType) {
        AnalysisNewUserFragment fragment = new AnalysisNewUserFragment();
        Bundle args = new Bundle();
        args.putString(StatisticsHomeFragment.INTENTKEY_USERID, userId);
        args.putString(StatisticsHomeFragment.INTENTKEY_USERNAME, userName);
        args.putInt(StatisticsHomeFragment.INTENTKEY_DATE, dateType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {

        mAdapter = new AnalysisUserAdapter(getActivity(), null, TextUtils.isEmpty(mUserId));
        mAdapter.setLayoutManager(mAnalysisUserRecyclerview);
        mAnalysisUserRecyclerview.setAdapter(mAdapter);
        mAnalysisUserRecyclerview.addOnItemTouchListener(new OnRecycleItemClickListener() {
            @Override
            public void onItemChildClick(BaseRecyleAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.analysis_user_head_iv:
                        start(CardFragment.newIntance(2, mAdapter.getList().get(position).getBrowserUserId()));
                        break;
                    default:
                }
            }

            @Override
            public void onItemClick(BaseRecyleAdapter adapter, View view, int position) {
                start(CardFragment.newIntance(2, mAdapter.getList().get(position).getBrowserUserId()));
            }
        });

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
        if (TextUtils.isEmpty(mUserId)) {
            //企业
            mStatisticsTitleTv.setText("引流用户列表");
            mStatisticsRightTv.setText("引流排行");
            mStatisticsRightTv.setVisibility(View.VISIBLE);
            mAnalysisUserSmartrefreshlayout.setOnRefreshListener(this);
            mPresenter.getAnalysisNewUser_Company(mCompanyId, mDateType);
        } else {
            //用户
            mStatisticsTitleTv.setText(mUserName + "的引流用户列表");
            mStatisticsRightTv.setVisibility(View.GONE);
            mAnalysisUserSmartrefreshlayout.setOnRefreshLoadMoreListener(this);
            mPresenter.getAnalysisNewUser_User(true, mDateType, mUserId);
        }
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public Object getResId() {
        return R.layout.fragment_analysis_user;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mCompanyId = bundle.getString(StatisticsHomeFragment.INTENTKEY_DEPARTMENTID);
            mUserId = bundle.getString(StatisticsHomeFragment.INTENTKEY_USERID);
            mUserName = bundle.getString(StatisticsHomeFragment.INTENTKEY_USERNAME);
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
            case R.id.statistics_right_tv:
                start(AnalysisRankingFragment.newIntance(mCompanyId, mDateType, StatisticsHomeFragment.TYPE_NEWUSER, "all"));
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
    public void onLoadMore(AnalysisNewUserModel b) {
        if (b.getDataList().size() < 10) {
            mAnalysisUserSmartrefreshlayout.finishLoadMoreWithNoMoreData();
        } else {
            mAnalysisUserSmartrefreshlayout.finishLoadMore();
        }
        if (b == null || b.getDataList() == null || b.getDataList().size() < 1) {
            return;
        }
        mAdapter.addAll(b.getDataList());
    }

    @Override
    public void onRefresh(AnalysisNewUserModel b) {
        mAnalysisUserSmartrefreshlayout.finishRefresh();
        mAnalysisUserNodataIn.setVisibility(View.GONE);
        mAnalysisUserRecyclerview.setVisibility(View.VISIBLE);
        //企业、个人引流都是一处
        String number = getActivity().getString(R.string.analysis_newuser_company);
        if (!TextUtils.isEmpty(mUserId)) {
            number = getActivity().getString(R.string.analysis_newuser_user);
        }
        mAnalysisUserTitleTv.setText(String.format(number, mTime, b.getTotalNewUser()));
        mAdapter.update(b.getDataList());
    }


    @Override
    public void onNotData() {
        showNotData();
    }

    @Override
    public void onFail(String msg) {
        showNotData();
        showToast(msg);
    }

    /**
     * 显示没有数据
     */
    private void showNotData() {
        mAnalysisUserSmartrefreshlayout.finishRefresh();
        if (TextUtils.isEmpty(mUserId)) {
            mAnalysisUserTitleTv.setText(getActivity().getString(R.string.analysis_newuser_company, mTime, "0"));
        } else {
            mAnalysisUserTitleTv.setText(getActivity().getString(R.string.analysis_newuser_user, mTime, "0"));
        }
        mEmptyTv.setText("这里居然空空如也~");
        mAnalysisUserNodataIn.setVisibility(View.VISIBLE);
        mAnalysisUserRecyclerview.setVisibility(View.GONE);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        if (TextUtils.isEmpty(mUserId)) {
            mPresenter.getAnalysisNewUser_Company(mCompanyId, mDateType);
        } else {
            mPresenter.getAnalysisNewUser_User(true, mDateType, mUserId);
        }

    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getAnalysisNewUser_User(false, mDateType, mUserId);
    }
}
