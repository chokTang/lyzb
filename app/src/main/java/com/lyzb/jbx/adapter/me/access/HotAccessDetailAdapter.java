package com.lyzb.jbx.adapter.me.access;

import android.content.Context;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.lyzb.jbx.R;

import java.util.List;

public class HotAccessDetailAdapter extends BaseRecyleAdapter<String> {

    public HotAccessDetailAdapter(Context context, List<String> list) {
        super(context, R.layout.recycle_hot_access_detail, list);
    }

    @Override
    protected void convert(BaseViewHolder holder, String item) {

    }
}
