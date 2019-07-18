package com.szy.yishopcustomer.ViewHolder.Order;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lw on 2016/6/13.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司
 */
public class OrderListTotalViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.item_order_list_total)
    public TextView orderListTotal;
    @BindView(R.id.item_order_list_amount)
    public TextView orderListAmount;
    @BindView(R.id.item_order_list_shipping_fee)
    public TextView orderListShippingFee;
    @BindView(R.id.price_edit)
    public TextView orderListPriceEdit;

    public OrderListTotalViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
