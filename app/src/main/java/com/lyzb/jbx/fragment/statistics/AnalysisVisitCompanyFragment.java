package com.lyzb.jbx.fragment.statistics;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.like.longshaolib.base.BaseFragment;
import com.like.longshaolib.widget.BezierView;
import com.like.utilslib.app.CommonUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.model.statistics.AnalyVisitCompanyModel;
import com.lyzb.jbx.mvp.presenter.statistics.AnalysisVisitCompanyPresenter;
import com.lyzb.jbx.mvp.view.statistics.IAnalysisVisitCompanyView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author wyx
 * @role 分析-访问次数(企业)
 * @time 2019 2019/4/16 17:24
 */

public class AnalysisVisitCompanyFragment extends BaseFragment<AnalysisVisitCompanyPresenter>
        implements IAnalysisVisitCompanyView {

    @BindView(R.id.statistics_title_tv)
    TextView mStatisticsTitleTv;
    @BindView(R.id.statistics_right_tv)
    TextView mStatisticsRightTv;
    @BindView(R.id.analysis_visit_number_tv)
    TextView mAnalysisVisitNumberTv;
    @BindView(R.id.analysis_visit_bcardnumber_tv)
    TextView mAnalysisVisitBcardnumberTv;
    @BindView(R.id.analysis_visit_goodsnumber_tv)
    TextView mAnalysisVisitGoodsnumberTv;
    @BindView(R.id.analysis_visit_dynamic_number_tv)
    TextView mAnalysisVisitDynamicNumberTv;
    @BindView(R.id.analysis_visit_app_number_bezierview)
    BezierView mAnalysisVisitAppNumberBezierview;
    @BindView(R.id.analysis_visit_wxmini_number_bezierview)
    BezierView mAnalysisVisitWxminiNumberBezierview;
    @BindView(R.id.analysis_visit_share_number_bezierview)
    BezierView mAnalysisVisitShareNumberBezierview;
    @BindView(R.id.analysis_visit_other_number_bezierview)
    BezierView mAnalysisVisitOtherNumberBezierview;
    Unbinder unbinder;
    @BindView(R.id.analysis_visit_hottext_number_tv)
    TextView mAnalysisVisitHottextNumberTv;

    private int mDateType;
    private String mComanyId;

    public static AnalysisVisitCompanyFragment newIntance(String departmentId, int dateType) {
        AnalysisVisitCompanyFragment fragment = new AnalysisVisitCompanyFragment();
        Bundle args = new Bundle();
        args.putString(StatisticsHomeFragment.INTENTKEY_DEPARTMENTID, departmentId);
        args.putInt(StatisticsHomeFragment.INTENTKEY_DATE, dateType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        mStatisticsRightTv.setText("访问排行");
        mStatisticsRightTv.setVisibility(View.VISIBLE);
        switch (mDateType) {
            case StatisticsHomeFragment.DAY_ALL:
                mStatisticsTitleTv.setText("全部访客分析");
                break;
            case StatisticsHomeFragment.DAY_30:
                mStatisticsTitleTv.setText("近30天访客分析");
                break;
            case StatisticsHomeFragment.DAY_7:
                mStatisticsTitleTv.setText("近7天访客分析");
                break;
            case StatisticsHomeFragment.DAY_CURR:
                mStatisticsTitleTv.setText("今日访客分析");
                break;
            default:
        }
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        mPresenter.getVisitCompanyData(mDateType, mComanyId);
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_analysis_visit;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mComanyId = bundle.getString(StatisticsHomeFragment.INTENTKEY_DEPARTMENTID);
            mDateType = bundle.getInt(StatisticsHomeFragment.INTENTKEY_DATE);
        }
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

    @OnClick({R.id.statistics_back_iv, R.id.statistics_right_tv, R.id.analysis_visit_bcardnumber_tv,
            R.id.analysis_visit_goodsnumber_tv, R.id.analysis_visit_dynamic_number_tv,
            R.id.analysis_visit_hottext_number_tv})

    public void onViewClicked(View view) {
        switch (view.getId()) {
            //返回
            case R.id.statistics_back_iv:
                pop();
                break;
            //排行
            case R.id.statistics_right_tv:
                start(AnalysisRankingFragment.newIntance(mComanyId, mDateType, StatisticsHomeFragment.TYPE_VISIT, "all"));
                break;
            //名片次数
            case R.id.analysis_visit_bcardnumber_tv:
                start(AnalysisRankingFragment.newIntance(mComanyId, mDateType, StatisticsHomeFragment.TYPE_VISIT, "ext"));
                break;
            //商品次数
            case R.id.analysis_visit_goodsnumber_tv:
                start(AnalysisRankingFragment.newIntance(mComanyId, mDateType, StatisticsHomeFragment.TYPE_VISIT, "goods"));
                break;
            //动态次数
            case R.id.analysis_visit_dynamic_number_tv:
                start(AnalysisRankingFragment.newIntance(mComanyId, mDateType, StatisticsHomeFragment.TYPE_VISIT, "topic"));
                break;
            //热文次数
            case R.id.analysis_visit_hottext_number_tv:
                start(AnalysisRankingFragment.newIntance(mComanyId, mDateType, StatisticsHomeFragment.TYPE_VISIT, "hotText"));
                break;
            default:
        }
    }

    @Override
    public void onData(AnalyVisitCompanyModel b) {
        if (b == null) return;
        mAnalysisVisitAppNumberBezierview.setMaxValue(CommonUtil.converToT(b.getAppRatioNum(), 0.0f));
        mAnalysisVisitAppNumberBezierview.startAnimation();

        mAnalysisVisitWxminiNumberBezierview.setMaxValue(CommonUtil.converToT(b.getMiniRatioNum(), 0.0f));
        mAnalysisVisitWxminiNumberBezierview.startAnimation();

        mAnalysisVisitShareNumberBezierview.setMaxValue(CommonUtil.converToT(b.getWxRatioNum(), 0.0f));
        mAnalysisVisitShareNumberBezierview.startAnimation();

        mAnalysisVisitOtherNumberBezierview.setMaxValue(CommonUtil.converToT(b.getOtherRatioNum(), 0.0f));
        mAnalysisVisitOtherNumberBezierview.startAnimation();

        //四个数量
        mAnalysisVisitNumberTv.setText(String.valueOf(b.getViewNum()));
        mAnalysisVisitBcardnumberTv.setText(String.format("%d次\n名片", b.getExtNum()));
        mAnalysisVisitGoodsnumberTv.setText(String.format("%d次\n商品", b.getShopNum()));
        mAnalysisVisitDynamicNumberTv.setText(String.format("%d次\n动态", b.getTopicNum()));
        mAnalysisVisitHottextNumberTv.setText(String.format("%d次\n热文", b.getHotNum()));
    }

    @Override
    public void onFail(String msg) {
        //TODO
        showToast(msg);
    }

    @Override
    public void onNull() {
        //TODO
    }

}
