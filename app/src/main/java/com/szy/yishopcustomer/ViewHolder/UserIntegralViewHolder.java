package com.szy.yishopcustomer.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Smart on 2017/7/5.
 */

public class UserIntegralViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.imageView_shop)
    public ImageView imageView_shop;
    @BindView(R.id.textView_shop_name)
    public TextView textView_shop_name;
    @BindView(R.id.textView_store_points)
    public TextView textView_store_points;

    public UserIntegralViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
