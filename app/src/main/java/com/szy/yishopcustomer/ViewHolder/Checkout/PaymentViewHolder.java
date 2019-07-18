package com.szy.yishopcustomer.ViewHolder.Checkout;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 宗仁 on 16/8/5.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class PaymentViewHolder extends RecyclerView.ViewHolder {
    @Nullable
    @BindView(R.id.fragment_checkout_payment_linarLayout)
    public RelativeLayout paymentLayout;
    @Nullable
    @BindView(R.id.image_checkbox)
    public ImageView paymentCheckbox;
    @BindView(R.id.fragment_checkout_payment_checkboxImageView)
    public ImageView paymentImageView;
    @BindView(R.id.fragment_checkout_payment_nameTextView)
    public TextView nameTextView;
    @BindView(R.id.fragment_checkout_payment_wx_title)
    public TextView wxTitleText;

    @BindView(R.id.fragment_checkout_payment_labelTextView)
    public TextView labelTextView;

    public PaymentViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
