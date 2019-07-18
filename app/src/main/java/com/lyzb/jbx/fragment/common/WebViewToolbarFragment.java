package com.lyzb.jbx.fragment.common;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.like.longshaolib.base.BaseToolbarFragment;
import com.lyzb.jbx.R;

/**
 * 加载h5显示页面
 * Created by Administrator on 2018/1/1.
 */

public class WebViewToolbarFragment extends BaseToolbarFragment {

    private WebView common_webview;

    //参数
    private final static String PARAMS_TITLE = "PARAMS_TITLE";
    private final static String PARAMS_URL = "PARAMS_URL";
    private String mTitle = "";
    private String mUrl = "";

    public static WebViewToolbarFragment newIntance(String title, String url) {
        WebViewToolbarFragment fragment = new WebViewToolbarFragment();
        Bundle args = new Bundle();
        args.putString(PARAMS_TITLE, title);
        args.putString(PARAMS_URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        super.onInitView(savedInstanceState);
        onBack();
        common_webview = (WebView) findViewById(R.id.common_webview);

        setWebSetting(common_webview);
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args != null) {
            mTitle = args.getString(PARAMS_TITLE);
            mUrl = args.getString(PARAMS_URL);
        }
        setToolbarTitle(mTitle);
        common_webview.loadUrl(mUrl);

        common_webview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return super.shouldInterceptRequest(view, request);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
            }
        });
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_webview_toolbar_layout;
    }

    private void setWebSetting(WebView webView) {
        //声明WebSettings子类
        WebSettings webSettings = webView.getSettings();

        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);

        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        //其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
    }
}
