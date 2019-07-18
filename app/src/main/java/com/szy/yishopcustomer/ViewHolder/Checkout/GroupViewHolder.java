package com.szy.yishopcustomer.ViewHolder.Checkout;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 宗仁 on 16/7/20.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class GroupViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.fragment_checkout_group_layout)
    public LinearLayout groupLayout;
    @BindView(R.id.fragment_checkout_group_titleTextView)
    public TextView titleTextView;
    @BindView(R.id.fragment_checkout_group_selectedItemTextView)
    public TextView selectedItemTextView;
    @BindView(R.id.fragment_checkout_group_indicatorImageView)
    public ImageView indicatorImageView;

    public GroupViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
