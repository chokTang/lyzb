package com.lyzb.jbx.fragment.statistics;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.like.longshaolib.base.BaseFragment;
import com.like.longshaolib.widget.AutoWidthTabLayout;
import com.lyzb.jbx.R;
import com.lyzb.jbx.fragment.me.access.AccessDayDetailFragment;
import com.lyzb.jbx.fragment.me.access.AccessShareDetailFragment;
import com.lyzb.jbx.model.cenum.DayEnum;
import com.lyzb.jbx.model.statistics.AnalysisVisitUserModel;
import com.lyzb.jbx.mvp.presenter.statistics.AnalysisVisitUserPresenter;
import com.lyzb.jbx.mvp.view.statistics.IAnalysisVisitUserView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author wyx
 * @role 访问xx的人
 * @time 2019 2019/4/17 15:30
 */

public class AnalysisVisitRecordFragment extends BaseFragment<AnalysisVisitUserPresenter>
        implements IAnalysisVisitUserView {

    @BindView(R.id.statistics_title_tv)
    TextView mStatisticsTitleTv;
    @BindView(R.id.visit_record_tab)
    AutoWidthTabLayout mVisitRecordTab;
    @BindView(R.id.visit_record_transaction_number_tv)
    TextView mVisitRecordTransactionNumberTv;
    @BindView(R.id.visit_record_visit_number_tv)
    TextView mVisitRecordVisitNumberTv;
    @BindView(R.id.visit_record_share_number_tv)
    TextView mVisitRecordShareNumberTv;
    @BindView(R.id.visit_record_newuser_number_tv)
    TextView mVisitRecordNewuserNumberTv;
    Unbinder unbinder;

    private String mUserName;
    private String mUserId;
    private int mDateType = StatisticsHomeFragment.DAY_ALL;


    public static AnalysisVisitRecordFragment newIntance(String userId, String userName) {
        AnalysisVisitRecordFragment fragment = new AnalysisVisitRecordFragment();
        Bundle bundle = new Bundle();
        bundle.putString(StatisticsHomeFragment.INTENTKEY_USERID, userId);
        bundle.putString(StatisticsHomeFragment.INTENTKEY_USERNAME, userName);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        mStatisticsTitleTv.setText(mUserName + "的客户追踪");
        mVisitRecordTab.addTab("全部");
        mVisitRecordTab.addTab("近30天");
        mVisitRecordTab.addTab("近7天");
        mVisitRecordTab.addTab("今日");
        mVisitRecordTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        mDateType = StatisticsHomeFragment.DAY_ALL;
                        break;
                    case 1:
                        mDateType = StatisticsHomeFragment.DAY_30;
                        break;
                    case 2:
                        mDateType = StatisticsHomeFragment.DAY_7;
                        break;
                    case 3:
                        mDateType = StatisticsHomeFragment.DAY_CURR;
                        break;
                    default:
                }
                mPresenter.getVisitData(mDateType, mUserId);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        mPresenter.getVisitData(StatisticsHomeFragment.DAY_ALL, mUserId);
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_visit_record;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mUserName = bundle.getString(StatisticsHomeFragment.INTENTKEY_USERNAME);
            mUserId = bundle.getString(StatisticsHomeFragment.INTENTKEY_USERID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.statistics_back_iv, R.id.visit_record_visit_rl, R.id.visit_record_share_rl,
            R.id.visit_record_newuser_rl, R.id.visit_record_transaction_rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //返回
            case R.id.statistics_back_iv:
                pop();
                break;
            //访问次数
            case R.id.visit_record_visit_rl:
                start(AccessDayDetailFragment.newIntance(mUserId, mUserName, getSDZdayType()));
                break;
            //分享次数
            case R.id.visit_record_share_rl:
                start(AccessShareDetailFragment.newIntance(mUserId, mUserName, getSDZdayType()));
                break;
            //新用户
            case R.id.visit_record_newuser_rl:
                start(AnalysisNewUserFragment.newIntance(mUserId, mUserName, mDateType));
                break;
            //名片交易
            case R.id.visit_record_transaction_rl:
                start(AnalysisTransactionUserFragment.newIntance(mUserId, mUserName, mDateType));
                break;
            default:
        }

    }

    /**
     * 转换时间，我这边的跟sdz那边的不一致,依次是30，7，当天
     *
     * @return
     */
    private String getSDZdayType() {
        String s = DayEnum.DAY_ALL.getValue();
        switch (mDateType) {
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

    @Override
    public void onData(AnalysisVisitUserModel b) {
        mVisitRecordTransactionNumberTv.setText(String.valueOf(b.getOrderNum()));
        mVisitRecordVisitNumberTv.setText(String.valueOf(b.getViewNum()));
        mVisitRecordShareNumberTv.setText(String.valueOf(b.getShareNum()));
        mVisitRecordNewuserNumberTv.setText(String.valueOf(b.getUserNum()));
    }

    @Override
    public void onFail(String msg) {
        showToast(msg);
    }

    @Override
    public void onNull() {

    }
}
