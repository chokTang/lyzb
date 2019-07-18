package com.szy.yishopcustomer.View;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Smart on 2017/6/3.
 */

public class MyWebViewClient extends WebViewClient {

    @Override
    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);
    }

    public static final String FLAG_IMAGE = "imagelistner";
    private WebView webView;

    public MyWebViewClient(WebView webView) {
        this.webView = webView;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {

        return super.shouldOverrideUrlLoading(view, url);
    }

    @Override
    public void onPageFinished(WebView view, String url) {

        view.getSettings().setJavaScriptEnabled(true);

        super.onPageFinished(view, url);
        // html加载完成之后，添加监听图片的点击js函数

        addImageClickListner();

    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        view.getSettings().setJavaScriptEnabled(true);

        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

        super.onReceivedError(view, errorCode, description, failingUrl);

    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
    }

    // 注入js函数监听
    private void addImageClickListner() {
        // 这段js函数的功能就是，遍历所有的img几点，并添加onclick函数，在还是执行的时候调用本地接口传递url过去
        webView.loadUrl("javascript:(function(){" +
                "var imgs = ''; var objs = document.getElementsByTagName(\"img\"); " +
                "for(var i = 0; i < objs.length ; i ++)" +
                "{" +
                "imgs += objs[i].src;" +
                "if(i != objs.length-1) {" +
                "imgs += ',';" +
                "}" +
                "}" +

                "for(var i=0;i<objs.length;i++)" +
                "{(function(){var cp=i;" +
                "objs[cp].onclick=function(){" +
                "window."+FLAG_IMAGE+".openImage(imgs,cp);"+
                "};" +
                "})()}" +
                "})()");

    }
}