package com.szy.yishopcustomer.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.yishopcustomer.Activity.RegisterBonusActivity;
import com.szy.yishopcustomer.Activity.RegisterSuccessActivity;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.Config;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Login.ResponseLoginModel;
import com.szy.yishopcustomer.ResponseModel.Register.Model;
import com.szy.yishopcustomer.ResponseModel.Register.RegisterBonusModel;
import com.szy.yishopcustomer.ResponseModel.Register.RegisterIntegModel;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.JSON;
import com.szy.yishopcustomer.Util.SharedPreferencesUtils;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.Util.im.IMDoneListener;
import com.szy.yishopcustomer.Util.im.ImHelper;
import com.yolanda.nohttp.RequestMethod;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

//import com.szy.common.View.CustomProgressDialog;

/**
 * Created by liwei on 2016/4/19.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class RegisterStepThreeFragment extends YSCBaseFragment implements TextWatcher, TextView
        .OnEditorActionListener {
    private static final String TAG = "RegisterStepThree";
    @BindView(R.id.fragment_register_password_editText)
    EditText mPasswordEditText;
    @BindView(R.id.submit_button)
    Button mFinishButton;
    @BindView(R.id.fragment_register_show_password)
    ImageView mShowPassword;
    @BindView(R.id.warning_layout)
    LinearLayout mRegisterStepThreeTipLayout;
    @BindView(R.id.warning_tip)
    TextView mRegisterStepThreeTip;
    Boolean mFlagShow = false;
    private String mVerifyCode;
    private String mPassword;
    private String mPhoneNumber;
    private FinishThreeStep mFinishThreeStep;
    private String format;
    private String number;
    private String name;
    private RegisterBonusModel registerBonus;

    private RegisterIntegModel mRegisterIntegModel;
    //    private CustomProgressDialog mProgress;

    public static RegisterStepThreeFragment newInstance(String phoneNumber, String verifyCode) {
        RegisterStepThreeFragment fragment = new RegisterStepThreeFragment();
        Bundle arguments = new Bundle();
        arguments.putString(Key.KEY_PHONE_NUMBER.getValue(), phoneNumber);
        arguments.putString(Key.KEY_VERIFY_CODE.getValue(), verifyCode);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        mFinishButton.setEnabled(isFinishButtonEnabled());
        mPassword = mPasswordEditText.getText().toString();
        if (mPassword.length() != 0) {
            if (isFinishButtonEnabled()) {
                if (Utils.isIncludeSpace(mPassword)) {
                    mRegisterStepThreeTipLayout.setVisibility(View.VISIBLE);
                    mRegisterStepThreeTip.setText(getResources().getString(R.string
                            .registerSetPasswordTip));
                } else {
                    mRegisterStepThreeTipLayout.setVisibility(View.GONE);
                }
            } else {
                mRegisterStepThreeTipLayout.setVisibility(View.VISIBLE);
                mRegisterStepThreeTip.setText(getResources().getString(R.string
                        .registerSetPasswordTip));
            }
        } else {
            mRegisterStepThreeTipLayout.setVisibility(View.VISIBLE);
            mRegisterStepThreeTip.setText("密码不能为空");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_button:
                // mProgress.show();
                registerStepThree(mPhoneNumber, mPassword, mVerifyCode);
                break;
            case R.id.fragment_register_show_password:
                if (!mFlagShow) {
                    mPasswordEditText.setTransformationMethod(HideReturnsTransformationMethod
                            .getInstance());
                    mShowPassword.setBackgroundResource(R.mipmap.btn_show_password_normal);
                    mPasswordEditText.setSelection(mPasswordEditText.getText().toString().length());
                } else {
                    mPasswordEditText.setTransformationMethod(PasswordTransformationMethod
                            .getInstance());
                    mShowPassword.setBackgroundResource(R.mipmap.btn_show_password_disabled);
                    mPasswordEditText.setSelection(mPasswordEditText.getText().toString().length());
                }
                mFlagShow = !mFlagShow;
                break;
            default:
                super.onClick(v);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);

        mFinishButton.setOnClickListener(this);
        mPasswordEditText.addTextChangedListener(this);
        mPasswordEditText.setOnEditorActionListener(this);
        mShowPassword.setOnClickListener(this);
        mFinishButton.setText(getResources().getString(R.string.finish));
        Utils.showSoftInputFromWindow(getActivity(), mPasswordEditText);

        return v;
    }

    @Override
    protected void onRequestSucceed(int what, final String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_REGISTER_INFO:
                Utils.hideKeyboard(mFinishButton);
                HttpResultManager.resolve(response, Model.class, new HttpResultManager.HttpResultCallBack<Model>() {
                    @Override
                    public void onSuccess(Model mModel) {

                        if (!Utils.isNull(mModel.data.bonus_info)) {
                            registerBonus = mModel.data.bonus_info;
                            format = mModel.data.bonus_info.bonus_amount_format;
                            number = mModel.data.bonus_info.bonus_number;
                            name = mModel.data.bonus_info.bonus_name;
                        }
                        if (!Utils.isNull(mModel.data.integral_info)) {
                            mRegisterIntegModel = mModel.data.integral_info;

                            App.getInstance().user_ingot_number = mRegisterIntegModel.give_integral;

                            /**注册完成 跳转到 注册成功页面**/
                            Intent intent = new Intent(getActivity(), RegisterSuccessActivity.class);
                            intent.putExtra("", true);
                            intent.putExtra("ingot_number", mRegisterIntegModel.give_integral);
                            intent.putExtra("ingot_total", mRegisterIntegModel.integral_num);
                            startActivity(intent);

                            mFinishButton.setEnabled(false);
                            Utils.makeToast(getActivity(), mModel.message);
                        }
                        login();
                    }
                });
                break;
            case HTTP_LOGIN:
                loginCallback(response);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_register_step_three;

        Bundle arguments = getArguments();
        if (arguments != null) {
            mVerifyCode = arguments.getString(Key.KEY_VERIFY_CODE.getValue());
            mPhoneNumber = arguments.getString(Key.KEY_PHONE_NUMBER.getValue());
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        switch (v.getId()) {
            case R.id.fragment_register_password_editText:
                if (isFinishButtonEnabled()) {
                    mFinishButton.performClick();
                }
                return false;
            default:
                return false;
        }
    }

    public void registerStepThree(String phone, String password, String code) {
        CommonRequest mGetRegisterRequest = new CommonRequest(Api.API_REGISTER_FINISH_MOBILE,
                HttpWhat.HTTP_REGISTER_INFO.getValue(), RequestMethod.POST);
        mGetRegisterRequest.add("MobileRegisterModel[mobile]", phone);
        mGetRegisterRequest.add("MobileRegisterModel[password]", password);
        mGetRegisterRequest.add("MobileRegisterModel[sms_captcha]", code);
        addRequest(mGetRegisterRequest);
    }

    public void setFinishThreeStep(FinishThreeStep finishThreeStep) {
        mFinishThreeStep = finishThreeStep;
    }

    private boolean isFinishButtonEnabled() {
        String code = mPasswordEditText.getText().toString();
        return Utils.isPasswordValid(code);
    }

    private void login() {
        CommonRequest mLoginRequest = new CommonRequest(Config.BASE_URL + "/site/login", HttpWhat
                .HTTP_LOGIN.getValue(), RequestMethod.POST);
        mLoginRequest.add("user_name", mPhoneNumber);
        mLoginRequest.add("password", mPassword);
        addRequest(mLoginRequest);
    }

    private void loginCallback(String response) {
        ResponseLoginModel responseLoginMode = JSON.parseObject(response, ResponseLoginModel.class);
        assert responseLoginMode != null;
        if (responseLoginMode.code == 0) {
            App.getInstance().wangXinUserId = responseLoginMode.data.aliim_uid;
            App.getInstance().wangXinAppKey = responseLoginMode.data.aliim_appkey;
            App.getInstance().wangXinPassword = responseLoginMode.data.aliim_pwd;
            App.getInstance().wangXinServiceId = responseLoginMode.data.aliim_main_customer;
            App.getInstance().userId=responseLoginMode.data.user_id;
            App.getInstance().user_token=responseLoginMode.data.LBS_TOKEN;
            App.getInstance().headimg=responseLoginMode.data.image;
            App.getInstance().nickName=responseLoginMode.data.nick_name;
            //这里没有去调用获取IM信息，直接是通过拼凑的
            App.getInstance().userName="jbx"+responseLoginMode.data.user_id;
            ImHelper.getIntance().onLogin(  App.getInstance().userName, mPassword, new IMDoneListener() {
                @Override
                public void onSuccess() {
                    //注册监听
                    EventBus.getDefault().post(new CommonEvent(EventWhat.HX_LISTINENER
                            .getValue()));
                }

                @Override
                public void onFailer(String message) {

                }
            });

            SharedPreferencesUtils.setParam(getActivity(), Key.USER_INFO_TOKEN.getValue(), responseLoginMode.data.LBS_TOKEN);
            SharedPreferencesUtils.setParam(getActivity(), Key.USER_INFO_ID.getValue(), responseLoginMode.data.user_id);

        }
        if (!Utils.isNull(registerBonus) && !Utils.isNull(registerBonus.bonus_number)) {
            openRegisterBonus();
        } else {
            EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_LOGIN.getValue()));
            EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_CLOSE.getValue()));
        }
    }

    private void openRegisterBonus() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), RegisterBonusActivity.class);
        intent.putExtra(Key.KEY_BONUS_NUMBER.getValue(), number);
        intent.putExtra(Key.KEY_BONUS_VALUE.getValue(), format);
        intent.putExtra(Key.KEY_BONUS_NAME.getValue(), name);
        intent.putExtra(Key.KEY_BONUS_TYPE.getValue(), 0);
        startActivity(intent);
    }

    public interface FinishThreeStep {
        void onFinishThreeStep(String code);
    }
}
