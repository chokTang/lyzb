package com.szy.yishopcustomer.View;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.*;


/**
 * 顶部ViewPager
 * 自动滚动以及无限循环滚动
 * Created by Smart on 2016/6/6.
 */
public class HeadWrapContentViewPager extends HeadViewPager {

    private int maxHeight = 0;
    private int maxWidth = 0;

    public HeadWrapContentViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 获取屏幕信息
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        maxWidth = metrics.widthPixels;
    }

    public void setMaxWidth(int maxWidth){
        this.maxWidth = maxWidth;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure( MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            int w = child.getMeasuredWidth();

            if(h != 0 && w != 0) {
                int realH = maxWidth * h / w;
                if(realH > maxHeight) {
                    maxHeight = realH;
                }
            }
        }

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(maxHeight, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * Determines the height of this view
     *
     * @param measureSpec A measureSpec packed into an int
     * @param view        the base view with already measured height
     * @return The height of the view, honoring constraints from measureSpec
     */
    private int measureHeight(int measureSpec, View view) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            // set the height from the base view if available
            if (view != null) {
                result = view.getMeasuredHeight();
            }
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }
}