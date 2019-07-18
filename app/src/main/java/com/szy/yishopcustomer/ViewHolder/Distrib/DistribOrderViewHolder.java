package com.szy.yishopcustomer.ViewHolder.Distrib;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liwei on 2017/7/19.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class DistribOrderViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.fragment_distrib_order_user_name)
    public TextView userName;
    @BindView(R.id.fragment_distrib_order_user_rank)
    public ImageView userRank;
    @BindView(R.id.fragment_distrib_order_shop_name)
    public TextView shopName;
    @BindView(R.id.fragment_distrib_order_sn)
    public TextView orderSn;
    @BindView(R.id.fragment_distrib_order_dis_money)
    public TextView disMoney;
    @BindView(R.id.fragment_distrib_order_rate)
    public TextView orderRate;
    @BindView(R.id.dis_status)
    public ImageView disStatus;

    public DistribOrderViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
