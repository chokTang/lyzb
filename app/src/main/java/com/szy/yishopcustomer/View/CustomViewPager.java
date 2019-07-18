package com.szy.yishopcustomer.View;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.*;

/**
 * Created by Smart on 2017/5/26.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class CustomViewPager extends ViewPager {
    private int height = 0;
    private int widthMeasureSpec;
    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.widthMeasureSpec = widthMeasureSpec;

        for (int i = 0; i < getChildCount(); i++) {
            if(i < 1) {
                View child = getChildAt(i);
                child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                int h = child.getMeasuredHeight();
                if (h > height)
                    height = h;
            }
        }

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void resetHeight(){
        height = 0;
        for (int i = 0; i < getChildCount(); i++) {
            if(i < 1) {
                View child = getChildAt(i);
                child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                int h = child.getMeasuredHeight();
                if (h > height)
                    height = h;
            }
        }

        int heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

        this.getLayoutParams().height = heightMeasureSpec;
    }
}
