package com.lyzb.jbx.adapter;

import android.content.Context;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.lyzb.jbx.R;


import java.util.List;

/**
 * Created by Administrator on 2018/7/31.
 */

public class LoginAdapter extends BaseRecyleAdapter<Object> {
    public LoginAdapter(Context context, List<Object> list) {
        super(context, R.layout.recycle_login_adapter, list);
    }

    @Override
    protected void convert(BaseViewHolder holder, Object item) {

    }
}
