package com.szy.yishopcustomer.ViewHolder.OrderDetail;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liwei on 17/6/07.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class OrderQrcodeViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.fragment_order_detail_qrcode)
    public ImageView mQrcode;
    @BindView(R.id.fragment_order_detail_order_sn)
    public TextView mOrderSn;
    @BindView(R.id.textViewReachbuyNumber)
    public TextView textViewReachbuyNumber;

    public OrderQrcodeViewHolder(View view) {
        super(view);

        ButterKnife.bind(this, view);
    }
}