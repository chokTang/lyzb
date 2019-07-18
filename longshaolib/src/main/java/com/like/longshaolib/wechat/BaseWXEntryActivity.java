package com.like.longshaolib.wechat;

import com.like.longshaolib.net.helper.RequestSubscriber;
import com.like.longshaolib.net.inter.SubscriberOnNextListener;
import com.like.longshaolib.wechat.api.WXLoginApi;
import com.like.utilslib.json.JSONUtil;
import com.like.utilslib.other.LogUtil;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 微信登录
 * Created by Administrator on 2017/8/14.
 */

public abstract class BaseWXEntryActivity extends BaseWXActivity {

    protected abstract void onLoginSuccess(String userInfo);

    protected WXLoginApi wxLoginApi;

    //微信发送请求到第三方应用后的回调
    @Override
    public void onReq(BaseReq baseReq) {

    }

    //第三方应用发送请求到微信后的回调
    @Override
    public void onResp(BaseResp baseResp) {
        final String code = ((SendAuth.Resp) baseResp).code;
        final StringBuilder authUrl = new StringBuilder();
        authUrl
                .append("https://api.weixin.qq.com/sns/oauth2/access_token?appid=")
                .append(LongWeChat.APP_ID)
                .append("&secret=")
                .append(LongWeChat.APP_SECRET)
                .append("&code=")
                .append(code)
                .append("&grant_type=authorization_code");

        LogUtil.loge(authUrl.toString());
        getAuth(authUrl.toString());
    }

    /**
     * 获取用户信息
     * @param authUrl
     */
    private void getAuth(String authUrl) {
        RequestSubscriber<String> requestSubscriber = new RequestSubscriber<String>(this);
        requestSubscriber.bindCallbace(new SubscriberOnNextListener<String>() {
            @Override
            public void onNext(String response) {
                final JSONObject authObj = JSONUtil.toJsonObject(response);
                final String accessToken = JSONUtil.get(authObj, "access_token", "");
                final String openId = JSONUtil.get(authObj, "openid", "");

                final StringBuilder userInfoUrl = new StringBuilder();
                userInfoUrl
                        .append("https://api.weixin.qq.com/sns/userinfo?access_token=")
                        .append(accessToken)
                        .append("&openid=")
                        .append(openId)
                        .append("&lang=")
                        .append("zh_CN");
                LogUtil.loge(userInfoUrl.toString());
                onLoginSuccess(response);//让服务器去获取用户信息
//                getUserInfo(userInfoUrl.toString());
                LogUtil.loge("微信登录成功，开始获取用户信息");
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.loge("微信登录失败" + e.getMessage());
            }
        });
        getWXApi().onWXLogin(authUrl)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribe(requestSubscriber);
    }

    private void getUserInfo(String userInfoUrl) {
        RequestSubscriber<String> requestSubscriber = new RequestSubscriber<String>(this);
        requestSubscriber.bindCallbace(new SubscriberOnNextListener<String>() {
            @Override
            public void onNext(String response) {
                onLoginSuccess(response);
                LogUtil.loge("微信获取用户信息成功:" + response);
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.loge("微信获取用户信息失败" + e.getMessage());
            }
        });
        getWXApi().onGetWXUserInfo(userInfoUrl)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribe(requestSubscriber);
    }

    /**
     * 获取微信登录API
     *
     * @return
     */
    private WXLoginApi getWXApi() {
        if (wxLoginApi == null) {
            OkHttpClient.Builder mHttpBuilder = new OkHttpClient.Builder();
            mHttpBuilder.connectTimeout(5, TimeUnit.SECONDS);//设置超时时间

            Retrofit retrofit = new Retrofit.Builder()
                    .client(mHttpBuilder.build())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            wxLoginApi = retrofit.create(WXLoginApi.class);
        }
        return wxLoginApi;
    }
}
