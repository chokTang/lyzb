package com.szy.yishopcustomer.Other;

import android.content.Context;
import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.*;

import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.Utils;

/**
 * Created by Smart on 2017/9/23.
 */
public class MyURLSpan extends ClickableSpan {

    private String url;
    private Context mContext;

    public MyURLSpan(Context context,String url) {
        this.mContext = context;
        this.url = url;
    }

    @Override
    public void onClick(View widget) {
        Utils.openBrowser(mContext, url);
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(mContext.getResources().getColor(R.color.colorBlue));
//        ds.setColor(ds.linkColor);
        ds.setUnderlineText(false);
    }
}