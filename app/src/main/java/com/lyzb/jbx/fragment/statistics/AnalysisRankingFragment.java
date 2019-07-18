package com.lyzb.jbx.fragment.statistics;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener;
import com.like.longshaolib.base.BaseFragment;
import com.like.longshaolib.widget.AutoWidthTabLayout;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.statistics.AnalysisRankingAdapter;
import com.lyzb.jbx.fragment.me.access.AccessDetailFragment;
import com.lyzb.jbx.fragment.me.card.CardFragment;
import com.lyzb.jbx.model.cenum.AccessType;
import com.lyzb.jbx.model.cenum.DataType;
import com.lyzb.jbx.model.statistics.AnalysisRankingModel;
import com.lyzb.jbx.mvp.presenter.statistics.AnalysisRankingPresenter;
import com.lyzb.jbx.mvp.view.statistics.IAnalysisRankingView;
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
 * @role 各种排行
 * @time 2019 2019/4/17 10:51
 */

public class AnalysisRankingFragment extends BaseFragment<AnalysisRankingPresenter>
        implements IAnalysisRankingView, TabLayout.OnTabSelectedListener, OnRefreshListener {

    @BindView(R.id.statistics_title_tv)
    TextView mStatisticsTitleTv;
    @BindView(R.id.analysis_ranking_tab)
    AutoWidthTabLayout mAnalysisRankingTab;
    @BindView(R.id.analysis_ranking_recyclerview)
    RecyclerView mAnalysisRankingRecyclerview;
    @BindView(R.id.analysis_ranking_smartrefreshlayout)
    SmartRefreshLayout mAnalysisRankingSmartrefreshlayout;
    @BindView(R.id.analysis_ranking_notdata)
    View mNotDataView;
    Unbinder unbinder;

    private int mDateType = 0;
    private int mScreenType = 0;
    private String mDate = "";
    /**
     * 排行类型
     */
    private String rankingType;
    /**
     * 排行的筛选，默认全部
     */
    private String visitSort = "all";
    private String mCompanyId;
    private List<String> mScreenList = new ArrayList<>();//筛选的集合
    private List<Integer> mSecrenTypeList = new ArrayList<>();//筛选的类型集合

    private AnalysisRankingAdapter mAdapter;

    public static AnalysisRankingFragment newIntance(String departmentId, int dateType, int screenType, String visitSort) {
        AnalysisRankingFragment fragment = new AnalysisRankingFragment();
        Bundle args = new Bundle();
        args.putString(StatisticsHomeFragment.INTENTKEY_DEPARTMENTID, departmentId);
        args.putInt(StatisticsHomeFragment.INTENTKEY_DATE, dateType);
        args.putInt(StatisticsHomeFragment.INTENTKEY_SCREEN, screenType);
        args.putString(StatisticsHomeFragment.INTENTKEY_VISITSORT, visitSort);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        switch (mDateType) {
            case StatisticsHomeFragment.DAY_ALL:
                mDate = "全部";
                break;
            case StatisticsHomeFragment.DAY_30:
                mDate = "近30天";
                break;
            case StatisticsHomeFragment.DAY_7:
                mDate = "近7天";
                break;
            case StatisticsHomeFragment.DAY_CURR:
                mDate = "今日";
                break;
            default:
        }
        /**
         * 这里的类型本地定义的与后台不一致，需要转换一下
         */
        switch (mScreenType) {
            case StatisticsHomeFragment.TYPE_VISIT:
                rankingType = "visit";
                mStatisticsTitleTv.setText(mDate + "访问排行");
                initTab();
                break;
            case StatisticsHomeFragment.TYPE_SHAER:
                rankingType = "share";
                mStatisticsTitleTv.setText(mDate + "分享排行");
                initTab();
                break;
            case StatisticsHomeFragment.TYPE_NEWUSER:
                rankingType = "newUser";
                mStatisticsTitleTv.setText(mDate + "引流用户排行");
                mAnalysisRankingTab.setVisibility(View.GONE);
                break;
            case StatisticsHomeFragment.TYPE_TRANSACTION:
                rankingType = "order";
                mStatisticsTitleTv.setText(mDate + "交易排行");
                mAnalysisRankingTab.setVisibility(View.GONE);
                break;
            default:
        }
        mAnalysisRankingSmartrefreshlayout.setOnRefreshListener(this);
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        mAdapter = new AnalysisRankingAdapter(getActivity(), mScreenType, null);
        mAdapter.setLayoutManager(mAnalysisRankingRecyclerview);
        mAnalysisRankingRecyclerview.setAdapter(mAdapter);
        mAnalysisRankingRecyclerview.addOnItemTouchListener(new OnRecycleItemClickListener() {
            @Override
            public void onItemClick(BaseRecyleAdapter adapter, View view, int position) {
                super.onItemClick(adapter, view, position);
                String userId = mAdapter.getList().get(position).getUserId();
                String userName = mAdapter.getList().get(position).getUserName();
                if (TextUtils.isEmpty(userName)) {
                    userName = mAdapter.getList().get(position).getAccountName();
                }
                String dateType = StatisticsHomeFragment.getSDZdayType(mDateType);
                //根据不同的类型、跳转的页面不一样
                switch (mScreenType) {
                    //访问排行
                    case StatisticsHomeFragment.TYPE_VISIT:
                        start(AccessDetailFragment.newIntance(userId, userName, dateType,
                                AccessType.ACCESS.name(), mSecrenTypeList.get(mAnalysisRankingTab.getTabLayout().getSelectedTabPosition())));
                        break;
                    //分享排行
                    case StatisticsHomeFragment.TYPE_SHAER:
                        start(AccessDetailFragment.newIntance(userId, userName, dateType,
                                AccessType.SHARE.name(), mSecrenTypeList.get(mAnalysisRankingTab.getTabLayout().getSelectedTabPosition())));
                        break;
                    //引流新用户排行
                    case StatisticsHomeFragment.TYPE_NEWUSER:
                        start(AnalysisNewUserFragment.newIntance(userId, userName, mDateType));
                        break;
                    //名片交易排行
                    case StatisticsHomeFragment.TYPE_TRANSACTION:
                        start(AnalysisTransactionUserFragment.newIntance(userId, userName, mDateType));
                        break;
                    default:
                }
            }

            @Override
            public void onItemChildClick(BaseRecyleAdapter adapter, View view, int position) {
                super.onItemChildClick(adapter, view, position);
                //点头像时跳转名片
                start(CardFragment.newIntance(2, mAdapter.getList().get(position).getUserId()));
            }
        });
        //获取排行数据
        mPresenter.getAnalysisRanking(mCompanyId, rankingType, visitSort, mDateType);
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_analysis_ranking;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mDateType = bundle.getInt(StatisticsHomeFragment.INTENTKEY_DATE);
        mScreenType = bundle.getInt(StatisticsHomeFragment.INTENTKEY_SCREEN);
        mCompanyId = bundle.getString(StatisticsHomeFragment.INTENTKEY_DEPARTMENTID);
        visitSort = bundle.getString(StatisticsHomeFragment.INTENTKEY_VISITSORT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    /**
     * 筛选tab,根据选择的换成后台要的参数
     */
    private void initTab() {
        mScreenList.clear();
        mSecrenTypeList.clear();
        mAnalysisRankingTab.addTab("全部");
        mScreenList.add("all");
        mSecrenTypeList.add(DataType.ALL.getValue());
        mAnalysisRankingTab.addTab("名片");
        mScreenList.add("ext");
        mSecrenTypeList.add(DataType.CARD.getValue());
        if (mScreenType == StatisticsHomeFragment.TYPE_VISIT) {
            mAnalysisRankingTab.addTab("商品");
            mScreenList.add("goods");
            mSecrenTypeList.add(DataType.GOODS.getValue());
        }
        mAnalysisRankingTab.addTab("动态");
        mScreenList.add("topic");
        mSecrenTypeList.add(DataType.DYNAMIC.getValue());
        mAnalysisRankingTab.addTab("热文");
        mScreenList.add("hotText");
        mSecrenTypeList.add(DataType.ACRTICE.getValue());

        switch (visitSort) {
            case "all": {
                mAnalysisRankingTab.getTabLayout().getTabAt(0).select();
                break;
            }
            case "ext": {
                mAnalysisRankingTab.getTabLayout().getTabAt(1).select();
                break;
            }
            case "goods": {
                mAnalysisRankingTab.getTabLayout().getTabAt(2).select();
                break;
            }
            case "topic": {
                if (mScreenType == StatisticsHomeFragment.TYPE_VISIT) {
                    mAnalysisRankingTab.getTabLayout().getTabAt(3).select();
                } else {
                    mAnalysisRankingTab.getTabLayout().getTabAt(2).select();
                }
                break;
            }
            case "hotText": {
                if (mScreenType == StatisticsHomeFragment.TYPE_VISIT) {
                    mAnalysisRankingTab.getTabLayout().getTabAt(4).select();
                } else {
                    mAnalysisRankingTab.getTabLayout().getTabAt(3).select();
                }
                break;
            }
        }

        mAnalysisRankingTab.addOnTabSelectedListener(this);
    }

    @OnClick(R.id.statistics_back_iv)
    public void onViewClicked() {
        pop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onData(AnalysisRankingModel b) {
        mNotDataView.setVisibility(View.GONE);
        mAnalysisRankingSmartrefreshlayout.finishRefresh();
        mAdapter.setVisitSort(visitSort);
        mAdapter.update(b.getDataList());
    }

    @Override
    public void onFail(String msg) {
        mAnalysisRankingSmartrefreshlayout.finishRefresh();
        showToast(msg);
        mNotDataView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNull() {
        mAnalysisRankingSmartrefreshlayout.finishRefresh();
        mNotDataView.setVisibility(View.VISIBLE);
    }

    /**
     * 筛选的监听，访问排行有商品，分享没有
     *
     * @param tab
     */
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        visitSort = mScreenList.get(tab.getPosition());
        mPresenter.getAnalysisRanking(mCompanyId, rankingType, visitSort, mDateType);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getAnalysisRanking(mCompanyId, rankingType, visitSort, mDateType);
    }
}
