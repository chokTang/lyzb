package com.szy.yishopcustomer.ViewHolder.Cart;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Smart on 2017/7/31.
 */

public class CartStoreTipViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.textView_tips)
    public TextView textView_tips;
    @BindView(R.id.linearlayout_go_shop)
    public View linearlayout_go_shop;

    @BindView(R.id.linearlayout_root)
    public View linearlayout_root;

    public CartStoreTipViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
