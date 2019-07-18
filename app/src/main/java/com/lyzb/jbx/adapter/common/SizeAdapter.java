package com.lyzb.jbx.adapter.common;

import android.content.Context;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.lyzb.jbx.R;

import java.util.Arrays;

public class SizeAdapter extends BaseRecyleAdapter<String> {

    public SizeAdapter(Context context) {
        super(context, R.layout.recycle_color_text, null);
        _list.addAll(Arrays.asList("H1", "H2", "H3", "H4", "H5", "H6", "H7"));
    }

    @Override
    protected void convert(BaseViewHolder holder, String item) {
        holder.setText(R.id.v_color, item);
    }
}
