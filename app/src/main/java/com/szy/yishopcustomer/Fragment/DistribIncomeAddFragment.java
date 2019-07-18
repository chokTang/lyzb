package com.szy.yishopcustomer.Fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.*;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.common.ResponseModel.Common.ResponseCommonModel;
import com.szy.common.View.CommonEditText;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Distrib.DistribIncomeAddModel;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.JSON;
import com.szy.yishopcustomer.Util.Utils;
import com.yolanda.nohttp.RequestMethod;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

/**
 * Created by liwei on 2017/7/18.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class DistribIncomeAddFragment extends YSCBaseFragment implements View.OnTouchListener,TextWatcher {

    private static final int HTTP_WHAT_DISTRIB_INCOME_ADD = 1;
    private static final int HTTP_WHAT_DISTRIB_INCOME_ADD_SUBMIT = 2;

    @BindView(R.id.fragment_distrib_add_deposit_money)
    TextView mDepositMoney;
    @BindView(R.id.fragment_distrib_add_dis_reserve_money)
    TextView mDisReserveMoney;
    @BindView(R.id.fragment_destrib_income_add_edittext)
    CommonEditText mEdittext;
    @BindView(R.id.warning_layout)
    LinearLayout mTipLayout;
    @BindView(R.id.warning_tip)
    TextView mWarningTip;
    @BindView(R.id.fragment_distrib_income_add_tip_two)
    TextView mTipTwo;

    @BindView(R.id.submit_button)
    Button mSubmitButton;
    private String mMoney;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_distrib_income_add;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        view.setOnTouchListener(this);
        mSubmitButton.setOnClickListener(this);
        mEdittext.addTextChangedListener(this);
        mSubmitButton.setText(getResources().getString(R.string.submit));
        refresh();
        return view;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        mMoney = mEdittext.getText().toString();
        mSubmitButton.setEnabled(isFinishButtonEnabled());
        if (mMoney.length() != 0) {
            mTipLayout.setVisibility(View.GONE);
        } else {
            mTipLayout.setVisibility(View.VISIBLE);
            mWarningTip.setText("提现金额不能为空");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_button:
                submit();
                break;
            default:
                super.onClick(v);
        }
    }

    private void submit() {
        CommonRequest request = new CommonRequest(Api.API_DISTRIB_INCOME_ADD,
                HTTP_WHAT_DISTRIB_INCOME_ADD_SUBMIT, RequestMethod.POST);
        request.add("DistribIncomeModel[money]",mEdittext.getText().toString());
        request.setAjax(true);
        addRequest(request);
    }

    public void refresh() {
        CommonRequest request = new CommonRequest(Api.API_DISTRIB_INCOME_ADD,
                HTTP_WHAT_DISTRIB_INCOME_ADD);
        addRequest(request);
    }


    //接口成功回调
    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (what) {
            case HTTP_WHAT_DISTRIB_INCOME_ADD:
                refreshSucceed(response);
                break;
            case HTTP_WHAT_DISTRIB_INCOME_ADD_SUBMIT:
                submitSucceed(response);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }


    //设置数据
    private void refreshSucceed(String response) {
        Utils.showSoftInputFromWindowTwo(getActivity(),mEdittext);

        HttpResultManager.resolve(response, DistribIncomeAddModel.class, new HttpResultManager
                .HttpResultCallBack<DistribIncomeAddModel>() {
            @Override
            public void onSuccess(DistribIncomeAddModel back) {
                mDepositMoney.setText(back.data.deposit_money);
                mDisReserveMoney.setText("预留金额" + back.data.dis_reserve_money + "元");
                mTipTwo.setText("商家账户中必须预留" + back.data.dis_reserve_money + "元现金，此金额不可提现");
            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (hidden) {
            Utils.hideKeyboard(mEdittext);
        } else {
            Utils.showSoftInputFromWindowTwo(getActivity(),mEdittext);

        }
    }

    // onTouch事件 将上层的触摸事件拦截
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }


    private void submitSucceed(String response) {
        ResponseCommonModel back = JSON.parseObject(response, ResponseCommonModel.class);
        if(back.code ==0){//申请成功
            EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_OPEN_DISTRIB_INCOME_RECORD.getValue()));
        }else if(back.code ==  1){//提现金额不足/提现失败
            Toast.makeText(getActivity(),back.message,Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isFinishButtonEnabled() {
        boolean isEnabled = false;
        isEnabled = !Utils.isNull(mEdittext.getText().toString());
        return isEnabled;
    }

}
