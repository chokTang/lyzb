package com.szy.yishopcustomer.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.*;
import android.widget.TextView;

import com.lyzb.jbx.R;

/**
 * @author wyx
 * @role 字母列表
 * @time 2018 15:07
 */

public class SideBar extends View {

    private OnTouchingLetterChangedListener onTouchingLetterChangedListener;

    public static String[] mLetters = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};

    private int choose = -1;// 选中
    private Paint paint = new Paint();

    private TextView mTextDialog;

    public void setTextView(TextView textDialog) {
        this.mTextDialog = textDialog;
    }

    public SideBar(Context context) {
        super(context);
    }

    public SideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SideBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 每个字母所占用的高度

        int mViewHeight = getHeight();
        int mViewWidth = getWidth();

        float singleHeight = (float) mViewHeight / mLetters.length;
        // 不断循环把绘制字母
        for (int i = 0; i < mLetters.length; i++) {
            paint.setColor(Color.rgb(33, 65, 98));
            // paint.setColor(Color.WHITE);
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            paint.setAntiAlias(true);
            paint.setTextSize(30);
            if (i == choose) {// 选中的状态
                paint.setColor(Color.parseColor("#FFFFFF"));
                paint.setFakeBoldText(true);
            }
            // x坐标等于中间-字符串宽度的一半
            float xPos = mViewWidth / 2 - paint.measureText(mLetters[i]) / 2;
            float yPos = singleHeight * i + singleHeight;
            canvas.drawText(mLetters[i], xPos, yPos, paint);
            paint.reset();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();
        final int oldChoose = choose;
        final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
        final int c = (int) (y / getHeight() * mLetters.length);// 点击y坐标所占总高度的比例*b数组的长度就等于点击b中的个数

        switch (action) {
            case MotionEvent.ACTION_UP:
                setBackground(new ColorDrawable(0x00000000));
                choose = -1;//
                invalidate();
                if (mTextDialog != null) {
                    mTextDialog.setVisibility(View.INVISIBLE);
                }
                break;

            default:
                setBackgroundResource(R.drawable.sidebar_background);
                if (oldChoose != c) {
                    if (c >= 0 && c < mLetters.length) {
                        if (listener != null) {
                            listener.onTouchingLetterChanged(mLetters[c]);
                        }
                        if (mTextDialog != null) {
                            mTextDialog.setText(mLetters[c]);
                            mTextDialog.setVisibility(View.VISIBLE);
                        }

                        choose = c;
                        invalidate();
                    }
                }

                break;
        }
        return true;
    }

    public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }

    public interface OnTouchingLetterChangedListener {
        void onTouchingLetterChanged(String s);
    }
}
