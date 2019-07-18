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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.easeui.EaseConstant;
import com.lyzb.jbx.R;
import com.lyzb.jbx.util.AppPreference;
import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.common.ResponseModel.Common.ResponseCommonModel;
import com.szy.common.View.CommonEditText;
import com.szy.yishopcustomer.Activity.RegisterBonusActivity;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.ResponseModel.AppInfo.ResponseAppInfoModel;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.SharedPreferencesUtils;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.Util.im.IMDoneListener;
import com.szy.yishopcustomer.Util.im.ImHelper;
import com.yolanda.nohttp.RequestMethod;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by liwei on 2017/1/12.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class LoginBindingFragmentStepThree extends YSCBaseFragment implements TextWatcher, TextView
        .OnEditorActionListener {
    private static final String TAG = "RegisterStepThree";
    @BindView(R.id.fragment_register_password_editText)
    CommonEditText mPasswordEditText;
    @BindView(R.id.submit_button)
    Button mFinishButton;
    @BindView(R.id.fragment_register_show_password)
    ImageView mShowPassword;
    @BindView(R.id.warning_layout)
    LinearLayout mRegisterStepThreeTipLayout;
    @BindView(R.id.warning_tip)
    TextView mRegisterStepThreeTip;
    Boolean mFlagShow = false;

    private String mPassword;
    private String mPhoneNumber;
    private ArrayList<String> mBonusArray = new ArrayList<>();


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
                setPassword(mPhoneNumber, mPassword);
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
    protected void onRequestFailed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_POST_NEW_PASSWORD:
                Utils.makeToast(getContext(), "设置密码失败");
                break;
            case HTTP_APP_INFO:
                Utils.makeToast(getContext(), "获取用户信息失败");
                break;
            default:
                super.onRequestFailed(what, response);
                break;
        }
    }

    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_POST_NEW_PASSWORD:
                Utils.hideKeyboard(mFinishButton);
                HttpResultManager.resolve(response, ResponseCommonModel.class, new HttpResultManager.HttpResultCallBack<ResponseCommonModel>() {
                    @Override
                    public void onSuccess(ResponseCommonModel mModel) {
                        mFinishButton.setEnabled(false);
                        Utils.makeToast(getActivity(), mModel.message);

                        EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_BIND_SUCCESS.getValue()));

                        getUserInfo();
                    }
                }, true);
                break;
            //获取用户信息
            case HTTP_APP_INFO:
                refreshCallback(response);
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
            mPhoneNumber = arguments.getString(Key.KEY_PHONE_NUMBER.getValue());
            mBonusArray = arguments.getStringArrayList(Key.KEY_BONUS_LIST.getValue());
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

    public void setPassword(String mPhoneNumber, String password) {
        CommonRequest mGetRegisterRequest = new CommonRequest(Api.API_LOGIN_BIND, HttpWhat.HTTP_POST_NEW_PASSWORD.getValue(), RequestMethod.POST);
        mGetRegisterRequest.add("password", password);
        mGetRegisterRequest.add("MobileBindModel[mobile]", mPhoneNumber);
        addRequest(mGetRegisterRequest);
    }

    private boolean isFinishButtonEnabled() {
        String code = mPasswordEditText.getText().toString();
        return Utils.isPasswordValid(code);
    }

    private void openRegisterBonus() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), RegisterBonusActivity.class);
        intent.putExtra(Key.KEY_BONUS_NUMBER.getValue(), mBonusArray.get(0));
        intent.putExtra(Key.KEY_BONUS_VALUE.getValue(), mBonusArray.get(1));
        intent.putExtra(Key.KEY_BONUS_NAME.getValue(), mBonusArray.get(2));
        intent.putExtra(Key.KEY_BONUS_TYPE.getValue(), 0);
        startActivity(intent);
    }

    private void getUserInfo() {
        CommonRequest request = new CommonRequest(Api.API_APP_INFO, HttpWhat.HTTP_APP_INFO.getValue());
        request.setUserAgent("szyapp/android");
        request.alarm = false;
        addRequest(request);
    }

    private void refreshCallback(String response) {
        HttpResultManager.resolve(response, ResponseAppInfoModel.class, new HttpResultManager.HttpResultCallBack<ResponseAppInfoModel>() {
            @Override
            public void onSuccess(ResponseAppInfoModel back) {
                if (!Utils.isNull(back.data)) {
                    App.getInstance().setImageBaseUrl(back.data.image_url);
                    if (!Utils.isNull(back.data.user_id) && Integer.valueOf(back.data.user_id) > 0) {

                        App.getInstance().setLogin(true);
                        App.getInstance().userId = back.data.user_id;
                        App.getInstance().user_token = back.data.LBS_TOKEN;
                    } else {
                        App.getInstance().setLogin(false);
                    }

                    EaseConstant.CHAT_USER_ID = SharedPreferencesUtils.getParam(getContext(), Key.IM_USERNAME.name(),
                            "").toString();


                    App.getInstance().weixinLogin = back.data.use_weixin_login.contentEquals("1");
                    App.getInstance().weixinLoginLogo = TextUtils.isEmpty(back.data.wx_login_logo) ? "" : (back.data.wx_login_logo + "?" + System.currentTimeMillis());
                    App.getInstance().loginLogo = TextUtils.isEmpty(back.data.login_logo) ? "" : (back.data.login_logo + "?" + System.currentTimeMillis());
                    App.getInstance().loginBackground = TextUtils.isEmpty(back.data.login_bgimg) ? "" : (back.data.login_bgimg + "?" + System.currentTimeMillis());

                    App.getInstance().wangXinAppKey = back.data.aliim_appkey;
                    App.getInstance().wangXinUserId = back.data.aliim_uid;
                    App.getInstance().wangXinPassword = back.data.aliim_pwd;
                    App.getInstance().wangXinServiceId = back.data.aliim_main_customer;
                    App.getInstance().phoneNumber = back.data.mall_phone;
                    //App.getInstance().qq = back.data.qq;
                    App.getInstance().YWEnable = back.data.aliim_enable;
                    App.getInstance().aliim_icon_show = back.data.aliim_icon_show;
                    App.getInstance().aliim_icon = back.data.aliim_icon;
                    App.getInstance().wangXinAvatar = back.data.aliim_customer_logo;
                    App.getInstance().userCenterBgimage = TextUtils.isEmpty(back.data.m_user_center_bgimage) ? "" : (back.data.m_user_center_bgimage + "?" + System.currentTimeMillis());
                    App.getInstance().default_lazyload = back.data.default_lazyload;
                    App.getInstance().aliim_icon = TextUtils.isEmpty(back.data.aliim_icon) ? "" : (back.data.aliim_icon + "?" + System.currentTimeMillis());

                    App.getInstance().default_user_portrait = back.data.default_user_portrait;
                    App.getInstance().default_shop_image = back.data.default_shop_image;
                    App.getInstance().default_micro_shop_image = back.data.default_micro_shop_image;
                    App.getInstance().site_nav_list = back.data.site_nav_list;

                    App.getInstance().site_region_code = back.data.region_code;

                    //处理环信登录
                    App.getInstance().nickName = AppPreference.getIntance().getHxNickName();
                    App.getInstance().headimg = AppPreference.getIntance().getHxHeaderImg();

                    String userName = SharedPreferencesUtils.getParam(App.getInstance().mContext, Key.IM_USERNAME.name(), "").toString();
                    String password = SharedPreferencesUtils.getParam(App.getInstance().mContext, Key.IM_USERPASSWORD.name(), "").toString();
                    if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(password)) {
                        ImHelper.getIntance().onLogin(userName, password, new IMDoneListener() {
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
                    }

                    //跳转到红包页面
                    if (!Utils.isNull(mBonusArray)) {
                        openRegisterBonus();
                    }

                    finish();
                }
            }

            @Override
            public void onFailure(String message) {
                super.onFailure(message);
                showOfflineView();
            }
        });
    }
}
