package com.szy.yishopcustomer.Fragment;


import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.szy.common.Other.CommonRequest;
import com.szy.yishopcustomer.Constant.Api;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.BaseEntity;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewModel.RechargeCardInfoModel;
import com.yolanda.nohttp.RequestMethod;

import butterknife.BindView;


public class RechargeCardFragment extends YSCBaseFragment {

    private static final int HTTP_WHAT_USER_RECHARE_CARD = 1;
    private static final int HTTP_WHAT_USER_RECHARE_CARD_INFO = 2;

    @BindView(R.id.textView_cash_withdrawal)
    TextView textView_cash_withdrawal;
    @BindView(R.id.textView_non_present_amount)
    TextView textView_non_present_amount;
    @BindView(R.id.edittext_card_number)
    EditText edittext_card_number;
    @BindView(R.id.edittext_card_pwd)
    EditText edittext_card_pwd;

    @BindView(R.id.fragment_show_password)
    ImageView fragment_show_password;

    @BindView(R.id.submit_button)
    Button fragment_button_recharge;

    private boolean mbDisplayFlg = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_recharge_card;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        fragment_button_recharge.setOnClickListener(this);
        fragment_show_password.setOnClickListener(this);

        fragment_button_recharge.setEnabled(true);
        fragment_button_recharge.setText(getResources().getString(R.string.activityRecharge));

        refresh();
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit_button:
                rechargeClick();
                break;
            case R.id.fragment_show_password:
                if (!mbDisplayFlg) {
                    edittext_card_pwd.setTransformationMethod(HideReturnsTransformationMethod
                            .getInstance());
                    fragment_show_password.setBackgroundResource(R.mipmap.btn_show_password_normal);
                    edittext_card_pwd.setSelection(edittext_card_pwd.getText().toString().length());
                } else {
                    edittext_card_pwd.setTransformationMethod(PasswordTransformationMethod
                            .getInstance());
                    fragment_show_password.setBackgroundResource(R.mipmap
                            .btn_show_password_disabled);

                    edittext_card_pwd.setSelection(edittext_card_pwd.getText().toString().length());
                }
                mbDisplayFlg = !mbDisplayFlg;
                break;
        }
    }

    void rechargeClick() {
        String card_number = edittext_card_number.getText().toString().trim();
        String card_pwd = edittext_card_pwd.getText().toString().trim();
        if (TextUtils.isEmpty(card_number)) {
            toast("请输入储值卡卡号！");
            return;
        }
        if (TextUtils.isEmpty(card_pwd)) {
            toast("请输入储值卡密码！");
            return;
        }

        CommonRequest request = new CommonRequest(Api.API_USER_CARD_RECHARGE_INFO,
                HTTP_WHAT_USER_RECHARE_CARD, RequestMethod.POST);
        request.add("RechargeCardModel[card_number]", card_number);
        request.add("RechargeCardModel[pass_word]", card_pwd);
        request.setAjax(true);
        addRequest(request);
    }

    public void refresh() {
        CommonRequest request = new CommonRequest(Api.API_USER_CARD_RECHARGE_INFO,
                HTTP_WHAT_USER_RECHARE_CARD_INFO);
        request.setAjax(true);
        addRequest(request);
    }


    //接口成功回调
    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (what) {
            case HTTP_WHAT_USER_RECHARE_CARD_INFO:
                refreshSucceedInfo(response);
                break;
            case HTTP_WHAT_USER_RECHARE_CARD:
                refreshSucceed(response);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    //设置数据
    private void refreshSucceed(String response) {
        HttpResultManager.resolve(response, BaseEntity.class, new HttpResultManager
                .HttpResultCallBack<BaseEntity>() {
            @Override
            public void onSuccess(BaseEntity back) {
                toast("充值成功");
                setResult(getActivity().RESULT_OK);
                finish();
            }
        }, true);
    }

    //设置数据
    private void refreshSucceedInfo(String response) {
        Utils.showSoftInputFromWindow(getActivity(), edittext_card_number);
        HttpResultManager.resolve(response, RechargeCardInfoModel.class, new HttpResultManager
                .HttpResultCallBack<RechargeCardInfoModel>() {
            @Override
            public void onSuccess(RechargeCardInfoModel back) {
                textView_cash_withdrawal.setText(back.data.user_info.user_money);
                textView_non_present_amount.setText("不可提现资金" + back.data.user_info
                        .user_money_limit + "元");
            }

            @Override
            public void onEmptyData(int state) {
            }

            @Override
            public void onFailure(String message) {
            }
        });
    }

}
