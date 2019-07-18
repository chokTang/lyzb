package com.szy.yishopcustomer.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.hyphenate.easeui.EaseConstant;
import com.like.utilslib.image.config.GlideApp;
import com.like.utilslib.json.GSONUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.activity.PerfectActivity;
import com.lyzb.jbx.model.account.IsPerfectModel;
import com.lyzb.jbx.util.AppPreference;
import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.common.View.CommonEditText;
import com.szy.common.View.CustomProgressDialog;
import com.szy.yishopcustomer.Activity.FindPasswordActivity;
import com.szy.yishopcustomer.Activity.GoodsActivity;
import com.szy.yishopcustomer.Activity.GoodsListActivity;
import com.szy.yishopcustomer.Activity.GroupBuyListActivity;
import com.szy.yishopcustomer.Activity.LoginActivity;
import com.szy.yishopcustomer.Activity.MessageActivity;
import com.szy.yishopcustomer.Activity.RegisterActivity;
import com.szy.yishopcustomer.Activity.ShopActivity;
import com.szy.yishopcustomer.Activity.YSCWebViewActivity;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.ResponseModel.Login.ResponseLoginModel;
import com.szy.yishopcustomer.ResponseModel.ShowCaptchaModel;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.JSON;
import com.szy.yishopcustomer.Util.RequestAddHead;
import com.szy.yishopcustomer.Util.SharedPreferencesUtils;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.Util.im.IMDoneListener;
import com.szy.yishopcustomer.Util.im.ImHelper;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.CacheMode;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;
import com.yolanda.nohttp.rest.SimpleResponseListener;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

/**
 * Created by zongren on 2016/3/18.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class LoginFragment extends YSCBaseFragment implements TextWatcher {
    private static final String TAG = "LoginFragment";

    LoginActivity parentActivity;
    public static CustomProgressDialog mProgress;
    public int login_type = 0;
    @BindView(R.id.fragment_login_background_relativeLayout)
    RelativeLayout mLoginBackgroundLayout;
    @BindView(R.id.fragment_login_background)
    ImageView mLoginBackground;
    @BindView(R.id.fragment_login_logo)
    ImageView mLoginLogo;
    @BindView(R.id.fragment_login_home_buttion)
    ImageView mLoginHomeButton;
    @BindView(R.id.fragment_login_register_button)
    TextView mLoginRegisterButton;
    @BindView(R.id.fragment_login_register_button2)
    LinearLayout mLoginRegisterButton2;
    @BindView(R.id.fragment_login_action_button)
    Button mLoginButton;
    @BindView(R.id.fragment_login_find_password_button)
    LinearLayout mFindPasswordButton;
    @BindView(R.id.fragment_login_username)
    CommonEditText mUsernameEditText;
    @BindView(R.id.fragment_login_password)
    CommonEditText mPasswordEditText;
    @BindView(R.id.fragment_login_username_phone)
    CommonEditText mUsernameEditTextPhone;
    @BindView(R.id.fragment_login_password_phone)
    CommonEditText mPasswordEditTextPhone;
    @BindView(R.id.fragment_login_relativelayout_password)
    LinearLayout mPasswordLogin;
    @BindView(R.id.fragment_login_relativelayout_phone)
    LinearLayout mPhoneLogin;
    @BindView(R.id.fragment_login_button_one)
    TextView mPasswordLoginButton;
    @BindView(R.id.fragment_login_button_two)
    TextView mPhoneLoginButton;
    @BindView(R.id.send_code)
    TextView mSendButton;
    @BindView(R.id.fragment_show_password)
    ImageView mShowPassword;
    @BindView(R.id.fragment_login_button_weixin)
    ImageView mWeiXin;
    @BindView(R.id.dynamic_password_login_captcha)
    ImageView phoneLoginCaptcha;
    @BindView(R.id.dynamic_password_login_captcha_edittext)
    CommonEditText phoneLoginCaptchaEditText;
    @BindView(R.id.fragment_login_password_captcha_layout)
    View loginPasswordCaptchaLayout;
    @BindView(R.id.fragment_login_password_login_captcha)
    ImageView passwordLoginCaptcha;
    @BindView(R.id.fragment_login_password_captcha_edittext)
    EditText passwordLoginCaptchaEdit;
    @BindView(R.id.captcha_view)
    View captchaView;
    @BindView(R.id.layout_captcha)
    View captchaLayout;

    Boolean mbDisplayFlg = false;
    private TimeCount time;
    private String mType;
    private String mValue;
    private boolean showCaptcha = true;

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        mLoginButton.setEnabled(isLoginButtonEnabled());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //返回
            case R.id.fragment_login_home_buttion:
                getActivity().finish();
                break;
            //跳转到注册页面
            case R.id.fragment_login_register_button:
            case R.id.fragment_login_register_button2:
                startActivity(new Intent().setClass(getActivity(), RegisterActivity.class));
                break;
            //微信登陆
            case R.id.fragment_login_button_weixin:
                if (Utils.isWeixinAvilible(getContext())) {
                    mProgress.show();
                    parentActivity.loginWeiXin();
                } else {
                    Utils.toastUtil.showToast(getActivity(), "请先安装微信客户端");
                }
                break;
            //账户密码登陆
            case R.id.fragment_login_action_button:
                doLogin();
                break;
            //点击普通登陆
            case R.id.fragment_login_button_one:
                login_type = 0;
                mUsernameEditTextPhone.setText("");
                mPasswordEditTextPhone.setText("");
                mPhoneLogin.setVisibility(View.GONE);
                mPasswordLogin.setVisibility(View.VISIBLE);
                mPasswordLoginButton.setSelected(true);
                mPhoneLoginButton.setSelected(false);
                getVerCode();
                break;
            //动态密码登陆
            case R.id.fragment_login_button_two:
                login_type = 1;
                mSendButton.setText("获取动态密码");
                mUsernameEditText.setText("");
                //隐藏密码输入
                captchaView.setVisibility(View.VISIBLE);
                captchaLayout.setVisibility(View.GONE);
                mPasswordLogin.setVisibility(View.GONE);
                mPhoneLogin.setVisibility(View.VISIBLE);
                mPhoneLoginButton.setSelected(true);
                mPasswordLoginButton.setSelected(false);
                Utils.showSoftInputFromWindow(getActivity(), mUsernameEditTextPhone);
                getVerCode();
                break;
            //发送验证码
            case R.id.send_code:
                String mPhoneNumber = mUsernameEditTextPhone.getText().toString();
                if (!Utils.isPhone(mPhoneNumber)) {
                    Utils.makeToast(getActivity(), R.string.pleaseEnterValidPhoneNumber);
                    return;
                }
                sendSmsCaptcha(mPhoneNumber);
                time = new TimeCount(60000, 1000);
                break;
            //找回密码
            case R.id.fragment_login_find_password_button:
                startActivity(new Intent().setClass(getContext(), FindPasswordActivity.class));
                break;
            //显示和隐藏密码
            case R.id.fragment_show_password:
                if (!mbDisplayFlg) {
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
                mbDisplayFlg = !mbDisplayFlg;
                break;
            //更换验证码
            case R.id.fragment_login_password_login_captcha:
            case R.id.dynamic_password_login_captcha:
                getVerCode();
                break;
            default:
                super.onClick(v);
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        parentActivity = (LoginActivity) getActivity();
        mLoginHomeButton.setOnClickListener(this);
        mLoginRegisterButton.setOnClickListener(this);
        mLoginRegisterButton2.setOnClickListener(this);
        mLoginButton.setOnClickListener(this);
        mFindPasswordButton.setOnClickListener(this);
        mUsernameEditText.addTextChangedListener(this);

        mPasswordEditText.addTextChangedListener(this);
        mUsernameEditTextPhone.addTextChangedListener(this);
        mPasswordEditTextPhone.addTextChangedListener(this);
        mPasswordLoginButton.setOnClickListener(this);
        mPhoneLoginButton.setOnClickListener(this);
        mSendButton.setOnClickListener(this);
        mShowPassword.setOnClickListener(this);
        mWeiXin.setOnClickListener(this);
        mPasswordLoginButton.setSelected(true);

        updateBackgroundAndLogo();

        mProgress = new CustomProgressDialog(getActivity());
        mProgress.setCanceledOnTouchOutside(false);
        phoneLoginCaptcha.setOnClickListener(this);
        passwordLoginCaptcha.setOnClickListener(this);
        passwordLoginCaptchaEdit.addTextChangedListener(this);
        return v;
    }

    @Override
    protected void onRequestFailed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_LOGIN:
                Utils.toastUtil.showToast(getActivity(), getResources().getString(R.string
                        .pleaseCheckYourNetwork));
                break;
            case HTTP_IM_USERINFO:
                break;
            default:
                super.onRequestFailed(what, response);
        }
    }

    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_LOGIN:
                cbLogin(response);
                break;
            //请求验证码
            case HTTP_VERIFY_CODE:
                ShowCaptchaModel model = JSON.parseObject(response, ShowCaptchaModel.class);
                if (model.code == 0) {
                    time.start();
                } else if (model.code == -1) {
                    showCaptcha = model.data.show_captcha;
                    if (showCaptcha) {
                        captchaView.setVisibility(View.VISIBLE);
                        captchaLayout.setVisibility(View.VISIBLE);
                    } else {
                        captchaView.setVisibility(View.GONE);
                        captchaLayout.setVisibility(View.GONE);
                    }
                    Utils.makeToast(getActivity(), model.message);
                } else {
                    Utils.makeToast(getActivity(), model.message);
                }
                break;
            case HTTP_USER_INVCODE:
                JSONObject object1 = JSONObject.parseObject(response);
                if (object1.getInteger("code") == 0) {
                    App.getInstance().user_inv_code = JSONObject.parseObject(JSONObject.parseObject(response).getString("data")).getString("invite_code");
                    AppPreference.getIntance().setInviteCode(App.getInstance().user_inv_code);
                }
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_login;
        Bundle arguments = getArguments();
        if (arguments != null) {
            mType = arguments.getString(Key.KEY_TYPE_PUSH.getValue());
            mValue = arguments.getString(Key.KEY_TYPE_PUSH_VALUE.getValue());
        }
    }

    public void openArticle(String articleId) {
        Intent intent = new Intent(getContext(), YSCWebViewActivity.class);
        intent.putExtra(Key.KEY_URL.getValue(), Api.API_ARTICLE + articleId);
        startActivity(intent);
    }

    public void openBrandGoodsList(String brandId) {
        Intent intent = new Intent(getContext(), GoodsListActivity.class);
        intent.putExtra(Key.KEY_GOODS_BRAND_ID.getValue(), brandId);
        startActivity(intent);
    }

    public void openCategory(String catId) {
        Intent intent = new Intent(getContext(), GoodsListActivity.class);
        intent.putExtra(Key.KEY_CATEGORY.getValue(), catId);
        startActivity(intent);
    }

    public void openGroupBuyList(String actId) {
        Intent intent = new Intent(getContext(), GroupBuyListActivity.class);
        intent.putExtra(Key.KEY_GROUP_BUY_ACT_ID.getValue(), actId);
        startActivity(intent);
    }

    public void openWeb(String uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(uri));
        Utils.openActivity(getContext(), intent);
    }


    /**
     * Called when login-in request succeed.
     */
    private void cbLogin(String response) {
        ResponseLoginModel responseLoginMode = JSON.parseObject(response, ResponseLoginModel.class);
        assert responseLoginMode != null;
        if (responseLoginMode.code == 0) {
            App.getInstance().wangXinUserId = responseLoginMode.data.aliim_uid;
            App.getInstance().wangXinAppKey = responseLoginMode.data.aliim_appkey;
            App.getInstance().wangXinPassword = responseLoginMode.data.aliim_pwd;
            App.getInstance().wangXinServiceId = responseLoginMode.data.aliim_main_customer;
            App.getInstance().wangXinAvatar = responseLoginMode.data.aliim_customer_logo;
            App.getInstance().user_token = responseLoginMode.data.LBS_TOKEN;
            App.getInstance().userId = responseLoginMode.data.user_id;

            AppPreference.getIntance().setUserGuid(responseLoginMode.data.user_guid);
            AppPreference.getIntance().setInviteCode(responseLoginMode.data.invite_code);
            SharedPreferencesUtils.setParam(getActivity(), Key.USER_INFO_TOKEN.getValue(), responseLoginMode.data.LBS_TOKEN);
            SharedPreferencesUtils.setParam(getActivity(), Key.USER_INFO_ID.getValue(), responseLoginMode.data.user_id);

            Utils.makeToast(getActivity(), responseLoginMode.message);
            EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_LOGIN.getValue(), responseLoginMode.message));

            /**环信IM 登录*/
            getImInfo();
            isFerfectMessage();
            /**用户code*/
            getUserCode();

            Intent intent = new Intent();
            setResult(Activity.RESULT_OK, intent);
            if (!Utils.isNull(mType) && !Utils.isNull(mValue)) {
                checkType();
            }
            if (time != null) {
                time.cancel();
            }
        } else {
            if (responseLoginMode.data.show_captcha) {
                loginPasswordCaptchaLayout.setVisibility(View.VISIBLE);
            } else {
                loginPasswordCaptchaLayout.setVisibility(View.GONE);
            }
            mLoginButton.setEnabled(isLoginButtonEnabled());
            getVerCode();
            Utils.makeToast(getActivity(), responseLoginMode.message);
        }
    }

    private void checkType() {
        switch (mType) {
            case "0":
                openGoodsActivity(mValue);
                break;
            case "1":
                openShopActivity(mValue);
                break;
            case "2":
                openArticle(mValue);
                break;
            case "3":
                openCategory(mValue);
                break;
            case "4":
                openGroupBuyList(mValue);
                break;
            case "5":
                openBrandGoodsList(mValue);
                break;
            case "6":
                openWeb(mValue);
                break;
            default:
                openMessage();
                break;
        }
    }

    /**
     * 获取用户邀请码
     */
    private void getUserCode() {
        CommonRequest request = new CommonRequest(Api.API_USER_CODE, HttpWhat.HTTP_USER_INVCODE.getValue());
        request.add("user_id", App.getInstance().userId);
        addRequest(request);
    }

    /**
     * 获取IM信息
     */
    private void getImInfo() {
        final RequestQueue requestQueue = NoHttp.newRequestQueue();
        final Request<String> request = NoHttp.createStringRequest(Api.API_CITY_IM_GET_IMINFO_LOGIN, RequestMethod.POST);
        RequestAddHead.addNoHttpHead(request, getActivity(), Api.API_CITY_IM_GET_IMINFO_LOGIN, "POST");
        JSONObject object = new JSONObject();
        object.put("userName", App.getInstance().userId);
        request.setDefineRequestBodyForJson(object.toJSONString());
        requestQueue.add(HttpWhat.HTTP_IM_USERINFO.getValue(), request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                JSONObject object = JSONObject.parseObject(response.get());
                if (Integer.valueOf(object.getString("status")) == 200) {

                    String nickName = object.getString("nickName");
                    String userName = object.getString("userName");
                    String password = object.getString("password");
                    String uuid = object.getString("uuid");
                    String headimg = object.getString("headimg");

                    SharedPreferencesUtils.setParam(App.getInstance().mContext, Key.IM_USERNAME.name(), userName);
                    EaseConstant.CHAT_USER_ID = userName;

                    SharedPreferencesUtils.setParam(App.getInstance().mContext, Key.IM_USERPASSWORD.name(), password);
                    SharedPreferencesUtils.setParam(App.getInstance().mContext, Key.IM_USERUUID.name(), uuid);
                    SharedPreferencesUtils.setParam(App.getInstance().mContext, Key.IM_USERNICK.name(), nickName);
                    SharedPreferencesUtils.setParam(App.getInstance().mContext, Key.IM_USERHEADING.name(), headimg);
                    AppPreference.getIntance().setHxHeaderImg(headimg);
                    AppPreference.getIntance().setHxNickName(nickName);

                    ImHelper.getIntance().onLogin(userName, password, new IMDoneListener() {
                        @Override
                        public void onSuccess() {
                            EventBus.getDefault().post(new CommonEvent(EventWhat.HX_LISTINENER
                                    .getValue()));
                        }

                        @Override
                        public void onFailer(String message) {

                        }
                    });
                } else {

                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }

            @Override
            public void onFinish(int what) {
                if (what == HttpWhat.HTTP_IM_USERINFO.getValue()) {
                    requestQueue.stop();
                }
            }
        });
    }

    /**
     * Called when click login button.
     */
    private void doLogin() {
        if (login_type == 0) {
            String username = mUsernameEditText.getText().toString();
            String password = mPasswordEditText.getText().toString();
            String captcha = passwordLoginCaptchaEdit.getText().toString();
            if (Utils.isNull(username)) {
                Utils.makeToast(getActivity(), R.string.emptyUserName);
                mUsernameEditText.requestFocus();
                InputMethodManager inputMethodManager = (InputMethodManager) mUsernameEditText
                        .getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
                return;
            }
            if (Utils.isNull(password)) {
                Utils.makeToast(getActivity(), R.string.emptyPassword);
                mPasswordEditText.requestFocus();
                InputMethodManager inputMethodManager = (InputMethodManager) mPasswordEditText
                        .getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
                return;
            }
            CommonRequest mLoginRequest = new CommonRequest(Api.API_LOGIN, HttpWhat.HTTP_LOGIN.getValue(), RequestMethod.POST);
            mLoginRequest.add("user_name", username);
            mLoginRequest.add("password", password);
            if (!Utils.isNull(passwordLoginCaptchaEdit.getText().toString())) {
                mLoginRequest.add("verifyCode", captcha);
            }
            addRequest(mLoginRequest);
        } else {
            String username = mUsernameEditTextPhone.getText().toString();
            String code = mPasswordEditTextPhone.getText().toString();
            CommonRequest mLoginRequest = new CommonRequest(Api.API_LOGIN, HttpWhat.HTTP_LOGIN
                    .getValue(), RequestMethod.POST);
            mLoginRequest.add("login_type", login_type);
            mLoginRequest.add("mobile", username);
            mLoginRequest.add("sms-captcha", code);
            addRequest(mLoginRequest);
        }
    }

    /**
     * Check if login enabled.
     *
     * @return isEnabled.
     */
    private boolean isLoginButtonEnabled() {
        boolean init = true;
        if (loginPasswordCaptchaLayout.getVisibility() == View.VISIBLE && Utils.isNull(passwordLoginCaptchaEdit.getText().toString())) {
            init = false;
        }

        return (!Utils.isNull(mUsernameEditText.getText().toString()) && !Utils.isNull
                (mPasswordEditText.getText().toString()) && init) || (!Utils.isNull
                (mUsernameEditTextPhone.getText().toString()) && !Utils.isNull
                (mPasswordEditTextPhone.getText().toString()));
    }

    private void openGoodsActivity(String skuId) {
        Intent intent = new Intent(getContext(), GoodsActivity.class);
        intent.putExtra(Key.KEY_SKU_ID.getValue(), skuId);
        startActivity(intent);
    }

    private void openMessage() {
        Intent intent = new Intent(getContext(), MessageActivity.class);
        startActivity(intent);
    }

    private void openShopActivity(String shopId) {
        Intent intent = new Intent(getContext(), ShopActivity.class);
        intent.putExtra(Key.KEY_SHOP_ID.getValue(), shopId);
        startActivity(intent);
    }

    private void updateBackgroundAndLogo() {
        if (!Utils.isNull(App.getInstance().loginBackground)) {
            GlideApp.with(getActivity())
                    .load(Utils.setImgNetUrl(Api.API_HEAD_IMG_URL, App.getInstance().loginBackground))
                    .error(R.mipmap.bg_login_top)
                    .into(mLoginBackground);
        } else {
            mLoginBackground.setBackgroundResource(R.mipmap.bg_login_top);
        }


        /****
         if (!Utils.isNull(App.getInstance().loginLogo)) {
         String url = Utils.urlOfImage(App.getInstance().loginLogo, false);
         ImageLoader.displayImage(url, mLoginLogo);
         } else {
         mLoginLogo.setImageResource(R.mipmap.ic_login_logo);
         }**/

    }

    /**
     * 发送短信 倒计时
     **/
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            mSendButton.setEnabled(false);
            mSendButton.setText(millisUntilFinished / 1000 + "秒");
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            mSendButton.setText(getResources().getString(R.string.sendVerifyCode));
            mSendButton.setEnabled(true);
        }

    }

    //发送短信验证码
    private void sendSmsCaptcha(String phone) {
        CommonRequest mGetVerifyCodeRequest = new CommonRequest(Api.API_LOGIN_SMS_CAPTCHA, HttpWhat.HTTP_VERIFY_CODE.getValue(), RequestMethod.POST);
        mGetVerifyCodeRequest.add("mobile", phone);
        if (showCaptcha) {
            mGetVerifyCodeRequest.add("captcha", phoneLoginCaptchaEditText.getText().toString());
        }
        addRequest(mGetVerifyCodeRequest);
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
                if (login_type == 0) {
                    passwordLoginCaptcha.setImageBitmap(response.get());
                } else {
                    phoneLoginCaptcha.setImageBitmap(response.get());
                }
            }

            @Override
            public void onFailed(int what, Response<Bitmap> response) {
                super.onFailed(what, response);
            }
        });
    }


    /**
     * 是否完善信息
     */
    public void isFerfectMessage() {
        final RequestQueue requestQueue = NoHttp.newRequestQueue();
        final Request<String> request = NoHttp.createStringRequest(Api.API_LOGIN_IS_PERFECT_MSG, RequestMethod.GET);
        RequestAddHead.addNoHttpHead(request, getActivity(), Api.API_LOGIN_IS_PERFECT_MSG, "GET");
        requestQueue.add(HttpWhat.HTTP_IS_PERFECT.getValue(), request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {

                if (response.responseCode() == 200) {
                    IsPerfectModel model = GSONUtil.getEntity(response.get(), IsPerfectModel.class);
                    if (null != model) {
                        if (!model.isSt()) {
                            startActivity(new Intent(getContext(), PerfectActivity.class));
                        }
                    }
                }

            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }


    @Override
    public void onDestroy() {
        if (time != null) {
            time.cancel();
            time = null;
        }
        super.onDestroy();
    }
}
