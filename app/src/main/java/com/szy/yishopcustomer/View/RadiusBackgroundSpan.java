package com.szy.yishopcustomer.View;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.style.ReplacementSpan;

/**
 * Created by Smart on 2017/7/6.
 */

public class RadiusBackgroundSpan extends ReplacementSpan {

    private int mSize;
    private int mColor;
    private int mRadius;
    private int mTextSize;
    private int mTextColor;

    /**
     * @param color  背景颜色
     * @param radius 圆角半径
     */
    public RadiusBackgroundSpan(int color, int radius) {
        this(color,radius,0,0);
    }


    public RadiusBackgroundSpan(int color, int radius, int textSize, int textColor) {
        mColor = color;
        mRadius = radius;
        mTextSize = textSize;
        mTextColor = textColor;
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        if(mTextSize != 0) {
            paint.setTextSize(mTextSize);
        }
        mSize = (int) (paint.measureText(text, start, end) + 2 * mRadius);
        return mSize;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        int color = paint.getColor();//保存文字颜色
        paint.setColor(mColor);//设置背景颜色
        paint.setAntiAlias(true);// 设置画笔的锯齿效果
        RectF oval = new RectF(x, y + paint.ascent(), x + mSize, y + paint.descent());
        //设置文字背景矩形，x为span其实左上角相对整个TextView的x值，y为span左上角相对整个View的y值。paint.ascent()获得文字上边缘，paint.descent()获得文字下边缘
        canvas.drawRoundRect(oval, mRadius, mRadius, paint);//绘制圆角矩形，第二个参数是x半径，第三个参数是y半径
        paint.setColor(color);//恢复画笔的文字颜色
        if(mTextColor != 0) {
            paint.setColor(mTextColor);
        }
        canvas.drawText(text, start, end, x + mRadius, y, paint);//绘制文字
    }
}