package com.szy.yishopcustomer.ViewHolder.Order;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lw on 2016/6/12.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司
 */
public class OrderListViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.item_order_list_goods_imageView)
    public ImageView mGoodsImageView;
    @Nullable
    @BindView(R.id.fragment_order_goods_activity)
    public TextView mGoodsActivity;
    @Nullable
    @BindView(R.id.item_order_list_goods_name_textView)
    public TextView mGoodsName;
    @Nullable
    @BindView(R.id.fragment_checkout_goods_numberTextView)
    public TextView mGoodsPrice;
    @Nullable
    @BindView(R.id.fragment_order_list_goods_number)
    public TextView mGoodsNumber;
    @Nullable
    @BindView(R.id.item_order_list_goods_attribute_textView)
    public TextView mGoodsAttribute;
    @Nullable
    @BindView(R.id.item_order_list)
    public RelativeLayout mItemGoods;
    @Nullable
    @BindView(R.id.fragment_order_list_back_status)
    public TextView mGoodsBackStatus;
    @Nullable
    @BindView(R.id.item_order_explain)
    public TextView item_order_explain;
    @Nullable
    @BindView(R.id.item_order_list_goods_deductibleTextView)
    public TextView deductibleTextView;

    public OrderListViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
