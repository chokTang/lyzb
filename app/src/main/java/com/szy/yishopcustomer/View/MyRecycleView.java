package com.szy.yishopcustomer.View;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by liuzhifeng on 16/7/23.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 * 能够嵌套scroller中不会滚动的ListView
 */
public class MyRecycleView extends RecyclerView {

    public MyRecycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecycleView(Context context) {
        super(context);
    }

    public MyRecycleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 禁止滚动
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 重写了GridView的onMeasure方法，使其不会出现滚动条
     */
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);

        super.onMeasure(widthMeasureSpec, expandSpec);
    }



}
