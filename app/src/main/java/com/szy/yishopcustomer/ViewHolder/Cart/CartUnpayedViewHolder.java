package com.szy.yishopcustomer.ViewHolder.Cart;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 宗仁 on 2016/8/19.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class CartUnpayedViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.order_sn)
    public TextView unpayedOrderSn;
    @BindView(R.id.add_time)
    public TextView unpayedOrderAddTime;
    @BindView(R.id.order_amount)
    public TextView unpayedOrderAmount;
    @BindView(R.id.order_check_button)
    public TextView unpayedOrderCheckButton;

    public CartUnpayedViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
