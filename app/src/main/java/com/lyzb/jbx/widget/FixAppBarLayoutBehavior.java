package com.lyzb.jbx.widget;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.OverScroller;

import java.lang.reflect.Field;

/**
 * 作者：yyx on 2019/5/22 10:04
 * <p>
 * 类描述：
 */
public class FixAppBarLayoutBehavior extends AppBarLayout.Behavior
{

    //https://www.jianshu.com/p/2924f32e8c22
    private OverScroller mScroller1;

    public FixAppBarLayoutBehavior()
    {
        super();
    }

    public FixAppBarLayoutBehavior(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        bindScrollerValue(context);
    }

    /**
     * 反射注入Scroller以获取其引用
     *
     * @param context
     */
    private void bindScrollerValue(Context context)
    {
        if (mScroller1 != null) return;
        mScroller1 = new OverScroller(context);
        try
        {
            Class<?> clzHeaderBehavior = getClass().getSuperclass().getSuperclass();
            Field fieldScroller = clzHeaderBehavior.getDeclaredField("mScroller");
            fieldScroller.setAccessible(true);
            fieldScroller.set(this, mScroller1);
        } catch (Exception e)
        {
        }
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, int dx, int dy, int[] consumed, int type)
    {
        if (type == ViewCompat.TYPE_NON_TOUCH)
        {
            //fling上滑appbar然后迅速fling下滑list时, HeaderBehavior的mScroller并未停止, 会导致上下来回晃动
            if (mScroller1.computeScrollOffset())
            {
                mScroller1.abortAnimation();
            }
            //当target滚动到边界时主动停止target fling,与下一次滑动产生冲突
            if (getTopAndBottomOffset() == 0)
            {
                ViewCompat.stopNestedScroll(target, type);
            }
        }
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
    }
}
