package com.szy.yishopcustomer.View;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.*;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lyzb.jbx.R;

/**
 * Created by Smart on 2017/12/8.
 */
public class FilterCheckBox extends LinearLayout implements Checkable, View.OnClickListener {

    private CustomTextView tv;
    private ImageView imageView;

    private Drawable check_img;
    private String text;
    private ColorStateList textColor = null;
    private int textSize = 14;
    private int image_width = 24;

    //图片和文字的间距
    private int spacing = 20;


    private boolean mChecked;

    public FilterCheckBox(Context context) {
        super(context);
        init(context, null);
    }

    public FilterCheckBox(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FilterCheckBox(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public FilterCheckBox(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.filterCheckBox);
            text = array.getString(R.styleable.filterCheckBox_ftext);
            textColor = array.getColorStateList(R.styleable.filterCheckBox_ftextColor);
            textSize = array.getDimensionPixelSize(R.styleable.filterCheckBox_ftextSize, textSize);
            spacing = array.getDimensionPixelSize(R.styleable.filterCheckBox_spacing, spacing);
            check_img = array.getDrawable(R.styleable.filterCheckBox_checkimg);
            image_width = array.getDimensionPixelSize(R.styleable.filterCheckBox_imageWidth, image_width);

            array.recycle();
        }

        if (check_img != null) {
            LayoutParams imParams = new LayoutParams(image_width, image_width);
            imParams.setMargins(0, 0, spacing, 0);
            imageView = new ImageView(context);
            imageView.setImageDrawable(check_img);
            imageView.setLayoutParams(imParams);
            imageView.setVisibility(GONE);
            addView(imageView);
        }

        tv = new CustomTextView(context);
        tv.setText(text);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
        tv.setMaxLines(1);
        tv.setEllipsize(TextUtils.TruncateAt.END);
        tv.setTextColor(textColor != null ? textColor : ColorStateList.valueOf(0xFF222222));
        addView(tv);

        setGravity(Gravity.CENTER);
        setOrientation(HORIZONTAL);
//        setOnClickListener(this);
    }

    @Override
    public void setChecked(boolean checked) {
        if(checked) {
            setImageVisibility(View.VISIBLE);
        } else {
            setImageVisibility(View.GONE);
        }

        if (mChecked != checked) {
            mChecked = checked;
            refreshDrawableState();
        }

    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void toggle() {

    }

    public void setText(String text) {
        this.text = text;
        if(tv != null) {
            tv.setText(text);
        }
    }

    private static final int[] CHECKED_STATE_SET = {
            android.R.attr.state_checked
    };

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }

    @Override
    public void onClick(View v) {
        if (isChecked()) {
            setChecked(false);
        } else {
            setChecked(true);
        }
    }

    void setImageVisibility(int visibility) {
        if (imageView != null) {
            imageView.setVisibility(visibility);
        }
    }

    class CustomTextView extends android.support.v7.widget.AppCompatTextView {

        public CustomTextView(Context context) {
            super(context);
        }

        @Override
        protected int[] onCreateDrawableState(int extraSpace) {
            final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
            if (isChecked()) {
                mergeDrawableStates(drawableState, CHECKED_STATE_SET);
            }
            return drawableState;
        }
    }
}
