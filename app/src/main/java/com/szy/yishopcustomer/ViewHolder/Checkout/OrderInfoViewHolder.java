package com.szy.yishopcustomer.ViewHolder.Checkout;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liwei on 17/3/28.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class OrderInfoViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.fragment_checkout_product_amount)
    public TextView productAmount;
    @BindView(R.id.fragment_checkout_shipping_fee_textView)
    public TextView shippingFee_text;
    @BindView(R.id.fragment_checkout_shipping_fee)
    public TextView shippingFee;
    @BindView(R.id.fragment_checkout_cash_more_textView)
    public TextView cashMoreTextView;
    @BindView(R.id.fragment_checkout_cash_more)
    public TextView cashMore;
    @BindView(R.id.fragment_checkout_bonus)
    public TextView bonus;
    @BindView(R.id.fragment_checkout_benefit)
    public TextView benefit;
    @BindView(R.id.fragment_order_detail_confirm_time)
    public TextView balance;
    @BindView(R.id.fragment_checkout_shipping_deductible)
    public TextView shippingDeductible;


    public OrderInfoViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
