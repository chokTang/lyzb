package com.lyzb.jbx.util.label;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

public abstract class TypeClickableSpan extends ClickableSpan {

    public abstract void onClick(String value, View widget);

    private String value;

    public TypeClickableSpan(String value) {
        this.value = value;
    }

    @Override
    public void onClick(@NonNull View widget) {
        onClick(value, widget);
    }

    @Override
    public void updateDrawState(@NonNull TextPaint ds) {
        if (ds!=null){
            ds.setColor(Color.parseColor("#247cf0"));
            ds.setUnderlineText(false);
        }
    }
}
