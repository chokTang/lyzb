package com.lyzb.jbx.adapter.me.hot;

import android.content.Context;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.lyzb.jbx.R;

import java.util.List;

public class HotAccessAdapter extends BaseRecyleAdapter<String> {

    public HotAccessAdapter(Context context,List<String> list) {
        super(context, R.layout.recycle_hot_access, list);
    }

    @Override
    protected void convert(BaseViewHolder holder, String item) {

    }
}
