package com.szy.yishopcustomer.core;

import android.app.Activity;

import com.alibaba.fastjson.JSONObject;
import com.alipay.sdk.app.PayTask;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Smart on 2017/12/12.
 */
public class PayUtils {

    Activity activity;
    Authorize authorize;

    public PayUtils(Activity activity) {
        this.activity = activity;
        authorize = new Authorize(activity);
    }

    public void AliPay(final String orderInfo, final OnPayCallback listener) {
        authorize.isAuthorize(new Authorize.OnAuthorizeState() {
            @Override
            public void onSuccess() {
                PayTask alipay = new PayTask(activity);
                String result = alipay.pay(orderInfo, true);
                listener.aliCallback(result);
            }

            @Override
            public void onError() {
            }

            @Override
            public void onFinished() {

            }
        });
    }

    public String getAliPayVersion() {
        PayTask alipay = new PayTask(activity);
        return alipay.getVersion();
    }

    public void AliPayV2(final JSONObject jsonObject, final OnPayCallback listener) {

        authorize.isAuthorize(new Authorize.OnAuthorizeState() {
            @Override
            public void onSuccess() {
                String orderParam = jsonObject.getString("orderInfo");

                PayTask alipay = new PayTask(activity);
                Map<String, String> result = alipay.payV2(orderParam, true);
                listener.aliCallback(result);
            }

            @Override
            public void onError() {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    public interface OnPayCallback {
        void aliCallback(Object object);
    }
}
