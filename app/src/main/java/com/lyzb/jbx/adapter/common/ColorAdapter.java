package com.lyzb.jbx.adapter.common;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.lyzb.jbx.R;

import java.util.Arrays;

public class ColorAdapter extends BaseRecyleAdapter<String> {

    public ColorAdapter(Context context) {
        super(context, R.layout.recycle_color_text, null);
        _list.addAll(Arrays.asList("#333333", "#FF0000", "#FF7D00", "#FFFF00", "#00FF00", "#00FFFF", "#0000FF", "#FF00FF"));
    }

    @Override
    protected void convert(BaseViewHolder holder, String item) {
        TextView colorText = holder.cdFindViewById(R.id.v_color);
        colorText.setBackgroundColor(Color.parseColor(item));
    }
}
