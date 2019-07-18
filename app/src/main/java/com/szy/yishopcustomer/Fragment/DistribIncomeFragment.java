package com.szy.yishopcustomer.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Distrib.DistribIncomeModel;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

/**
 * Created by liwei on 2017/7/18.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class DistribIncomeFragment extends YSCBaseFragment {

    private static final int HTTP_WHAT_DISTRIB_APPLY = 1;

    @BindView(R.id.distrib_income_total_amount)
    TextView mTotalAmount;
    @BindView(R.id.distrib_income_deposit_money)
    TextView mDepositMoney;
    @BindView(R.id.distrib_income_total_dis_money)
    TextView mTotalDisMoney;
    @BindView(R.id.distrib_income_total_income_money)
    TextView mTotalIncomeMoney;

    @BindView(R.id.distrib_income_add)
    TextView mDistribIncomeAdd;
    @BindView(R.id.distrib_income_details_layout)
    LinearLayout mDistribIncomeDetailLayout;
    @BindView(R.id.distrib_income_record_layout)
    LinearLayout mDistribIncomeRecordLayout;

    private String distribText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_distrib_income;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mDistribIncomeAdd.setOnClickListener(this);
        mDistribIncomeDetailLayout.setOnClickListener(this);
        mDistribIncomeRecordLayout.setOnClickListener(this);
        refresh();
        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.distrib_income_add:
                EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_OPEN_DISTRIB_INCOME_ADD.getValue()));
                break;
            case R.id.distrib_income_details_layout:
                EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_OPEN_DISTRIB_INCOME_DETAILS.getValue()));
                break;
            case R.id.distrib_income_record_layout:
                EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_OPEN_DISTRIB_INCOME_RECORD.getValue()));
                break;
            default:
                super.onClick(v);
        }
    }


    public void refresh() {
        CommonRequest request = new CommonRequest(Api.API_DISTRIB_INCOME, HTTP_WHAT_DISTRIB_APPLY);
        addRequest(request);
    }


    //接口成功回调
    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (what) {
            case HTTP_WHAT_DISTRIB_APPLY:
                refreshSucceed(response);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }


    //设置数据
    private void refreshSucceed(String response) {
        HttpResultManager.resolve(response, DistribIncomeModel.class, new HttpResultManager
                .HttpResultCallBack<DistribIncomeModel>() {
            @Override
            public void onSuccess(DistribIncomeModel back) {

                mTotalAmount.setText(Utils.formatMoney(mTotalAmount.getContext(), back.data.shop_info.total_amount));
                mDepositMoney.setText(back.data.deposit_money);
                mTotalDisMoney.setText(back.data.total_dis_money);
                mTotalIncomeMoney.setText(back.data.total_income_money);

            }
        });
    }

}
