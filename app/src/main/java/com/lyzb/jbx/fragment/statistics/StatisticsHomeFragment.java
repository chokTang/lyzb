package com.lyzb.jbx.fragment.statistics;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.like.longshaolib.base.BaseFragment;
import com.like.longshaolib.widget.AutoWidthTabLayout;
import com.like.utilslib.screen.ScreenUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.statistics.StatisticsHomeAdapter;
import com.lyzb.jbx.fragment.me.card.CardFragment;
import com.lyzb.jbx.model.cenum.DayEnum;
import com.lyzb.jbx.model.statistics.StatisticsHomeModel;
import com.lyzb.jbx.mvp.presenter.statistics.StatisticsHomePresenter;
import com.lyzb.jbx.mvp.view.statistics.IStatisticsHomeView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author wyx
 * @role 数据统计主页
 * @time 2019 2019/4/16 15:08
 */

public class StatisticsHomeFragment extends BaseFragment<StatisticsHomePresenter>
        implements IStatisticsHomeView, View.OnClickListener, OnRefreshListener,
        BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.OnItemClickListener,
        TabLayout.OnTabSelectedListener {

    public final static int DAY_ALL = 0;
    public final static int DAY_30 = 30;
    public final static int DAY_7 = 7;
    public final static int DAY_CURR = 1;

    public final static int TYPE_VISIT = 1;
    public final static int TYPE_SHAER = 2;
    public final static int TYPE_NEWUSER = 3;
    public final static int TYPE_TRANSACTION = 4;

    public final static String INTENTKEY_DATE = "intentkey_date";
    public final static String INTENTKEY_SCREEN = "intentkey_screen";
    public final static String INTENTKEY_VISITSORT = "INTENTKEY_VISITSORT";

    public final static String INTENTKEY_USERID = "Intentkey_userid";
    public final static String INTENTKEY_USERNAME = "intentkey_username";
    /**
     * 企业id
     */
    public final static String INTENTKEY_DEPARTMENTID = "intentkey_departmentid";


    @BindView(R.id.statistics_date_tab)
    AutoWidthTabLayout mStatisticsDateTab;
    @BindView(R.id.statistics_recyclerview)
    RecyclerView mStatisticsRecyclerview;
    @BindView(R.id.statistics_smartrefreshlayout)
    SmartRefreshLayout mSstatisticsSmartrefreshlayout;
    @BindView(R.id.statistics_title_tv)
    TextView mStatisticsTitleTv;

    private TextView mStatisticsShareNumberTv;
    private TextView mStatisticsVisitNumberTv;
    private TextView mStatisticsTransactionNumberTv;
    private TextView mStatisticsNewuserNumberTv;
    private ImageView mStatisticsShareNumberIv;

    Unbinder unbinder;

    private StatisticsHomeAdapter mAdapter;
    private int mDateType = DAY_ALL, mScreenType = TYPE_VISIT;
    private String mCompanyId;
    private View mHeadView;

    public static StatisticsHomeFragment newIntance(String departmentId) {
        StatisticsHomeFragment fragment = new StatisticsHomeFragment();
        Bundle args = new Bundle();
        args.putString(INTENTKEY_DEPARTMENTID, departmentId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        initHeader();
        int widht = ScreenUtil.getScreenWidth();
        double height = widht * 0.77;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(widht, (int) height);
        mStatisticsShareNumberIv.setLayoutParams(params);

        mStatisticsTitleTv.setText("统计数据");

        mStatisticsDateTab.addTab("全部");
        mStatisticsDateTab.addTab("近30天");
        mStatisticsDateTab.addTab("近7天");
        mStatisticsDateTab.addTab("今日");
        mStatisticsDateTab.addOnTabSelectedListener(this);

        mAdapter = new StatisticsHomeAdapter(null);
        mAdapter.addHeaderView(mHeadView);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemChildClickListener(this);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mStatisticsRecyclerview.setLayoutManager(manager);
        mStatisticsRecyclerview.setAdapter(mAdapter);

        mSstatisticsSmartrefreshlayout.setOnRefreshListener(this);
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        mPresenter.getData(mDateType, mScreenType, mCompanyId);
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_statistics_home;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCompanyId = getArguments().getString(INTENTKEY_DEPARTMENTID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 做整体滑动和下拉刷新，顶部就要用head的方式加进来了
     */
    private void initHeader() {
        mHeadView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_statistics_head, null);
        mStatisticsShareNumberTv = mHeadView.findViewById(R.id.statistics_share_number_tv);
        mStatisticsVisitNumberTv = mHeadView.findViewById(R.id.statistics_visit_number_tv);
        mStatisticsTransactionNumberTv = mHeadView.findViewById(R.id.statistics_transaction_number_tv);
        mStatisticsNewuserNumberTv = mHeadView.findViewById(R.id.statistics_newuser_number_tv);
        mStatisticsShareNumberIv = mHeadView.findViewById(R.id.statistics_share_number_iv);

        findViewById(R.id.statistics_back_iv).setOnClickListener(this);
        mHeadView.findViewById(R.id.statistics_visit_rl).setOnClickListener(this);
        mHeadView.findViewById(R.id.statistics_share_rl).setOnClickListener(this);
        mHeadView.findViewById(R.id.statistics_newuser_rl).setOnClickListener(this);
        mHeadView.findViewById(R.id.statistics_transaction_rl).setOnClickListener(this);
        mHeadView.findViewById(R.id.statistics_visit_rbtn).setOnClickListener(this);
        mHeadView.findViewById(R.id.statistics_share_rbtn).setOnClickListener(this);
        mHeadView.findViewById(R.id.statistics_newuser_rbtn).setOnClickListener(this);
        mHeadView.findViewById(R.id.statistics_money_rbtn).setOnClickListener(this);
    }

    @Override
    public void onData(StatisticsHomeModel b) {
        mSstatisticsSmartrefreshlayout.finishRefresh();
        mAdapter.setNewData(b.getDataList());
        //四个数量
        mStatisticsVisitNumberTv.setText(String.valueOf(b.getTotalVisit()));
        mStatisticsShareNumberTv.setText(String.valueOf(b.getTotalShare()));
        mStatisticsNewuserNumberTv.setText(String.valueOf(b.getTotalNewUser()));
        mStatisticsTransactionNumberTv.setText(String.valueOf(b.getTotalOrder()));
    }

    @Override
    public void onFail(String msg) {
        showToast(msg);
        pop();
    }

    @Override
    public void onNotData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //返回键
            case R.id.statistics_back_iv:
                pop();
                break;
            //访问次数
            case R.id.statistics_visit_rl:
                start(AnalysisVisitCompanyFragment.newIntance(mCompanyId, mDateType));
                break;
            //分享次数
            case R.id.statistics_share_rl:
                start(AnalysisShareFragment.newIntance(mCompanyId, mDateType));
                break;
            //新用户
            case R.id.statistics_newuser_rl:
                start(AnalysisNewUserFragment.newIntance(mCompanyId, mDateType));
                break;
            //交易
            case R.id.statistics_transaction_rl:
                start(AnalysisTransactionCompanyFragment.newIntance(mCompanyId, mDateType));
                break;
            //按访问次数
            case R.id.statistics_visit_rbtn:
                mScreenType = TYPE_VISIT;
                mPresenter.getData(mDateType, mScreenType, mCompanyId);
                break;
            //按分享次数
            case R.id.statistics_share_rbtn:
                mScreenType = TYPE_SHAER;
                mPresenter.getData(mDateType, mScreenType, mCompanyId);
                break;
            //按新用户人数
            case R.id.statistics_newuser_rbtn:
                mScreenType = TYPE_NEWUSER;
                mPresenter.getData(mDateType, mScreenType, mCompanyId);
                break;
            //按交易额
            case R.id.statistics_money_rbtn:
                mScreenType = TYPE_TRANSACTION;
                mPresenter.getData(mDateType, mScreenType, mCompanyId);
                break;
            default:
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getData(mDateType, mScreenType, mCompanyId);
    }

    /**
     * itme-头像的监听
     */
    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        start(CardFragment.newIntance(2, mAdapter.getData().get(position).getUserId()));
    }

    /**
     * item的监听
     */
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        String userId = mAdapter.getData().get(position).getUserId();
        String userName = mAdapter.getData().get(position).getUserName();
        if (TextUtils.isEmpty(userName)) {
            userName = mAdapter.getData().get(position).getAccountName();
        }

        start(AnalysisVisitRecordFragment.newIntance(userId, userName));
    }

    /**
     * 筛选日期的监听
     */
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch (tab.getPosition()) {
            case 0:
                mDateType = DAY_ALL;
                break;
            case 1:
                mDateType = DAY_30;
                break;
            case 2:
                mDateType = DAY_7;
                break;
            case 3:
                mDateType = DAY_CURR;
                break;
            default:
        }
        mPresenter.getData(mDateType, mScreenType, mCompanyId);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    /**
     * 转换时间类型，我这边的跟sdz那边的不一致,依次是30，7，当天
     *
     * @return
     */
    public static String getSDZdayType(int dateType) {
        String s = DayEnum.DAY_THIRTY.getValue();
        switch (dateType) {
            case StatisticsHomeFragment.DAY_ALL:
                s = DayEnum.DAY_ALL.getValue();
                break;
            case StatisticsHomeFragment.DAY_30:
                s = DayEnum.DAY_THIRTY.getValue();
                break;
            case StatisticsHomeFragment.DAY_7:
                s = DayEnum.DAY_SEVEN.getValue();
                break;
            case StatisticsHomeFragment.DAY_CURR:
                s = DayEnum.DAY_ZERO.getValue();
                break;
            default:
        }
        return s;
    }
}