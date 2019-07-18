package com.szy.yishopcustomer.View;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by Smart on 2017/12/20.
 */

public class ScrollWebView extends WebView {

    public ScrollWebView(Context context) {
        this(context, null);
    }

    public ScrollWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, mExpandSpec);
    }
}