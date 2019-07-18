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
public class OrderAddressViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.fragment_order_detail_consignee)
    public TextView mConsignee;
    @BindView(R.id.fragment_order_detail_tel)
    public TextView mTel;
    @BindView(R.id.fragment_order_detail_address)
    public TextView mAddress;

    public OrderAddressViewHolder(View view) {
        super(view);

        ButterKnife.bind(this, view);
    }
}