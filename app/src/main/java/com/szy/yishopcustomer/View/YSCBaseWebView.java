package com.szy.yishopcustomer.View;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.webkit.WebView;

import com.szy.yishopcustomer.Util.Utils;
import com.yolanda.nohttp.NoHttp;

import java.net.HttpCookie;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 宗仁 on 2017/1/7.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class YSCBaseWebView extends WebView {
    public YSCBaseWebView(Context context) {
        super(context);
    }

    public YSCBaseWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public YSCBaseWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public YSCBaseWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public YSCBaseWebView(Context context, AttributeSet attrs, int defStyleAttr, boolean
            privateBrowsing) {
        super(context, attrs, defStyleAttr, privateBrowsing);
    }

    @Override
    public void loadUrl(String url, Map<String, String> additionalHttpHeaders) {
        if (additionalHttpHeaders == null) {
            additionalHttpHeaders = new HashMap<>();
        }


        URI uri = null;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        if (uri != null) {
            // 这里你还可以添加一些自定头。
            additionalHttpHeaders.put("AppVersion", Utils.getVersionName(getContext()));
            // 比如添加app版本信息，当然实际开发中要自动获取哦。

            java.net.CookieStore cookieStore = NoHttp.getCookieManager().getCookieStore();
            List<HttpCookie> cookies = cookieStore.get(uri);

            // 同步到WebView。
            android.webkit.CookieManager webCookieManager = android.webkit.CookieManager
                    .getInstance();
            webCookieManager.setAcceptCookie(true);
            for (HttpCookie cookie : cookies) {
                String cookieUrl = cookie.getDomain();
                String cookieValue = cookie.getName() + "=" + cookie.getValue() + "; path=" +
                        cookie.getPath() + "; domain=" + cookie.getDomain();

                webCookieManager.setCookie(cookieUrl, cookieValue);
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                webCookieManager.flush();
            } else {
                android.webkit.CookieSyncManager.createInstance(NoHttp.getContext()).sync();
            }
        }
        super.loadUrl(url, additionalHttpHeaders);
    }

    @Override
    public void loadUrl(String url) {
        this.loadUrl(url, new HashMap<String, String>());
    }

    @Override
    public void reload() {
        this.loadUrl(getUrl());
    }
}
