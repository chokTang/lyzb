package com.szy.yishopcustomer.View;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

/**
 * Created by Smart on 2016/2/25.
 */
public class MyFoucesRelativeLayout extends RelativeLayout {

    public MyFoucesRelativeLayout(Context context) {
        super(context);
    }
    public MyFoucesRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public MyFoucesRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        Rect r = new Rect();
        getWindowVisibleDisplayFrame(r);

        int totalHeight = getRootView().getHeight();
        int nowHeight = r.bottom - r.top;

        if (totalHeight - nowHeight > totalHeight / 4) {
//            super.onMeasure(widthSpec, MeasureSpec.makeMeasureSpec(totalHeight, MeasureSpec.EXACTLY));
            super.onMeasure(widthSpec, heightSpec);
            //键盘出现
        } else {
            super.onMeasure(widthSpec, heightSpec);
            //键盘消失

            //获取焦点
            onFocusdisappear();
        }
    }

    private void onFocusdisappear(){
        setFocusable(true);
        setFocusableInTouchMode(true);
        requestFocus();
    }

}
