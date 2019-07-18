package com.szy.yishopcustomer.Activity;

import android.view.*;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.szy.yishopcustomer.Constant.Key;
import com.lyzb.jbx.R;

/**
 * @author wyx
 * @role 同城生活 activity
 * @time 2018 10:46
 */

public class CityLifeActivity extends IBaseWebview {

    WebView mWebView;
    ProgressBar mProgressBar;
    View mView_LoadHint;
    Button mButton_Reload;

    private String mUrl;

    @Override
    protected void bindLayoutId() {
        setContentView(R.layout.activity_citylife);
    }

    @Override
    protected void inittView() {

        mWebView = (WebView) findViewById(R.id.webview_citylife);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_citylife);
        mView_LoadHint = findViewById(R.id.view_loadhint);
        mButton_Reload = (Button) findViewById(R.id.webview_reload_button);

        if (getIntent().getStringExtra(Key.KEY_URL.getValue()) != null) {
            mUrl=getIntent().getStringExtra(Key.KEY_URL.getValue());
            setWebview(this,mWebView,mProgressBar,mWebView,mButton_Reload,mUrl);
        } else {
            Toast.makeText(this, "地址异常,无法访问", Toast.LENGTH_SHORT).show();
        }
    }
}
