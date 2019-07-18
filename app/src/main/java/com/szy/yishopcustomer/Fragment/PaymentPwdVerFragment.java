package com.szy.yishopcustomer.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.EditText;
import android.widget.ImageView;

import com.szy.common.Other.CommonRequest;
import com.szy.yishopcustomer.Activity.BalancePayResultActivity;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.BaseEntity;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.yolanda.nohttp.RequestMethod;

import butterknife.BindView;

/**
 * Created by Smart on 2018/1/17.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class PaymentPwdVerFragment extends YSCBaseFragment {

    public static final String PAY_TYPE = "type";

    public static final int TYPE_MERCHANT_PAY = 0;
    public static final int TYPE_OUT_LINE_PAY = 1;

    @BindView(R.id.fragment_show_password)
    ImageView mShowPassword;
    @BindView(R.id.editTextPaymentPwd)
    EditText editTextPaymentPwd;

    @BindView(R.id.fragment_login_action_button)
    View fragment_login_action_button;

    private String payment_iden;
    private String amount;
    private String shop_id;
    private String points;

    private String balance_password;

    boolean mbDisplayFlg = false;

    private int type = TYPE_MERCHANT_PAY;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_payment_pwd_ver;

        Intent intent = getActivity().getIntent();

        payment_iden = intent.getStringExtra(Key.KEY_PAYMENT_IDEN.getValue());
        amount = intent.getStringExtra(Key.KEY_AMOUNT.getValue());
        shop_id = intent.getStringExtra(Key.KEY_SHOP_ID.getValue());

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        mShowPassword.setOnClickListener(this);
        fragment_login_action_button.setOnClickListener(this);

        editTextPaymentPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                balance_password = s.toString();
            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_show_password:
                if (!mbDisplayFlg) {
                    editTextPaymentPwd.setTransformationMethod(HideReturnsTransformationMethod
                            .getInstance());

                    mShowPassword.setBackgroundResource(R.mipmap.btn_show_password_normal);

                    editTextPaymentPwd.setSelection(editTextPaymentPwd.getText().toString().length());

                } else {
                    editTextPaymentPwd.setTransformationMethod(PasswordTransformationMethod
                            .getInstance());
                    mShowPassword.setBackgroundResource(R.mipmap.btn_show_password_disabled);

                    editTextPaymentPwd.setSelection(editTextPaymentPwd.getText().toString().length());
                }

                mbDisplayFlg = !mbDisplayFlg;
                break;
            case R.id.fragment_login_action_button:
                scanCodePaymentSubmit();
                break;
        }
        super.onClick(view);
    }

    private void scanCodePaymentSubmit() {
        CommonRequest request = new CommonRequest(Api.API_USER_SCAN_CODE_PAYMENT, HttpWhat.HTTP_SUBMIT
                .getValue(), RequestMethod.POST);
        request.add("payment_iden", payment_iden);
        request.add("OutLinePayModel[shop_id]", shop_id);
        request.add("OutLinePayModel[amount]", amount);
        request.add("OutLinePayModel[balance_password]", balance_password);
        request.setAjax(true);
        addRequest(request);
    }

    public void OutLinePaySubmit() {

        CommonRequest request = new CommonRequest(Api.API_USER_INTEGRAL_OUT_LINE_PAY, HttpWhat.HTTP_SUBMIT
                .getValue());
        request.add("OutLinePayModel[shop_id]", shop_id);
        request.add("OutLinePayModel[amount]", amount);
        request.add("OutLinePayModel[points]", points);
        request.add("OutLinePayModel[balance_password]", balance_password);
        addRequest(request);
    }

    @Override
    public void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_SUBMIT:
                submitCllback(response);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    private void submitCllback(String response) {
        HttpResultManager.resolve(response, BaseEntity.class, new HttpResultManager.HttpResultCallBack<BaseEntity>() {
            @Override
            public void onSuccess(BaseEntity back) {
                openResult(back.getData());
            }
        });
    }

    void openResult(String data) {
        Intent intent = new Intent();
        intent.setClass(getContext(), BalancePayResultActivity.class);
        intent.putExtra(Key.KEY_URL.getValue(), data);
        startActivity(intent);
        finish();
    }

    protected void finish() {
        getActivity().finish();
    }
}
