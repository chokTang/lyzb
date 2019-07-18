package com.szy.yishopcustomer.ViewHolder.Order;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.LinearLayout;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lw on 2016/6/13.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司
 */
public class OrderListStatusViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.item_order_list_status)
    public LinearLayout mOrderListStatus;

    @BindView(R.id.linearlayout_buttons)
    public LinearLayout linearlayout_buttons;


    public OrderListStatusViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
