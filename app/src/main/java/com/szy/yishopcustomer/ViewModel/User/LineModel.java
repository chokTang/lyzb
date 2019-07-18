package com.szy.yishopcustomer.ViewModel.User;

import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;

import com.lyzb.jbx.R;

/**
 * Created by Smart on 2018/1/5.
 */

public class LineModel {
    private int resourceId = R.color.colorTen;
    private float heightDp = 0.6f;
    private float lrPadding = 0;

    public LineModel(){
    }

    public int getResourceId() {
        return resourceId;
    }

    public float getHeightDp() {
        return heightDp;
    }

    public float getLrPadding() {
        return lrPadding;
    }

    public LineModel setHeightDp(float heightDp) {
        this.heightDp = heightDp;
        return this;
    }

    public LineModel setResourceId(@DrawableRes int resourceId) {
        this.resourceId = resourceId;
        return this;
    }

    public LineModel setLrPadding(float lrPadding) {
        this.lrPadding = lrPadding;
        return this;
    }

}
