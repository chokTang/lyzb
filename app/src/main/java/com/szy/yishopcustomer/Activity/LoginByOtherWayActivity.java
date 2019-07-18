package com.szy.yishopcustomer.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseConstant;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.util.AppPreference;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbAuthListener;
import com.sina.weibo.sdk.auth.WbConnectErrorMessage;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.Config;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Fragment.LoginByOtherWayFragment;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.LoginOther.Model;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.JSON;
import com.szy.yishopcustomer.Util.RequestAddHead;
import com.szy.yishopcustomer.Util.SharedPreferencesUtils;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.Util.im.IMDoneListener;
import com.szy.yishopcustomer.Util.im.ImHelper;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by liwei on 17/1/9.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 * 此页面为 三方应用帐号登录(QQ,微博)
 */
public class LoginByOtherWayActivity extends YSCBaseActivity implements WbAuthListener, LoginByOtherWayFragment.ButtonDownListener {

    private IUiListener mIUiListener;
    private SsoHandler mSsoHandler;
    private IWXAPI api;
    private String loginType;
    private Oauth2AccessToken mAccessToken;
    private LoginByOtherWayFragment loginByOtherWayFragment;

    private String userId;

    @Override
    public void DownListener(String type) {
        loginType = type;
        if (type.equals("qq")) {
            loginQq();
        } else if (type.equals("weixin")) {
            loginWeiXin();
        } else if (type.equals("weibo")) {
            loginWeiBo();
        }
    }

    @Override
    public LoginByOtherWayFragment createFragment() {
        return loginByOtherWayFragment = new LoginByOtherWayFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActionBar.hide();
        overridePendingTransition(R.anim.pop_enter_anim, R.anim.pop_exit_anim);
        Window win = this.getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        win.setAttributes(lp);
        loginByOtherWayFragment.setOnButtonListener(this);

        try {
            mSsoHandler = new SsoHandler(this);
            api = WXAPIFactory.createWXAPI(this, Config.WEIXIN_APP_ID, true);
            api.registerApp(Config.WEIXIN_APP_ID);
        } catch (Exception e) {
        }

//        mAccessToken = AccessTokenKeeper.readAccessToken(this);
//        if (mAccessToken.isSessionValid()) {
//            updateTokenView(true);
//        }
    }


    public void loginQq() {
        try {
            String appId = Config.TENCENT_ID;
            Tencent tencent = Tencent.createInstance(appId, LoginByOtherWayActivity.this);
            mIUiListener = new IUiListener() {
                @Override
                public void onComplete(Object response) {
                    loginByOtherWayFragment.mProgressSwitch(false);
                    String openId = "";
                    String accessToken = "";
                    try {
                        openId = ((JSONObject) response).getString("openid");
                        accessToken = ((JSONObject) response).getString("access_token");
                        refresh(openId, accessToken, Config.TENCENT_ID, "app_qq", "openid");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(UiError uiError) {
                    loginByOtherWayFragment.mProgressSwitch(false);
                    Utils.toastUtil.showToast(LoginByOtherWayActivity.this, "发生错误");
                }

                @Override
                public void onCancel() {
                    loginByOtherWayFragment.mProgressSwitch(false);
                    Utils.toastUtil.showToast(LoginByOtherWayActivity.this, "登录取消");
                }
            };
            tencent.login(LoginByOtherWayActivity.this, "all", mIUiListener);
        } catch (Exception e) {
            toast("暂未配置");
        }
    }

    public void loginWeiBo() {
        try {
            mSsoHandler.authorize(this);
        } catch (Exception e) {
            toast("暂未配置");
        }
    }

    public void loginWeiXin() {
        try {
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            api.sendReq(req);
        } catch (Exception e) {
            toast("暂未配置");
        }
    }

    @Override
    public void onEvent(CommonEvent event) {
        switch (EventWhat.valueOf(event.getWhat())) {
            case EVENT_LOGIN:
                new android.os.Handler().postDelayed(new Runnable() {
                    public void run() {
                        Intent intent = new Intent();
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                    }
                }, 1000);
                break;
            case EVENT_BIND_SUCCESS:
                finish();
                break;
            case EVENT_WEIXIN_SHARE:
                String code = event.getMessage();
                switch (Integer.parseInt(code)) {
                    case BaseResp.ErrCode.ERR_OK:
                        refreshWX();
                        break;
                    case BaseResp.ErrCode.ERR_USER_CANCEL:
                        Utils.toastUtil.showToast(this, "登录取消");
                        break;
                    default:
                        Utils.toastUtil.showToast(this, "发生错误");
                        break;
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int refreshCode, int resultCode, Intent data) {
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(refreshCode, resultCode, data);
        }

        Tencent.onActivityResultData(refreshCode, resultCode, data, mIUiListener);

        super.onActivityResult(refreshCode, resultCode, data);
    }

    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_LOGIN_OTHER:
                loginOtherCallback(response);
                break;
            case HTTP_WEIXIN_GET_ACTION_TOKEN:
                wxCallback(response);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    private void loginOtherCallback(String response) {
        Model model = JSON.parseObject(response, Model.class);
        if (model.code == 0) {
            //Toast.makeText(mContext, "登录成功", Toast.LENGTH_SHORT).show();

            SharedPreferencesUtils.setParam(this, Key.USER_INFO_TOKEN.getValue(), model.user_info.LBS_TOKEN);
            SharedPreferencesUtils.setParam(this, Key.USER_INFO_ID.getValue(), model.user_info.user_id);
            userId = model.user_info.user_id;

            App.getInstance().userId = model.user_info.user_id;
            App.getInstance().user_token = model.user_info.LBS_TOKEN;
            AppPreference.getIntance().setUserGuid(model.user_info.user_guid);
            AppPreference.getIntance().setInviteCode(model.user_info.invite_code);

            /**根据user_id 拿到该用户的环信信息*/
            getImInfo();

            EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_LOGIN.getValue(), model.message));
            finish();
        } else if (model.code == -1) {
            Intent intent = new Intent();
            intent.putExtra(Key.KEY_LOGIN_NAME_KEY.getValue(), model.user_info.name);
            intent.putExtra(Key.KEY_LOGIN_IMAGE_KEY.getValue(), model.user_info.img);
            intent.putExtra(Key.KEY_LOGIN_TYPE_KEY.getValue(), model.user_info.type);
            intent.putExtra(Key.LOGIN_TYPE.getValue(), model.user_info.type);
            intent.setClass(LoginByOtherWayActivity.this, LoginBindingStepOneActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void refresh(String openId, String accessToken, String key, String type, String name) {
        CommonRequest mRequest = new CommonRequest(Api.API_LOGIN_OTHER, HttpWhat.HTTP_LOGIN_OTHER.getValue());
        mRequest.add("access_token", accessToken);
        mRequest.add(name, openId);
        mRequest.add("app_key", key);
        mRequest.add("type", type);
        addRequest(mRequest);
    }

    private void refreshWX() {
        String mCode = App.getInstance().weixin_get_access_token_code;
        CommonRequest mRequest = new CommonRequest(Api.API_GET_WEIXIN,
                HttpWhat.HTTP_WEIXIN_GET_ACTION_TOKEN.getValue());
        mRequest.add("appid", Config.WEIXIN_APP_ID);
        mRequest.add("secret", Config.WEIXIN_APP_SECRET);
        mRequest.add("code", mCode);
        mRequest.add("grant_type", "authorization_code");
        mRequest.add("connect_redirect", "1");
        addRequest(mRequest);
    }

    private void wxCallback(String response) {
        String accessToken = "";
        String openId = "";
        try {
            JSONObject result = new JSONObject(response);
            accessToken = result.getString("access_token");
            openId = result.getString("openid");
            refresh(openId, accessToken, "", "app_weixin", "openid");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getImInfo() {

        RequestQueue requestQueue = NoHttp.newRequestQueue();
        final Request<String> request = NoHttp.createStringRequest(Api.API_CITY_IM_GET_IMINFO, RequestMethod.POST);
        RequestAddHead.addNoHttpHead(request, this, Api.API_CITY_IM_GET_IMINFO, "POST");
        com.alibaba.fastjson.JSONObject object = new com.alibaba.fastjson.JSONObject();
        object.put("userName", userId);

        request.setDefineRequestBodyForJson(object.toJSONString());
        requestQueue.add(HttpWhat.HTTP_IM_USERINFO.getValue(), request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {

                com.alibaba.fastjson.JSONObject object = com.alibaba.fastjson.JSONObject.parseObject(response.get());

                if (Integer.valueOf(object.getString("status")) == 200) {
                    String nickName = object.getString("nickName");
                    final String userName = object.getString("userName");
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

                    ImHelper.getIntance().onLogin(userName, password, null);

                } else {
                    Log.d("wyx", "im信息获取异常-");
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Log.d("wyx", "获取环信信息失败-" + response.get() + "/code:" + what);
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.pop_enter_anim, R.anim.pop_exit_anim);
    }

    @Override
    public void onSuccess(final Oauth2AccessToken token) {

        LoginByOtherWayActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAccessToken = token;
                if (mAccessToken.isSessionValid()) {

                    loginByOtherWayFragment.mProgressSwitch(false);
                    String accessToken = mAccessToken.getToken();
                    String uid = mAccessToken.getUid();
                    refresh(uid, accessToken, "", "app_weibo", "uid");
                    // 保存 Token 到 SharedPreferences
                    AccessTokenKeeper.writeAccessToken(LoginByOtherWayActivity.this, mAccessToken);
//                    Toast.makeText(LoginByOtherWayActivity.this,"授权成功,正在登录", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void cancel() {
        loginByOtherWayFragment.mProgressSwitch(false);
        Utils.toastUtil.showToast(this, "登录取消");
    }

    @Override
    public void onFailure(WbConnectErrorMessage wbConnectErrorMessage) {
        loginByOtherWayFragment.mProgressSwitch(false);
        Utils.toastUtil.showToast(this, "发生错误" + wbConnectErrorMessage);

    }
}
