package com.szy.yishopcustomer.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.*;

import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.Utils;

/**
 * Created by 宗仁 on 16/8/18.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class BonusBackgroundView extends View {
    public int drawableResource = R.drawable.fragment_bonus_background;

    public BonusBackgroundView(Context context) {
        super(context);
    }

    public BonusBackgroundView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BonusBackgroundView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setEnabled(boolean enabled) {
        if (enabled) {
            drawableResource = R.drawable.fragment_bonus_background;
        } else {
            drawableResource = R.drawable.fragment_bonus_background_disabled;
        }
        this.requestLayout();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Drawable drawable = ContextCompat.getDrawable(getContext(), drawableResource);
        if (drawable != null) {
            Bitmap bitmap = Utils.toBitmap(drawable);
            BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
            bitmapDrawable.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
            bitmapDrawable.setBounds(canvas.getClipBounds());
            bitmapDrawable.draw(canvas);
        }
    }
}
