package com.szy.yishopcustomer.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.hyphenate.easeui.EaseConstant;
import com.lyzb.jbx.R;
import com.lyzb.jbx.util.AppPreference;
import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.common.View.CommonEditText;
import com.szy.yishopcustomer.Activity.LoginBindingStepThreeActivity;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.ViewType;
import com.szy.yishopcustomer.ResponseModel.AppInfo.ResponseAppInfoModel;
import com.szy.yishopcustomer.ResponseModel.LoginOther.BindingModel;
import com.szy.yishopcustomer.ResponseModel.Register.Model;
import com.szy.yishopcustomer.ResponseModel.Register.RegisterBonusModel;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.SharedPreferencesUtils;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.Util.im.IMDoneListener;
import com.szy.yishopcustomer.Util.im.ImHelper;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.CacheMode;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;
import com.yolanda.nohttp.rest.SimpleResponseListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by liwei on 2017/1/12.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class LoginBindingFragmentStepTwo extends YSCBaseFragment implements TextWatcher {
    @BindView(R.id.fragment_login_binding_verify_code_editText)
    CommonEditText mVerifyCodeEditText;
    @BindView(R.id.submit_button)
    Button mStepFinishButton;
    @BindView(R.id.warning_layout)
    LinearLayout mLoginBindingStepTwoTipLayout;
    @BindView(R.id.warning_tip)
    TextView mLoginBindingStepTwoTip;
    @BindView(R.id.send_code)
    TextView mSendButton;
    @BindView(R.id.fragment_login_binding_desc)
    TextView mDescription;

    @BindView(R.id.captcha_view)
    View captchaView;
    @BindView(R.id.layout_captcha)
    View captchaLayout;
    @BindView(R.id.captcha_edittext)
    EditText captchaEdittext;
    @BindView(R.id.captcha)
    ImageView captcha;

    private String mVerifyCode = "";
    private String phoneNumber;
    private boolean showCaptcha;
    private TimeCount time;
    private RegisterBonusModel registerBonus;
    private ArrayList<String> bonusArray;

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        mStepFinishButton.setEnabled(isFinishButtonEnabled());
        mVerifyCode = mVerifyCodeEditText.getText().toString();

        if (mVerifyCode.length() != 0) {
            mLoginBindingStepTwoTipLayout.setVisibility(View.GONE);
        } else {
            mLoginBindingStepTwoTipLayout.setVisibility(View.VISIBLE);
            mLoginBindingStepTwoTip.setText("短信校验码不能为空");
        }
    }

    public void bindAction() {
        CommonRequest bindRequest = new CommonRequest(Api.API_LOGIN_BIND, HttpWhat
                .HTTP_LOGIN_BIND.getValue(), RequestMethod.POST);
        bindRequest.add("MobileBindModel[mobile]", phoneNumber);
        bindRequest.add("MobileBindModel[sms_captcha]", mVerifyCode);
        addRequest(bindRequest);
    }

    private void openSetPawwordActivity() {
        Intent intent = new Intent();
        intent.putExtra(Key.KEY_PHONE_NUMBER.getValue(), phoneNumber);
        intent.putStringArrayListExtra(Key.KEY_BONUS_LIST.getValue(), bonusArray);
        intent.setClass(getActivity(), LoginBindingStepThreeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        ViewType viewType = Utils.getViewTypeOfTag(v);
        int position = Utils.getPositionOfTag(v);
        int extraInfo = Utils.getExtraInfoOfTag(v);
        switch (viewType) {
            case VIEW_TYPE_SEND_CODE:
                if (showCaptcha && Utils.isNull(captchaEdittext.getText().toString())) {
                    Toast.makeText(getActivity(), "图片验证码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                sendVerifyCode();
                break;
            case VIEW_TYPE_FINISH_BUTTON:
                bindAction();
                break;
            default:
                super.onClick(v);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mDescription.setText(String.format(getResources().getString(R.string
                .pleaseEnterVerifyCode), phoneNumber));

        mVerifyCodeEditText.addTextChangedListener(this);

        Utils.setViewTypeForTag(mSendButton, ViewType.VIEW_TYPE_SEND_CODE);
        mSendButton.setOnClickListener(this);

        if (showCaptcha) {
            captchaView.setVisibility(View.VISIBLE);
            captchaLayout.setVisibility(View.VISIBLE);
            getVerCode();
        } else {
            captchaView.setVisibility(View.GONE);
            captchaLayout.setVisibility(View.GONE);
        }

        time = new TimeCount(60000, 1000);

        //进来默认发送验证码
        //sendVerifyCode();
        return view;
    }

    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_VERIFY_CODE:
                BindingModel model = JSON.parseObject(response, BindingModel.class);
                if (model.code == 0) {
                    Utils.makeToast(getActivity(), model.message);
                    if ("1".equals(model.is_new)) {
                        mStepFinishButton.setText("下一步");
                    } else {
                        mStepFinishButton.setText("完成");
                    }

                    Utils.setViewTypeForTag(mStepFinishButton, ViewType.VIEW_TYPE_FINISH_BUTTON);
                    mStepFinishButton.setOnClickListener(this);
                } else {
                    Utils.makeToast(getActivity(), model.message);
                }
                break;
            case HTTP_LOGIN_BIND:
                Model registerModel = JSON.parseObject(response, Model.class);
                if (time != null) {
                    time.cancel();
                }

                if (registerModel.code == 0) {
                    //账号已注册未绑定返回的data==null，账号未注册返回data!=null
                    if ("1".equals(registerModel.set_password)) {
                        if (!Utils.isNull(registerModel.data)) {
                            registerBonus = registerModel.data.bonus_info;
                            bonusArray = new ArrayList<String>();
                            if (!Utils.isNull(registerBonus)) {
                                bonusArray.add(registerModel.data.bonus_info.bonus_amount_format);
                                bonusArray.add(registerModel.data.bonus_info.bonus_number);
                                bonusArray.add(registerModel.data.bonus_info.bonus_name);
                            }
                        }

                        EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_LOGIN.getValue()));
                        openSetPawwordActivity();
                        finish();

                    } else {
                        Utils.makeToast(getActivity(), registerModel.message);
                        EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_LOGIN.getValue()));
                        EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_BIND_SUCCESS.getValue()));

                        //如果注册过 直接去获取用户信息
                        getUserInfo();
                    }
                } else {
                    Utils.makeToast(getActivity(), registerModel.message);
                }
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
        mLayoutId = R.layout.fragment_login_binding_step_two;

        Intent intent = getActivity().getIntent();
        phoneNumber = intent.getStringExtra(Key.KEY_PHONE_NUMBER.getValue());
        showCaptcha = intent.getBooleanExtra(Key.KEY_BOOLEAN.getValue(), false);
    }

    private boolean isFinishButtonEnabled() {
        String code = mVerifyCodeEditText.getText().toString();
        return !Utils.isNull(code);
    }

    private void sendVerifyCode() {
        CommonRequest mGetVerifyCodeRequest = new CommonRequest(Api.API_LOGIN_BIND_SMS_CAPTCHA,
                HttpWhat.HTTP_VERIFY_CODE.getValue(), RequestMethod.POST);
        //mGetVerifyCodeRequest.setAjax(true);
        mGetVerifyCodeRequest.add("mobile", phoneNumber);
        if (showCaptcha) {
            mGetVerifyCodeRequest.add("captcha", captchaEdittext.getText().toString());
        }
        addRequest(mGetVerifyCodeRequest);
        time.start();
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            mSendButton.setClickable(false);
            mSendButton.setText(millisUntilFinished / 1000 + "秒");
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            mSendButton.setText(getResources().getString(R.string.sendVerifyCode));
            mSendButton.setClickable(true);
        }
    }

    @Override
    public void onDestroy() {
        if (time != null) {
            time.cancel();
            time = null;
        }
        super.onDestroy();
    }

    //刷新并获取图形验证码图片
    private String refreshCaptcha() {
        return Api.API_REFRESH_CAPTCHA;
    }

    //获取图形验证码图片
    private String getCaptcha() {
        return Api.API_CAPTCHA;
    }

    private void getVerCode() {
        Request<Bitmap> imageRequest = NoHttp.createImageRequest(refreshCaptcha());//这里 RequestMethod.GET可以不写（删除掉即
        imageRequest.setCacheMode(CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE);
        imageRequest.setUserAgent("szyapp/android");

        this.mRequestQueue.add(HttpWhat.HTTP_CAPTCHA.getValue(), imageRequest, new SimpleResponseListener<Bitmap>() {
            @Override//请求成功的回调
            public void onSucceed(int i, Response<Bitmap> response) {
                captcha.setImageBitmap(response.get());
            }

            @Override
            public void onFailed(int what, Response<Bitmap> response) {
                super.onFailed(what, response);
            }
        });
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
