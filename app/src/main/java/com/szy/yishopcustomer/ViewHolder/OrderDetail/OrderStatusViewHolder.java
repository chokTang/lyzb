package com.szy.yishopcustomer.ViewHolder.OrderDetail;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liwei on 16/12/06.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class OrderStatusViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.trading_status)
    public TextView mOrderStatus;

    public OrderStatusViewHolder(View view) {
        super(view);

        ButterKnife.bind(this, view);
    }
}