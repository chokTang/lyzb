package com.lyzb.jbx.mvp.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.like.longshaolib.base.inter.IRequestListener;
import com.like.longshaolib.net.model.HttpResult;
import com.like.utilslib.json.JSONUtil;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.me.IAccountCorrelationView;
import com.szy.yishopcustomer.Constant.Config;
import com.szy.yishopcustomer.Util.App;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

/**
 * @author wyx
 * @role
 * @time 2019 2019/5/16 10:34
 */

public class AccountCorrelationPresenter extends APPresenter<IAccountCorrelationView> {
    /**
     * 检查帐号是否绑定
     */
    public void checkBinDing(final String user_id) {

        onRequestDataNoMap(true, new IRequestListener<HttpResult<String>>() {
            @Override
            public Observable onCreateObservable() {
                Map<String, Object> map = new HashMap<>();
                map.put("user_id", user_id);
                return phpCommonApi.checkBinDing(map);
            }

            @Override
            public void onSuccess(HttpResult<String> httpResult) {
                if (httpResult == null || httpResult.getCode() != 0) {
                    getView().onFail("");
                    return;
                }
                getView().onCheckBinDingSuccess(httpResult);
            }

            @Override
            public void onFail(String message) {
                getView().onFail(message);
            }
        });
    }

    /**
     * 获取openid
     */
    public void getOpenId() {
        onRequestDataNoMap(true, new IRequestListener<Object>() {
            @Override
            public Observable onCreateObservable() {
                String mCode = App.getInstance().weixin_get_access_token_code;
                return wxapi.getOpenId(Config.WEIXIN_APP_ID, Config.WEIXIN_APP_SECRET, mCode, "authorization_code");
            }

            @Override
            public void onSuccess(Object o) {
                String s = o.toString();
                String access_token = JSONUtil.get(JSONUtil.toJsonObject(s), "access_token", "");
                if (TextUtils.isEmpty(access_token)) {
                    getView().onFail("获取授权失败");
                    return;
                }
                String openid = JSONUtil.get(JSONUtil.toJsonObject(s), "openid", "");
                binDingWX(access_token, openid);
            }

            @Override
            public void onFail(String message) {
                getView().onFail(message);
            }
        });
    }

    /**
     * 绑定微信
     */
    public void binDingWX(final String access_token, final String openid) {
        onRequestDataHaveCommon(true, new IRequestListener<Object>() {
            @Override
            public Observable onCreateObservable() {
                Map<String, Object> map = new HashMap<>();
                map.put("access_token", access_token);
                map.put("openid", openid);
                map.put("type", "app_weixin");
                map.put("user_id", App.getInstance().userId);
                map.put("connect_redirect", "1");
                return phpCommonApi.binDingWX(map);
            }

            @Override
            public void onSuccess(Object o) {
                getView().onBinDingSuccess();
            }

            @Override
            public void onFail(String message) {
                getView().onFail(message);
            }
        });
    }

    /**
     * 解除绑定
     */
    public void removeBinDing(final String user_id) {
        onRequestDataHaveCommon(true, new IRequestListener<Boolean>() {
            @Override
            public Observable onCreateObservable() {
                Map<String, Object> map = new HashMap<>();
                map.put("user_id", user_id);
                return phpCommonApi.removeBinDing(map);
            }

            @Override
            public void onSuccess(Boolean b) {
                if (b) {
                    getView().onRemoveBinDingSuccess();
                } else {
                    getView().onFail("");
                }

            }

            @Override
            public void onFail(String message) {
                getView().onFail(message);
            }
        });
    }
}
