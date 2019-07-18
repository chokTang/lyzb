package com.szy.yishopcustomer.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.*;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.yishopcustomer.Activity.BindAccountActivity;
import com.szy.yishopcustomer.Activity.DepositActivity;
import com.szy.yishopcustomer.Activity.DepositListActivity;
import com.szy.yishopcustomer.Activity.DetailActivity;
import com.szy.yishopcustomer.Activity.LoginActivity;
import com.szy.yishopcustomer.Activity.MoneyIntoActivity;
import com.szy.yishopcustomer.Activity.RechargeActivity;
import com.szy.yishopcustomer.Activity.RechargeRecordActivity;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Other.BottomMenuController;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.DetailModel.Model;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;

import butterknife.BindView;

/**
 * Created by liuzhifeng on 16/7/18.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class AccountBalanceFragment extends YSCBaseFragment {
    @BindView(R.id.fragment_accountbalance_detail)
    LinearLayout mDetail;
    @BindView(R.id.fragment_accountbalance_deposit)
    LinearLayout mDeposit;
    @BindView(R.id.fragment_accountbalance_prepaid)
    LinearLayout mPrepaid;
    @BindView(R.id.fragment_account_top_up_record)
    LinearLayout mTopUpRecord;
    @BindView(R.id.fragment_account_balance_bind)
    LinearLayout mBind;
    @BindView(R.id.login_button)
    Button goLoginButton;
    @BindView(R.id.no_login)
    LinearLayout noLogin;
    @BindView(R.id.fragment_accountbalance_withddrawal)
    LinearLayout mWithddrawal;
    @BindView(R.id.fragment_account_money_into)
    LinearLayout mMoneyInto;

    @BindView(R.id.view1)
    View lineView;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.view3)
    View view3;


    @BindView(R.id.fragment_accountbalance_available_funds_text_view)
    TextView mAvailableFunds;
    @BindView(R.id.fragment_accountbalance_freezefunds_text_view)
    TextView mFreezeFunds;
    private String userMoneyFormat;

    @Override
    public void onClick(View v) {
        if (Utils.isDoubleClick()) {
            return;
        }
        switch (v.getId()) {
            case R.id.fragment_accountbalance_prepaid:
                startActivity(new Intent().setClass(getActivity(), RechargeActivity.class));
                break;
            case R.id.fragment_accountbalance_deposit:
                startActivity(new Intent().setClass(getActivity(), DepositListActivity.class));
                break;
            case R.id.fragment_accountbalance_detail:
                startActivity(new Intent().setClass(getActivity(), DetailActivity.class));
                break;
            /** 集食惠 预存货款迁入 跳转 */
            case R.id.fragment_account_money_into:
                startActivity(new Intent().setClass(getActivity(), MoneyIntoActivity.class));
                break;
            case R.id.fragment_accountbalance_withddrawal:
                Intent intent = new Intent();
                intent.setClass(getActivity(), DepositActivity.class);
                intent.putExtra("user_money_format", userMoneyFormat);
                startActivity(intent);
                break;
            case R.id.login_button:
                openLoginActivity();
                break;
            case R.id.fragment_account_balance_bind:
                openBindAccountActivity();
                break;
            case R.id.fragment_account_top_up_record:
                openTopUpRecordActivity();
                break;
            default:
                super.onClick(v);
                break;
        }
    }

    private void openTopUpRecordActivity() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), RechargeRecordActivity.class);
        startActivity(intent);
    }

    private void openBindAccountActivity() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), BindAccountActivity.class);
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        BottomMenuController.init(getContext(), v);

        mPrepaid.setOnClickListener(this);
        mDeposit.setOnClickListener(this);
        mDetail.setOnClickListener(this);
        mMoneyInto.setOnClickListener(this);

        mWithddrawal.setOnClickListener(this);
        goLoginButton.setOnClickListener(this);
        mTopUpRecord.setOnClickListener(this);
        mBind.setOnClickListener(this);
        getData();
        return v;
    }

    @Override
    public void onEvent(CommonEvent event) {
        switch (EventWhat.valueOf(event.getWhat())) {
            case EVENT_PAY_FINISH:
                getData();
                break;
            case EVENT_MONEY_CHANGE:
                getData();
                break;
            case EVENT_LOGIN:
                getData();
                break;
            default:
                super.onEvent(event);
                break;
        }
    }

    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_CAPITAL_ACCOUNT:
                HttpResultManager.resolve(response, Model.class, new HttpResultManager.HttpResultCallBack<Model>() {
                    @Override
                    public void onSuccess(Model Data) {
                        noLogin.setVisibility(View.GONE);
                        userMoneyFormat = Data.data.user.user_money_format;
                        mAvailableFunds.setText(Data.data.user.user_money_format);

                        //mFreezeFunds.setText("不可提现资金：" + Data.data.user.user_money_limit_format);

                        if ("1".equals(Data.data.is_deposit)) {
                            mWithddrawal.setVisibility(View.VISIBLE);
                            lineView.setVisibility(View.VISIBLE);
                            mDeposit.setVisibility(View.VISIBLE);
                            mBind.setVisibility(View.VISIBLE);
                            view2.setVisibility(View.VISIBLE);
                            view3.setVisibility(View.VISIBLE);
                        } else {
                            mWithddrawal.setVisibility(View.GONE);
                            lineView.setVisibility(View.GONE);
                            mDeposit.setVisibility(View.GONE);
                            mBind.setVisibility(View.GONE);
                            view2.setVisibility(View.GONE);
                            view3.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onLogin() {
                        noLogin.setVisibility(View.VISIBLE);
                    }
                });
                break;

            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_account_balance;
    }

    private void getData() {
        CommonRequest request = new CommonRequest(Api.API_CAPITAL_ACCOUNT, HttpWhat
                .HTTP_CAPITAL_ACCOUNT.getValue());
        addRequest(request);
    }

    private void openLoginActivity() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), LoginActivity.class);
        startActivity(intent);
    }
}
