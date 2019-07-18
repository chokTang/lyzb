package com.szy.yishopcustomer.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.*;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.lyzb.jbx.R;

/**
 * Created by liuzhifeng on 2016/12/22.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 * 侧滑删除按钮
 */
public class SlidingButtonView extends HorizontalScrollView {

    private TextView mTextView_Delete;
    private TextView mTextView_Mark;

    private int mScrollWidth;

    private IonSlidingButtonListener mIonSlidingButtonListener;

    private Boolean isOpen = false;
    private Boolean once = false;

    public SlidingButtonView(Context context) {
        this(context, null);
    }

    public SlidingButtonView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.setOverScrollMode(OVER_SCROLL_NEVER);
    }

    /**
     * 按滚动条被拖动距离判断关闭或打开菜单
     */
    public void changeScrollx() {
        if (getScrollX() >= (mScrollWidth / 2)) {
            this.smoothScrollTo(mScrollWidth, 0);
            isOpen = true;
            mIonSlidingButtonListener.onMenuIsOpen(this);
        } else {
            this.smoothScrollTo(0, 0);
            isOpen = false;
        }
    }

    /**
     * 关闭菜单
     */
    public void closeMenu() {
        if (!isOpen) {
            return;
        }
        this.smoothScrollTo(0, 0);
        isOpen = false;
    }

    /**
     * 打开菜单
     */
    public void openMenu() {
        if (isOpen) {
            return;
        }
        this.smoothScrollTo(mScrollWidth, 0);
        isOpen = true;
        mIonSlidingButtonListener.onMenuIsOpen(this);
    }

    public void setSlidingButtonListener(IonSlidingButtonListener listener) {
        mIonSlidingButtonListener = listener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (!once) {
            mTextView_Delete = (TextView) findViewById(R.id.tv_delete);
            if(findViewById(R.id.tv_mark)!=null){
                mTextView_Mark = (TextView) findViewById(R.id.tv_mark);
            }
            once = true;
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                mIonSlidingButtonListener.onDownOrMove(this);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                changeScrollx();
                return true;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            this.scrollTo(0, 0);
            //获取水平滚动条可以滑动的范围，即右侧按钮的宽度
            if(findViewById(R.id.tv_mark)!=null&&mTextView_Mark.getVisibility()==View.VISIBLE){
                mScrollWidth = mTextView_Delete.getWidth()+mTextView_Mark.getWidth();
            }else{
                mScrollWidth = mTextView_Delete.getWidth();
            }
        }

    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        mTextView_Delete.setTranslationX(l - mScrollWidth);
    }

    public interface IonSlidingButtonListener {
        void onDownOrMove(SlidingButtonView slidingButtonView);

        void onMenuIsOpen(View view);
    }

}
