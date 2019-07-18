package com.lyzb.jbx.util;

import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by :TYK
 * Date: 2019/7/8  14:31
 * Desc:
 */
public class WebViewUtil {

    public static void webviewSet(WebView webView){
        WebSettings webSettings =webView.getSettings();
        webSettings.setLoadWithOverviewMode(true);
        webView.setVerticalScrollBarEnabled(false);//不能垂直滑动
        webView.setHorizontalScrollBarEnabled(false);//不能水平滑动
//        webSettings.setTextSize(WebSettings.TextSize.NORMAL);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);//支持通过JS打开新窗口
        webSettings.setJavaScriptEnabled(true);//支持通过JS打开新窗口

    }
}
