package com.lyzb.jbx.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Created by :TYK
 * Date: 2019/7/8  17:21
 * Desc:
 */
public class NoClickRetalation extends RelativeLayout {
    public NoClickRetalation(Context context) {
        super(context);
    }

    public NoClickRetalation(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoClickRetalation(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
