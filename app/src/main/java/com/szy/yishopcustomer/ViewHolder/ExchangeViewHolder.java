package com.szy.yishopcustomer.ViewHolder;

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
 * Created by Smart on 2017/7/6.
 */

public class ExchangeViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.item_order_list)
    public LinearLayout mItemGoods;

    @BindView(R.id.item_order_list_goods_name_textView)
    public TextView item_order_list_goods_name_textView;
    @BindView(R.id.item_order_list_goods_imageView)
    public ImageView item_order_list_goods_imageView;
    @BindView(R.id.item_order_list_goods_attribute_textView)
    public TextView item_order_list_goods_attribute_textView;

    @BindView(R.id.item_order_list_shop_name_textView)
    public TextView item_order_list_shop_name_textView;
    @BindView(R.id.item_order_list_order_status)
    public TextView item_order_list_order_status;

    @BindView(R.id.fragment_checkout_goods_numberTextView)
    public TextView fragment_checkout_goods_numberTextView;
    @BindView(R.id.fragment_order_list_goods_number)
    public TextView fragment_order_list_goods_number;

    @BindView(R.id.relativeLayout_title)
    public View relativeLayout_title;

    @BindView(R.id.linearlayout_buttons)
    public LinearLayout linearlayout_buttons;

    public ExchangeViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
