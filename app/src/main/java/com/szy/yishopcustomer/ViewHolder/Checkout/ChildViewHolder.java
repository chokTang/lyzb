package com.szy.yishopcustomer.ViewHolder.Checkout;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 宗仁 on 16/7/20.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ChildViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.fragment_checkout_child_layout)
    public RelativeLayout childLayout;
    @BindView(R.id.fragment_checkout_child_titleTextView)
    public TextView titleTextView;
    @BindView(R.id.fragment_checkout_child_titleTwoTextView)
    public TextView titleTwoTextView;
    @BindView(R.id.image_checkbox)
    public ImageView indicatorImageView;
    @BindView(R.id.fragment_checkout_child_image)
    public ImageView childImage;
    @BindView(R.id.fragment_checkout_child_subtitleTextView)
    public TextView subTitleTextView;

    public ChildViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
