package com.szy.yishopcustomer.ViewHolder.Checkout;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 宗仁 on 16/7/20.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ShopOrderViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.fragment_checkout_shop_order_totalPriceTextView)
    public TextView totalPriceTextView;
    @BindView(R.id.fragment_checkout_shop_order_labelTextView)
    public TextView totalPriceTextViewTip;

    @BindView(R.id.textViewFullCutAmount)
    public TextView textViewFullCutAmount;

    @BindView(R.id.fragment_checkout_shop_order_totalDeductibleTextView)
    public TextView totalDeductibleTextView;

    public ShopOrderViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
