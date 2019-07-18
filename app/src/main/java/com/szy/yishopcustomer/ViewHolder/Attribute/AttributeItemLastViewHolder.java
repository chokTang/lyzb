package com.szy.yishopcustomer.ViewHolder.Attribute;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Smart on 17/9/8.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class AttributeItemLastViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.textView_sku_name)
    public TextView textView_sku_name;
    @BindView(R.id.textView_sku_stock)
    public TextView textView_sku_stock;

    @BindView(R.id.item_cart_goods_minus_button)
    public ImageView item_cart_goods_minus_button;
    @BindView(R.id.item_cart_goods_add_button)
    public ImageView item_cart_goods_add_button;
    @BindView(R.id.item_cart_goods_number)
    public TextView item_cart_goods_number;

    public AttributeItemLastViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
