package com.szy.yishopcustomer.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szy.common.Other.CommonRequest;
import com.szy.yishopcustomer.Activity.AccountSecurityStepActivity;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.View.PayPasswordView;
import com.szy.yishopcustomer.ViewModel.SecurityModel;

import butterknife.BindView;

/**
 * 账户安全
 * A simple {@link Fragment} subclass.
 */
public class AccountSecurityFragment extends YSCBaseFragment {

    @BindView(R.id.textViewMessage)
    TextView textViewMessage;

    @BindView(R.id.imageViewPwd)
    ImageView imageViewPwd;
    @BindView(R.id.textViewPwd)
    TextView textViewPwd;

    @BindView(R.id.imageViewEmail)
    ImageView imageViewEmail;
    @BindView(R.id.textViewEmail)
    TextView textViewEmail;

    @BindView(R.id.imageViewMobile)
    ImageView imageViewMobile;
    @BindView(R.id.textViewMobile)
    TextView textViewMobile;

    @BindView(R.id.textViewPayPwd)
    TextView textViewPayPwd;
    @BindView(R.id.imageViewPayPwd)
    ImageView imageViewPayPwd;

    @BindView(R.id.linearlayoutPwd)
    LinearLayout linearlayoutPwd;
    @BindView(R.id.linearlayoutEmail)
    LinearLayout linearlayoutEmail;
    @BindView(R.id.linearlayoutMobile)
    LinearLayout linearlayoutMobile;
    @BindView(R.id.linearlayoutPayPwd)
    LinearLayout linearlayoutPayPwd;

    private SecurityModel mModel;

    public AccountSecurityFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mLayoutId = R.layout.fragment_account_security;
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        refresh();

        linearlayoutPwd.setOnClickListener(this);
        linearlayoutEmail.setOnClickListener(this);
        linearlayoutMobile.setOnClickListener(this);
//        linearlayoutPayPwd.setOnClickListener(this);

        return v;
    }

    public void refresh() {
        CommonRequest request = new CommonRequest(Api.API_USER_SECURITY, HttpWhat.HTTP_SIMPLE
                .getValue());
        addRequest(request);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.linearlayoutPwd:
                openAccountSecurityStepActivity(AccountSecurityStepActivity.TYPE_STEP_PWD);
                break;
            case R.id.linearlayoutEmail:
                openAccountSecurityStepActivity(AccountSecurityStepActivity.TYPE_STEP_EMAIL);
                break;
            case R.id.linearlayoutMobile:
                openAccountSecurityStepActivity(AccountSecurityStepActivity.TYPE_STEP_MOBILE);
                break;
            case R.id.linearlayoutPayPwd:
                openAccountSecurityStepActivity(AccountSecurityStepActivity.TYPE_STEP_PAYPWD);
                break;
            default:
                super.onClick(view);
                break;
        }
    }

    void openAccountSecurityStepActivity(int type){
        Intent intent = new Intent(getContext(), AccountSecurityStepActivity.class);
        intent.putExtra(AccountSecurityStepActivity.TYPE_STEP,type);
        startActivity(intent);
    }

    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_SIMPLE:
                HttpResultManager.resolve(response, SecurityModel.class, new HttpResultManager
                        .HttpResultCallBack<SecurityModel>() {
                    @Override
                    public void onSuccess(SecurityModel back) {
                        mModel = back;
                        setUpAdapterData();
                    }
                },true);
                break;
            default:
                break;
        }
    }

    PayPasswordView payPasswordView = null;
    void setUpAdapterData() {
        if (mModel.data.safe_info.safe_grade >= 4) {
            textViewMessage.setVisibility(View.GONE);
        } else {
            textViewMessage.setVisibility(View.VISIBLE);
        }

        if (TextUtils.isEmpty(mModel.data.info.password)) {
            imageViewPwd.setImageResource(R.mipmap.ic_safety_verification);
            textViewPwd.setText("尚未设置登录密码");
        } else {
            imageViewPwd.setImageResource(R.mipmap.ic_safety_code);
            textViewPwd.setText("已设置登录密码");
        }

        if (1 != mModel.data.info.email_validated) {
            imageViewEmail.setImageResource(R.mipmap.ic_safety_verification);
            textViewEmail.setText("绑定后，可用于账号登录，快速找回登录密码、支付密码，接收账户余额变动提醒等。");
        } else {
            imageViewEmail.setImageResource(R.mipmap.ic_safety_code);
            textViewEmail.setText(Html.fromHtml("您绑定的邮箱： <font color=\"#f23030\">" + mModel.data.info.email + "</font>，该邮箱可用于账号登录，快速找回登录密码、支付密码，接收账户余额变动提醒等。"));
        }

        if (1 != mModel.data.info.mobile_validated) {
            imageViewMobile.setImageResource(R.mipmap.ic_safety_verification);
            textViewMobile.setText("绑定后，可用于账号登录，快速找回登录密码、支付密码，接收账户余额变动提醒等。");
        } else {
            imageViewMobile.setImageResource(R.mipmap.ic_safety_code);
            textViewMobile.setText(Html.fromHtml("您绑定的手机： <font color=\"#f23030\">" + mModel.data.info.mobile + "</font>，该手机可用于账号登录，快速找回登录密码、支付密码，接收账户余额变动提醒等。 "));
        }

        if (TextUtils.isEmpty(mModel.data.info.surplus_password)) {
            imageViewPayPwd.setImageResource(R.mipmap.ic_safety_verification);
            textViewPayPwd.setText("启用支付密码后，可保障您账户余额的支付安全,在使用账户资产时，需通过支付密码进行支付认证。");
            linearlayoutPayPwd.setOnClickListener(this);
        } else {
            imageViewPayPwd.setImageResource(R.mipmap.ic_safety_code);
            textViewPayPwd.setText("启用支付密码后，可保障您账户余额的支付安全,在使用账户资产时，需通过支付密码进行支付认证。");
            linearlayoutPayPwd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(payPasswordView == null) {
                        payPasswordView = new PayPasswordView(getContext(),payListener);
                    }
                    payPasswordView.show();
                }
            });
        }
    }

    View.OnClickListener payListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button_close_paypwd:
                    openAccountSecurityStepActivity(AccountSecurityStepActivity.TYPE_STEP_PAYPWD_CLOSE);
                    break;
                case R.id.button_modify_paypwd:
                    openAccountSecurityStepActivity(AccountSecurityStepActivity.TYPE_STEP_PAYPWD);
                    break;
                case R.id.textView_forget_paypwd:
                    openAccountSecurityStepActivity(AccountSecurityStepActivity.TYPE_STEP_PAYPWD);
                    break;
            }
            payPasswordView.dismiss();
        }
    };

}
