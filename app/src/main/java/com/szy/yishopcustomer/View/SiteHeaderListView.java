package com.szy.yishopcustomer.View;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.*;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by liuzhifeng on 2016/8/25.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class SiteHeaderListView extends ListView {

    private View mHeaderView;
    private int mMeasuredWidth;
    private int mMeasuredHeight;
    private boolean mDrawFlag = true;
    private SiteHeaderAdapter mSiteHeaderAdapter;

    public SiteHeaderListView(Context context) {
        super(context);
    }

    public SiteHeaderListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SiteHeaderListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * HeaderView三种状态的具体处理
     *
     * @param position
     */
    public void controlPinnedHeader(int position) {
        if (null == mHeaderView) {
            return;
        }

        int pinnedHeaderState = mSiteHeaderAdapter.getPinnedHeaderState(position);
        switch (pinnedHeaderState) {
            case SiteHeaderAdapter.PINNED_HEADER_GONE:
                mDrawFlag = false;
                break;

            case SiteHeaderAdapter.PINNED_HEADER_VISIBLE:
                mSiteHeaderAdapter.configurePinnedHeader(mHeaderView, position, 0);
                mDrawFlag = true;
                mHeaderView.layout(0, 0, mMeasuredWidth, mMeasuredHeight);
                break;

            case SiteHeaderAdapter.PINNED_HEADER_PUSHED_UP:
                mSiteHeaderAdapter.configurePinnedHeader(mHeaderView, position, 0);
                mDrawFlag = true;

                // 移动位置
                View topItem = getChildAt(0);

                if (null != topItem) {
                    int bottom = topItem.getBottom();
                    int height = mHeaderView.getHeight();

                    int y;
                    if (bottom < height) {
                        y = bottom - height;
                    } else {
                        y = 0;
                    }

                    if (mHeaderView.getTop() != y) {
                        mHeaderView.layout(0, y, mMeasuredWidth, mMeasuredHeight + y);
                    }

                }
                break;
        }

    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);

        mSiteHeaderAdapter = (SiteHeaderAdapter) adapter;
    }

    // 三个覆写方法负责在当前窗口显示inflate创建的Header View
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (null != mHeaderView) {
            measureChild(mHeaderView, widthMeasureSpec, heightMeasureSpec);
            mMeasuredWidth = mHeaderView.getMeasuredWidth();
            mMeasuredHeight = mHeaderView.getMeasuredHeight();
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        if (null != mHeaderView && mDrawFlag) {
            drawChild(canvas, mHeaderView, getDrawingTime());
        }
    }

    /**
     * 设置置顶的Header View
     *
     * @param pHeader
     */
    public void setPinnedHeader(View pHeader) {
        mHeaderView = pHeader;

        requestLayout();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if (null != mHeaderView) {
            mHeaderView.layout(0, 0, mMeasuredWidth, mMeasuredHeight);
            controlPinnedHeader(getFirstVisiblePosition());
        }
    }

    public interface SiteHeaderAdapter {

        int PINNED_HEADER_GONE = 0;

        int PINNED_HEADER_VISIBLE = 1;

        int PINNED_HEADER_PUSHED_UP = 2;

        void configurePinnedHeader(View headerView, int position, int alpaha);

        int getPinnedHeaderState(int position);
    }

}
