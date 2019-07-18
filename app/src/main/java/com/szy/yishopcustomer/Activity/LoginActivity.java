package com.szy.yishopcustomer.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.hyphenate.easeui.EaseConstant;
import com.like.utilslib.json.GSONUtil;
import com.like.utilslib.other.LogUtil;
import com.lyzb.jbx.activity.PerfectActivity;
import com.lyzb.jbx.model.account.IsPerfectModel;
import com.lyzb.jbx.util.AppPreference;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbAuthListener;
import com.sina.weibo.sdk.auth.WbConnectErrorMessage;
import com.szy.common.Fragment.CommonFragment;
import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.Config;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Fragment.LoginByWeixinFragment;
import com.szy.yishopcustomer.Fragment.LoginFragment;
import com.szy.yishopcustomer.ResponseModel.LoginOther.Model;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.HttpResultManager;
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
 * Created by zongren on 2016/3/18.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 * 此页面为 微信帐号登录页面
 * 三方应用帐号登录为:(LoginByOtherWayActivity)
 */
public class LoginActivity extends YSCBaseActivity implements WbAuthListener {


    //登录成功后，是否重启上一个activity
    private String activityName = "";

    private IWXAPI api;

    private String userId;


    @Override
    public CommonFragment createFragment() {
        return new LoginFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityName = getIntent().getStringExtra("activityName");
        mActionBar.hide();
        App.getInstance().disableWeixinLoginTemporary = false;

        api = WXAPIFactory.createWXAPI(this, Config.WEIXIN_APP_ID, true);
        api.registerApp(Config.WEIXIN_APP_ID);
    }

    public void loginWeiXin() {
        //api注册
        if (api == null) {
            //这一步在onCreate做
            api = WXAPIFactory.createWXAPI(this, Config.WEIXIN_APP_ID, true);
            api.registerApp(Config.WEIXIN_APP_ID);
        }

        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        api.sendReq(req);
    }

    /**
     * @param event
     */
    @Override
    public void onEvent(CommonEvent event) {
        switch (EventWhat.valueOf(event.getWhat())) {
            case EVENT_LOGIN:
                new android.os.Handler().postDelayed(new Runnable() {
                    public void run() {
                        Intent intent = new Intent();
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                        if (!TextUtils.isEmpty(activityName)) {
                            startActivity(getIntent().setClassName(LoginActivity.this, activityName));
                        }

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
                        if (!Utils.isNull(LoginByWeixinFragment.mProgress)) {
                            LoginByWeixinFragment.mProgress.dismiss();
                        }
                        if (!Utils.isNull(LoginFragment.mProgress)) {
                            LoginFragment.mProgress.dismiss();
                        }
                        refreshWX();
                        break;
                    case BaseResp.ErrCode.ERR_USER_CANCEL:
                        if (!Utils.isNull(LoginByWeixinFragment.mProgress)) {
                            LoginByWeixinFragment.mProgress.dismiss();
                        }
                        if (!Utils.isNull(LoginFragment.mProgress)) {
                            LoginFragment.mProgress.dismiss();
                        }
                        Utils.toastUtil.showToast(this, "登录取消");
                        break;
                    default:
                        if (!Utils.isNull(LoginByWeixinFragment.mProgress)) {
                            LoginByWeixinFragment.mProgress.dismiss();
                        }
                        if (!Utils.isNull(LoginFragment.mProgress)) {
                            LoginFragment.mProgress.dismiss();
                        }
                        Utils.toastUtil.showToast(this, "发生错误");
                        break;
                }
                break;
                default:
                    break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_LOGIN_CANCEL.getValue
                        ()));
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
            case HTTP_USER_INVCODE:
                com.alibaba.fastjson.JSONObject object1 = com.alibaba.fastjson.JSONObject.parseObject(response);
                if (object1.getInteger("code") == 0) {
                    //获取用户的code 权限
                    App.getInstance().user_inv_code = com.alibaba.fastjson.JSONObject.parseObject
                            (com.alibaba.fastjson.JSONObject.parseObject(response).getString("data")).getString("invite_code");
                    AppPreference.getIntance().setInviteCode(App.getInstance().user_inv_code);
                }
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    @Override
    protected void onRequestFailed(int what, String response) {
        super.onRequestFailed(what, response);
        switch (HttpWhat.valueOf(what)) {
            //调用微信地址报错
            case HTTP_WEIXIN_GET_ACTION_TOKEN:
                LogUtil.loge("微信调用地址报错");
                break;
        }
    }

    /**
     * 三方登陆成功 数据
     */
    private void loginOtherCallback(String response) {
        HttpResultManager.resolve(response, Model.class, new HttpResultManager.HttpResultCallBack<Model>() {
            Model model;

            @Override
            public void getObj(Model back) {
                model = back;
            }

            @Override
            public void onSuccess(Model back) {
                Toast.makeText(getBaseContext(), "登录成功", Toast.LENGTH_SHORT).show();

                SharedPreferencesUtils.setParam(LoginActivity.this, Key.USER_INFO_TOKEN.getValue(), model.user_info.LBS_TOKEN);
                SharedPreferencesUtils.setParam(LoginActivity.this, Key.USER_INFO_ID.getValue(), model.user_info.user_id);

                App.getInstance().userId = model.user_info.user_id;
                App.getInstance().user_token = model.user_info.LBS_TOKEN;
                AppPreference.getIntance().setUserGuid(model.user_info.user_guid);
                AppPreference.getIntance().setInviteCode(model.user_info.invite_code);

                userId = model.user_info.user_id;

                /**环信 登录 */
                getImInfo();
                isFerfectMessage();
                /**用户code */
                getUserCode();

                EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_LOGIN.getValue(), model.message));
                finish();
            }

            @Override
            public void onFailure(String message) {
                Intent intent = new Intent();
                intent.putExtra(Key.LOGIN_TYPE.getValue(), model.user_info.type);
                intent.setClass(LoginActivity.this, LoginBindingStepOneActivity.class);
                startActivity(intent);
            }
        }, true);
    }

    public void getImInfo() {
        final RequestQueue requestQueue = NoHttp.newRequestQueue();
        final Request<String> request = NoHttp.createStringRequest(Api.API_CITY_IM_GET_IMINFO_LOGIN, RequestMethod.POST);
        RequestAddHead.addNoHttpHead(request, this, Api.API_CITY_IM_GET_IMINFO_LOGIN, "POST");
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
                } else {
                    Log.d("wyx", "im信息获取异常-");
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Log.d("wyx", "im信息获取异常-");
            }

            @Override
            public void onFinish(int what) {
                if (what == HttpWhat.HTTP_IM_USERINFO.getValue()) {
                    requestQueue.stop();
                }
            }
        });

    }

    public void getUserCode() {
        CommonRequest request = new CommonRequest(Api.API_USER_CODE, HttpWhat.HTTP_USER_INVCODE.getValue());
        request.add("user_id", App.getInstance().userId);
        addRequest(request);
    }


    /**
     * 是否完善信息
     */
    public void isFerfectMessage() {
        final RequestQueue requestQueue = NoHttp.newRequestQueue();
        final Request<String> request = NoHttp.createStringRequest(Api.API_LOGIN_IS_PERFECT_MSG, RequestMethod.GET);
        RequestAddHead.addNoHttpHead(request, this, Api.API_LOGIN_IS_PERFECT_MSG, "GET");
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
                            startActivity(new Intent(LoginActivity.this, PerfectActivity.class));
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

    /**
     * 三方登录 登录到API.API_LOGIN_OTHER 接口
     */
    private void refresh(String openId, String accessToken, String key, String type, String name) {
        CommonRequest mRequest = new CommonRequest(Api.API_LOGIN_OTHER, HttpWhat.HTTP_LOGIN_OTHER.getValue());
        mRequest.add("access_token", accessToken);
        mRequest.add(name, openId);
        mRequest.add("app_key", key);
        mRequest.add("type", type);
        addRequest(mRequest);
    }

    /**
     * 获取 微信登录 用户信息
     */
    private void refreshWX() {
        String mCode = App.getInstance().weixin_get_access_token_code;
        CommonRequest mRequest = new CommonRequest(Api.API_GET_WEIXIN, HttpWhat.HTTP_WEIXIN_GET_ACTION_TOKEN.getValue());
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

    /**
     * 微博 登录 回调
     */
    private Oauth2AccessToken mAccessToken;

    @Override
    public void onSuccess(final Oauth2AccessToken token) {
        Log.e("onSuccess", JSON.toJSONString(token));
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LoginFragment.mProgress.dismiss();
                mAccessToken = token;
                if (mAccessToken.isSessionValid()) {
                    String accessToken = token.getToken();
                    String uid = token.getUid();
                    refresh(uid, accessToken, "", "app_weibo", "uid");
                }
            }
        });
    }


    @Override
    public void cancel() {
        LoginFragment.mProgress.dismiss();
        Utils.toastUtil.showToast(this, "登录取消");
    }

    @Override
    public void onFailure(WbConnectErrorMessage errorMessage) {
        LoginFragment.mProgress.dismiss();
        Toast.makeText(this, errorMessage.getErrorMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LoginFragment.mProgress = null;
    }
}

