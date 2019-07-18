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

public class PickUpListViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.textView_pickup_name)
    public TextView textView_pickup_name;
    @BindView(R.id.textView_pickup_address)
    public TextView textView_pickup_address;
    @BindView(R.id.textView_pickup_desc)
    public TextView textView_pickup_desc;

    @BindView(R.id.imageView_pickup)
    public ImageView imageView_pickup;
    @BindView(R.id.view_line)
    public View view_line;
    @BindView(R.id.imageView_pickup_tel)
    public ImageView imageView_pickup_tel;

    public PickUpListViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
