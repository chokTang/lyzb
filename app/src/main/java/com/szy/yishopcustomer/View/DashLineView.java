package com.szy.yishopcustomer.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.*;

import com.lyzb.jbx.R;

/**
 * @author wyx
 * @role
 * @time 2018 2018/7/11 11:32
 */

public class DashLineView extends View {

    private Paint mPaint;
    private Path mPath;

    public DashLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(getResources().getColor(R.color.ing_expl_view));
        // 需要加上这句，否则画不出东西
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
        mPaint.setPathEffect(new DashPathEffect(new float[] {15, 5}, 0));

        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int centerY = getHeight() / 2;
        mPath.reset();
        mPath.moveTo(0, centerY);
        mPath.lineTo(getWidth(), centerY);
        canvas.drawPath(mPath, mPaint);
    }
}
