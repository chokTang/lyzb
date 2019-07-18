package com.szy.yishopcustomer.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.*;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.szy.common.Fragment.CommonFragment;
import com.szy.common.Interface.NoMatchListener;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Other.WebViewRequest;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.Util.WebViewUrlManager;
import com.szy.yishopcustomer.View.YSCBaseWebView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by 宗仁 on 2016/4/19.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class WebViewActivity extends YSCBaseActivity implements NoMatchListener {
    @BindView(R.id.activity_web_view_toolbar_closeImageButton)
    ImageButton mCloseImageButton;
    @BindView(R.id.activity_web_view_toolbar_backImageButton)
    ImageButton mBackImageButton;
    @BindView(R.id.activity_web_view_yscBaseWebView)
    YSCBaseWebView mWebView;

    private List<String> mHistories;
    private WebViewUrlManager mWebViewUrlUtils;
    private boolean mLoadByWebView;
    private boolean mIsBack;
    private String mUrl;
    private String mTitle;

    private ArrayList<String> mShareData = new ArrayList<>();

    @Override
    public CommonFragment createFragment() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mLayoutId = R.layout.activity_web_view;
        super.onCreate(savedInstanceState);
        String url = getIntent().getStringExtra(Key.KEY_URL.getValue());
        if (Utils.isNull(url)) {
            Utils.makeToast(this, R.string.emptyUrl);
            new android.os.Handler().postDelayed(new Runnable() {
                public void run() {
                    WebViewActivity.this.finish();
                }
            }, 1000);
            return;
        }

        mCloseImageButton.setOnClickListener(this);
        mBackImageButton.setOnClickListener(this);

        mHistories = new ArrayList<>();

        mWebViewUrlUtils = new WebViewUrlManager();
        mWebViewUrlUtils.noMatchListener = this;

        WebViewClient webViewClient = new WebViewClient();
        mWebView.getSettings().setDefaultTextEncodingName("utf-8");
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(webViewClient);
        loadUrl(url);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    public void loadUrl(String url, boolean isBack) {
        WebViewRequest request = new WebViewRequest(url, 10);
        addRequest(request);
        mIsBack = isBack;
    }

    public void loadUrl(String url) {
        loadUrl(url, false);
    }

    public void loadUrlCallback(String response) {
        mLoadByWebView = true;
        mWebView.loadDataWithBaseURL(mUrl, response, "text/html", "UTF-8", null);
        if (!mIsBack) {
            mHistories.add(mUrl);
        }
    }

    @Override
    public boolean noMatches(Context context, String url) {
        try {
            new URL(url);
            loadUrl(url);
        } catch (MalformedURLException e) {
            Utils.copyToClipboard(this, getString(R.string.copyUrl), url);
            Toast.makeText(this, R.string.copyToClipboard, Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_web_view_toolbar_closeImageButton:
                finish();
                break;
            case R.id.activity_web_view_toolbar_backImageButton:
                goBack();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.activity_web_view_share:

                mShareData.add(mUrl);
                mShareData.add("集宝箱");
                mShareData.add(mTitle);
                mShareData.add("http://thyrsi.com/t6/366/1535888373x-1566680337.png");

                Intent mIntent = new Intent(this, ShareActivity.class);
                mIntent.putStringArrayListExtra(ShareActivity.SHARE_DATA, mShareData);
                mIntent.putExtra(ShareActivity.SHARE_SCOPE, 0);
                mIntent.putExtra(ShareActivity.SHARE_SOURCE, ShareActivity.TYPE_GOODS);
                startActivity(mIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onRequestFailed(int what, String response) {
        switch (what) {
            case 10:
                Toast.makeText(this, R.string.requestFailed, Toast.LENGTH_SHORT).show();
                break;
            default:
                super.onRequestFailed(what, response);
                break;
        }
    }

    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (what) {
            case 10:
                loadUrlCallback(response);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_web_view, menu);
        return true;
    }

    private boolean canGoBack() {
        return mHistories.size() > 1;
    }

    private void goBack() {
        if (canGoBack()) {
            mHistories.remove(mHistories.size() - 1);
            loadUrl(mHistories.get(mHistories.size() - 1), true);
        } else {
            finish();
        }
    }

    private class WebViewClient extends android.webkit.WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            mTitle = view.getTitle();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return mWebViewUrlUtils.parseUrl(WebViewActivity.this, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            if (mLoadByWebView) {
                //                setTitle(view.getTitle());
                mTitleView.setText(view.getTitle());
                mLoadByWebView = false;
            } else {
                goBack();
            }

            if (!TextUtils.isEmpty(view.getTitle())) {
                setTitle(view.getTitle());
                mTitle = view.getTitle();
            }

            if (!TextUtils.isEmpty(url)) {
                mUrl = url;
            }
        }
    }
}
