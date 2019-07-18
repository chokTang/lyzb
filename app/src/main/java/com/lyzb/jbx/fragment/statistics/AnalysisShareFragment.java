package com.lyzb.jbx.fragment.statistics;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.like.longshaolib.base.BaseFragment;
import com.lyzb.jbx.R;
import com.lyzb.jbx.model.statistics.AnalysisShareModel;
import com.lyzb.jbx.mvp.presenter.statistics.AnalysisSharePresenter;
import com.lyzb.jbx.mvp.view.statistics.IAnalysisShareView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author wyx
 * @role 分析-分享次数
 * @time 2019 2019/4/16 17:24
 */

public class AnalysisShareFragment extends BaseFragment<AnalysisSharePresenter>
        implements IAnalysisShareView {

    Unbinder unbinder;
    @BindView(R.id.statistics_title_tv)
    TextView mStatisticsTitleTv;
    @BindView(R.id.statistics_right_tv)
    TextView mStatisticsRightTv;
    @BindView(R.id.analysis_share_number_tv)
    TextView mAnalysisShareNumberTv;
    @BindView(R.id.analysis_share_bcard_number_tv)
    TextView mAnalysisShareBcardNumberTv;
    @BindView(R.id.analysis_share_dynamic_number_tv)
    TextView mAnalysisShareDynamicNumberTv;
    @BindView(R.id.analysis_share_hottext_number_tv)
    TextView analysis_share_hottext_number_tv;

    private int mDateType;
    private String mCompanyId;

    public static AnalysisShareFragment newIntance(String departmentId, int dateType) {
        AnalysisShareFragment fragment = new AnalysisShareFragment();
        Bundle args = new Bundle();
        args.putString(StatisticsHomeFragment.INTENTKEY_DEPARTMENTID, departmentId);
        args.putInt(StatisticsHomeFragment.INTENTKEY_DATE, dateType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        mStatisticsRightTv.setText("分享排行");
        mStatisticsRightTv.setVisibility(View.VISIBLE);
        switch (mDateType) {
            case StatisticsHomeFragment.DAY_ALL:
                mStatisticsTitleTv.setText("全部分享数据分析");
                break;
            case StatisticsHomeFragment.DAY_30:
                mStatisticsTitleTv.setText("近30天分享数据分析");
                break;
            case StatisticsHomeFragment.DAY_7:
                mStatisticsTitleTv.setText("近7天分享数据分析");
                break;
            case StatisticsHomeFragment.DAY_CURR:
                mStatisticsTitleTv.setText("今日分享数据分析");
                break;
            default:
        }
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        mPresenter.getData(mCompanyId, mDateType);
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_analysis_share;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mDateType = bundle.getInt(StatisticsHomeFragment.INTENTKEY_DATE);
            mCompanyId = bundle.getString(StatisticsHomeFragment.INTENTKEY_DEPARTMENTID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick({R.id.statistics_back_iv, R.id.statistics_right_tv, R.id.layout_card, R.id.layout_dynamic, R.id.layout_hot})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //返回键
            case R.id.statistics_back_iv:
                pop();
                break;
            //分享排行
            case R.id.layout_card:
                start(AnalysisRankingFragment.newIntance(mCompanyId, mDateType, StatisticsHomeFragment.TYPE_SHAER, "ext"));
                break;
            case R.id.layout_dynamic:
                start(AnalysisRankingFragment.newIntance(mCompanyId, mDateType, StatisticsHomeFragment.TYPE_SHAER, "topic"));
                break;
            case R.id.layout_hot:
                start(AnalysisRankingFragment.newIntance(mCompanyId, mDateType, StatisticsHomeFragment.TYPE_SHAER, "hotText"));
                break;
            case R.id.statistics_right_tv:
                start(AnalysisRankingFragment.newIntance(mCompanyId, mDateType, StatisticsHomeFragment.TYPE_SHAER, "all"));
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
    public void onFail(String msg) {
    }

    @Override
    public void onData(AnalysisShareModel b) {
        mAnalysisShareNumberTv.setText(String.valueOf(b.getShareSum()));
        mAnalysisShareBcardNumberTv.setText(getActivity().getString(R.string.analysis_share, b.getShareExtSum(), b.getUserExtNum(), b.getUserExtSumNum()));
        mAnalysisShareDynamicNumberTv.setText(getActivity().getString(R.string.analysis_share, b.getShareTopicSum(), b.getUserTopicNum(), b.getUserTopicSumNum()));
        analysis_share_hottext_number_tv.setText(getActivity().getString(R.string.analysis_share, b.getShareHotSum(), b.getUserHotNum(), b.getUserHotSumNum()));
    }

    @Override
    public void onNull() {
    }
}
