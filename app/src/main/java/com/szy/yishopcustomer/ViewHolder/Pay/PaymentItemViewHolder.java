package com.szy.yishopcustomer.ViewHolder.Pay;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 宗仁 on 16/8/2.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class PaymentItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.fragment_pay_payment_payNameTextView)
    public TextView nameTextView;
    @BindView(R.id.fragment_tv_wxpay_hint)
    public TextView wxpayHintText;

    @BindView(R.id.fragment_pay_payment_checkboxImageView)
    public ImageView paymentImage;

    @BindView(R.id.image_checkbox_pay_payment)
    public ImageView checkboxImage;

    public PaymentItemViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
