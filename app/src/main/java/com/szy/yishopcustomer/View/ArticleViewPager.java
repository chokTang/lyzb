package com.szy.yishopcustomer.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.szy.common.View.VerticalViewPager;
import com.lyzb.jbx.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 宗仁 on 16/8/15.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ArticleViewPager extends VerticalViewPager {
    private static final String TAG = "VerticalBannerView";
    private static final int DEFAULT_DURATION = 3000;
    private static final int MESSAGE_WHAT_SWITCH = 100;

    private int mDuration;
    private boolean mAutoStart;
    private int mPosition;
    private boolean isStarted;
    private Timer mTimer;
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_WHAT_SWITCH:
                    next();
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    };
    private TimerTask mTimerTask = new TimerTask() {
        @Override
        public void run() {
            mHandler.sendEmptyMessage(MESSAGE_WHAT_SWITCH);
        }
    };

    public ArticleViewPager(Context context) {
        this(context, null);
    }

    public ArticleViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ArticleViewPager);
        mDuration = array.getInteger(R.styleable.ArticleViewPager_duration, DEFAULT_DURATION);
        mAutoStart = array.getBoolean(R.styleable.ArticleViewPager_autoStart, true);
        array.recycle();
    }

    public void next() {
        mPosition++;
        if (mPosition >= getAdapter().getCount()) {
            mPosition = 0;
        }
        setCurrentItem(mPosition, true);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }

    public void start() {
        if (getAdapter() == null) {
            return;
        }

        if (!isStarted && getAdapter().getCount() > 1) {
            isStarted = true;
            mTimerTask = new TimerTask() {
                @Override
                public void run() {
                    mHandler.sendEmptyMessage(MESSAGE_WHAT_SWITCH);
                }
            };
            mTimer = new Timer();
            mTimer.schedule(mTimerTask, mDuration, mDuration);
        }
    }

    public void stop() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer.purge();
            mTimer = null;
        }
        isStarted = false;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mAutoStart) {
            start();
        }
    }
}
