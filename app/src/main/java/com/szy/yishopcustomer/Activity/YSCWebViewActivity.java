package com.szy.yishopcustomer.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.lyzb.jbx.R;
import com.szy.common.Fragment.CommonFragment;
import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.ResponseModel.Share.WebShareModel;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.Util.WebViewUrlManager;
import com.szy.yishopcustomer.View.YSCBaseWebView;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by 宗仁 on 2017/1/7.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 * 商之翼 原有H5
 */

public class YSCWebViewActivity extends YSCBaseActivity {
    @BindView(R.id.activity_web_view_toolbar_closeImageButton)
    ImageButton mCloseImageButton;
    @BindView(R.id.activity_web_view_toolbar_backImageButton)
    ImageButton mBackImageButton;
    @BindView(R.id.activity_web_view_yscBaseWebView)
    YSCBaseWebView mWebView;
    @BindView(R.id.progressBar)
    ProgressBar pg1;

    private WebViewUrlManager mWebViewUrlUtils;

    private ArrayList<String> mShareData = new ArrayList<>();

    /**
     * 默认分享的图片 网络地址
     */
    private String shareImgUrl = "https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/system/ic_launcher.png";

    /**
     * url 拦截的boolean  true 继续szy以前的业务逻辑  false webview 继续加载,不拦截
     * 默认是拦截
     */
    private boolean interCept = true;

    @Override
    public CommonFragment createFragment() {
        return null;
    }

    private String mUrl;
    private String mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mLayoutId = R.layout.activity_web_view;
        super.onCreate(savedInstanceState);
        mUrl = getIntent().getStringExtra(Key.KEY_URL.getValue());

        if (mUrl.contains("game/selectGameList")) {
            if (App.getInstance().isLogin()) {
                mUrl = mUrl + "?userId=" + App.getInstance().userId;
            }
        }

        if (Utils.isNull(mUrl)) {
            Utils.makeToast(this, R.string.emptyUrl);
            new android.os.Handler().postDelayed(new Runnable() {
                public void run() {
                    YSCWebViewActivity.this.finish();
                }
            }, 1000);
            return;
        }

        mCloseImageButton.setOnClickListener(this);
        mBackImageButton.setOnClickListener(this);

        mWebViewUrlUtils = new WebViewUrlManager();
        mWebViewUrlUtils.noMatchListener = null;

        WebViewClient webViewClient = new WebViewClient();
        mWebView.getSettings().setDefaultTextEncodingName("utf-8");
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(webViewClient);
        mWebView.loadUrl(mUrl);

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    pg1.setVisibility(View.GONE);
                } else {
                    pg1.setVisibility(View.VISIBLE);
                    pg1.setProgress(newProgress);
                }
            }
        });
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_web_view_toolbar_closeImageButton:
                if (App.getInstance().ads_h5) {
//                    startActivity(new Intent(this, RootActivity.class));
                    App.getInstance().ads_h5 = false;
                    finish();
                } else {
                    finish();
                }
                break;
            case R.id.activity_web_view_toolbar_backImageButton:
                goBack();
                break;
        }
    }

    @Override
    protected void onRequestSucceed(int what, String response) {
        super.onRequestSucceed(what, response);
        switch (HttpWhat.valueOf(what)) {
            case HTTP_H5_SHARE_DATA:

                WebShareModel model = JSON.parseObject(response, WebShareModel.class);
                if (model.code == 0) {

                    mShareData.add(model.data.seo_topic_link);
                    mShareData.add(model.data.seo_topic_title);
                    mShareData.add(model.data.seo_topic_discription);
                    mShareData.add(model.data.seo_topic_image);

                    Intent mIntent = new Intent(this, ShareActivity.class);
                    mIntent.putStringArrayListExtra(ShareActivity.SHARE_DATA, mShareData);
                    mIntent.putExtra(ShareActivity.SHARE_SCOPE, 0);
                    mIntent.putExtra(ShareActivity.SHARE_SOURCE, ShareActivity.TYPE_GOODS);
                    startActivity(mIntent);
                } else {

                    mShareData.add(mUrl);
                    mShareData.add("集宝箱");
                    mShareData.add(mTitle);
                    mShareData.add(shareImgUrl);

                    Intent mIntent = new Intent(this, ShareActivity.class);
                    mIntent.putStringArrayListExtra(ShareActivity.SHARE_DATA, mShareData);
                    mIntent.putExtra(ShareActivity.SHARE_SCOPE, 0);
                    mIntent.putExtra(ShareActivity.SHARE_SOURCE, ShareActivity.TYPE_GOODS);
                    startActivity(mIntent);
                }
                break;
        }
    }

    @Override
    protected void onRequestFailed(int what, String response) {
        super.onRequestFailed(what, response);
        switch (HttpWhat.valueOf(what)) {
            case HTTP_H5_SHARE_DATA:

                mShareData.add(mUrl);
                mShareData.add("集宝箱");
                mShareData.add(mTitle);
                mShareData.add(shareImgUrl);

                Intent mIntent = new Intent(this, ShareActivity.class);
                mIntent.putStringArrayListExtra(ShareActivity.SHARE_DATA, mShareData);
                mIntent.putExtra(ShareActivity.SHARE_SCOPE, 0);
                mIntent.putExtra(ShareActivity.SHARE_SOURCE, ShareActivity.TYPE_GOODS);
                startActivity(mIntent);

                break;
        }
    }

    @Override
    public void onEvent(CommonEvent event) {
        switch (EventWhat.valueOf(event.getWhat())) {
            case EVENT_LOGIN:
                mWebView.reload();
                break;
            default:
                super.onEvent(event);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.activity_web_view_share:
                getShareData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getShareData() {

        CommonRequest adRequest = new CommonRequest(Api.API_H5_SHARE, HttpWhat.HTTP_H5_SHARE_DATA.getValue());

        if (!mUrl.contains(".html")) {
            mUrl = mUrl + ".html";
        }
        adRequest.add("share_url", mUrl);
        addRequest(adRequest);
    }

    @Override
    public void showOfflineView() {
        super.showOfflineView();
        mWebView.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_web_view, menu);
        return true;
    }

    private void goBack() {
        if (mWebView != null) {
            mWebView.post(new Runnable() {
                @Override
                public void run() {
                    mWebView.loadUrl("javascript:socketCloseApp()");
                }
            });
        }
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            if (App.getInstance().ads_h5) {
//                startActivity(new Intent(this, RootActivity.class));
                App.getInstance().ads_h5 = false;
                finish();
            } else {
                finish();
            }
        }
    }

    private class WebViewClient extends android.webkit.WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            WebView.HitTestResult hit = view.getHitTestResult();

            int hitType;
            if (!Utils.isNull(hit)) {
                hitType = hit.getType();
            } else {
                hitType = WebView.HitTestResult.UNKNOWN_TYPE;
            }

//            if (hitType == WebView.HitTestResult.SRC_ANCHOR_TYPE)//点击超链接
            if (hitType != WebView.HitTestResult.UNKNOWN_TYPE) {
                //返回false代表webView本身处理链接，所以打开普通连接的时候，应该返回false，这样才会有Referer
                //因为网页有可能是通过Referer判断的上一个页面，如果没有Referer可能导致一直302无限循环

                if (Utils.verCityLifeUrl(url)) {
                    Intent intent = new Intent(YSCWebViewActivity.this, ProjectH5Activity.class);
                    intent.putExtra(Key.KEY_URL.getValue(), url);
                    startActivity(intent);
                    //true 当前页面不继续加载
                    return true;
                } else {
                    if (interCept) {
                        if (!TextUtils.isEmpty(url) && url.contains("mkt.jbxgo.com")) {
                            return false;
                        }
                        /***默认 拦截*/
                        if (mWebViewUrlUtils.parseUrl(YSCWebViewActivity.this, url)) {
                            return true;
                        } else {
                            return false;
                        }

                    } else {
                        /***继续加载url 不做任何跳转*/
                        return false;
                    }
                }
            } else {
                return false;
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {

            if (!TextUtils.isEmpty(view.getTitle())) {
                setTitle(view.getTitle());
                mTitle = view.getTitle();
            }

            if (!TextUtils.isEmpty(url)) {
                mUrl = url;
            }

            if (url.contains("isurl_intercept=1")) {
                interCept = false;
            }
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String
                failingUrl) {
            Toast.makeText(YSCWebViewActivity.this, description, Toast.LENGTH_SHORT).show();
            setTitle(getString(R.string.requestFailed));
        }

        @Override
        public void onReceivedSslError(WebView view,
                                       SslErrorHandler handler, SslError error) {
            handler.proceed();
        }
    }


//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        url = getIntent().getStringExtra(Key.KEY_URL.getValue());
//        mWebView.loadUrl(url);
//    }
}
