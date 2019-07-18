package com.lyzb.jbx.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.lyzb.jbx.R;

/**
 * 宽高占比的View
 */
public class ProportionImageView extends AppCompatImageView {
    private int proportionWidth = -1;
    private int proportionHeight = -1;

    public ProportionImageView(Context context) {
        this(context, null);
    }

    public ProportionImageView(Context context, AttributeSet attrs) {

        this(context, attrs, 0);
    }

    public ProportionImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.proportionImageView);
        if (ta != null) {
            proportionWidth = ta.getInteger(R.styleable.proportionImageView_proportionWidth, -1);
            proportionHeight = ta.getInteger(R.styleable.proportionImageView_proportionHeight, -1);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (proportionWidth == -1 || proportionHeight == -1) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        int width = MeasureSpec.getSize(widthMeasureSpec);

        setMeasuredDimension(width, width * proportionHeight / proportionWidth);

    }
}
