package com.szy.yishopcustomer.Util;

import android.content.Context;
import android.os.Build;
import android.view.*;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * @author wyx
 * @role webview
 * @time 2018 15:08
 */

public class WebviewUtils {


    public Context mContext;
    public WebView mWebView;
    public WebSettings webSettings = null;

    public WebviewUtils(Context context, WebView webView) {
        this.mContext = context;
        this.mWebView = webView;
    }

    public void webviewSet() {

        /**获取webview设置**/
        webSettings = mWebView.getSettings();
        /**适配**/
        webSettings.setUseWideViewPort(true);
        /**允许访问文件 **/
        webSettings.setAllowFileAccess(true);
        webSettings.setLoadWithOverviewMode(true);

        webSettings.setLoadWithOverviewMode(true);
        /**设置 webview agent**/
        webSettings.setUserAgentString(mWebView.getSettings().getUserAgentString() + "jbxmall");
        /**支持js**/
        webSettings.setJavaScriptEnabled(true);
        /**设置缓存模式**/
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        /**开启 DOM缓存功能**/
        webSettings.setDomStorageEnabled(true);
        /**关闭 Application Caches 缓存功能**/
        webSettings.setAppCacheEnabled(false);


        webSettings.setBuiltInZoomControls(false);
        /**http https地址图片连接**/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }


//        /***
//         * 5.0以上允许加载http和https混合的页面(5.0以下默认允许，5.0+默认禁止)
//         */
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            mWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
//        }

    }

    /***
     * webview 加载图片控制 true 加载
     * @param isLoadImage
     */
    public void setWebViewImage(boolean isLoadImage) {
        if (isLoadImage) {
            webSettings.setBlockNetworkImage(false);
        } else {
            webSettings.setBlockNetworkImage(true);
        }
    }

}
