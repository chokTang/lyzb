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
 * @role webview 集食惠 h5
 * @time 2018 13:53
 */

public class MallWebviewActivity extends IBaseWebview {

    WebView mWebView;
    ProgressBar mProgressBar;
    View mView;
    Button mButton;

    private String mUrl;

    @Override
    protected void bindLayoutId() {
        setContentView(R.layout.activity_webview_mall);
    }

    @Override
    protected void inittView() {

        mWebView= (WebView) findViewById(R.id.webview_mall);
        mProgressBar= (ProgressBar) findViewById(R.id.progress_mall);
        mView= findViewById(R.id.view_loadhint);
        mButton= (Button) findViewById(R.id.webview_reload_button);

        if (getIntent().getStringExtra(Key.KEY_URL.getValue()) != null) {
            mUrl=getIntent().getStringExtra(Key.KEY_URL.getValue());
            setWebview(this,mWebView,mProgressBar,mView,mButton,mUrl);
        } else {
            Toast.makeText(this, "地址异常,无法访问", Toast.LENGTH_SHORT).show();
        }

    }

}
