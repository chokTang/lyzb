package com.szy.yishopcustomer.ViewHolder.OrderDetail;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liwei on 16/12/06.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class OrderGoodsViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.fragment_order_detail_goodsImg)
    public ImageView mGoodsThumb;
    @BindView(R.id.fragment_order_detail_goods_price)
    public TextView mGoodsPrice;
    @BindView(R.id.fragment_order_detail_goods_layout)
    public RelativeLayout mGoodsItem;
    @BindView(R.id.fragment_order_detail_goods_name)
    public TextView mGoodsName;
    @BindView(R.id.fragment_order_detail_spec_info)
    public TextView mGoodsSpec;
    @BindView(R.id.fragment_order_detail_goods_number)
    public TextView mGoodsNumber;
    @BindView(R.id.fragment_order_detail_goods_status)
    public TextView mGoodsStatus;
    @BindView(R.id.back_button_layout)
    public LinearLayout mBackButtonLayout;
    @BindView(R.id.back_button)
    public TextView mBackButton;
    @BindView(R.id.ll_activity)
    public LinearLayout llActivity;

    public OrderGoodsViewHolder(View view) {
        super(view);

        ButterKnife.bind(this, view);
    }
}