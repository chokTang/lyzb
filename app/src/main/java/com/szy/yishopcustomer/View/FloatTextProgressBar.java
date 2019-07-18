package com.szy.yishopcustomer.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.lyzb.jbx.R;

/**
 * Created by liwei on 2016/11/4.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class FloatTextProgressBar extends ProgressBar {

    /**
     * 进度条填充颜色
     */
    protected int barFillColor;
    /**
     * 进度条高度
     */
    private float progressHeight;
    /**
     * 文字大小
     */
    private float textSize;

    public FloatTextProgressBar(Context context) {
        super(context);
    }

    public FloatTextProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public FloatTextProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @Override
    public void drawProgress(Canvas canvas) {
        //绘制未填充进度条
        paint.setColor(backgroundColor);
        RectF backgroundRectF = new RectF(0, height - progressHeight, width, height);
        canvas.drawRoundRect(backgroundRectF, progressHeight / 2, progressHeight / 2, paint);

        //绘制填充条
        paint.setColor(barFillColor);
        RectF fillRectF = new RectF(0, height - progressHeight, progressWidth, height);
        canvas.drawRoundRect(fillRectF, progressHeight / 2, progressHeight / 2, paint);
    }

    @Override
    protected void getDimension() {
        super.getDimension();
        progressHeight = height;
        textSize = height;
    }

    @Override
    public void drawText(Canvas canvas) {
        paint.setColor(textColor);
        paint.setTextSize(textSize);

        float textWidth = paint.measureText(progress + "%");
        canvas.drawText(progress + "%", width / 2 - textWidth/2, (height / 6) * 5, paint);

        /*if (progressWidth < height) {
            canvas.drawText(progress + "%", textWidth / 2, (height / 6) * 5, paint);
        } else if (width - progressWidth < height) {
            canvas.drawText(progress + "%", width - height / 2 - textWidth, (height / 6) * 5,
                    paint);
        } else {
            canvas.drawText(progress + "%", progressWidth - textWidth / 2, (height / 6) * 5, paint);
        }*/
    }

    /**
     * 设置填充色
     *
     * @param fillColor
     */
    public void setFillColor(int fillColor) {
        this.barFillColor = fillColor;
    }

    private void init(AttributeSet attrs) {
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable
                .floatTextProgressBar);
        barFillColor = a.getColor(R.styleable.floatTextProgressBar_barFillColor, 0xffff0000);
        a.recycle();
    }

}
