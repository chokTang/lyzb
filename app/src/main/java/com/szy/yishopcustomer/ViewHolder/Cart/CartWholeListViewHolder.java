package com.szy.yishopcustomer.ViewHolder.Cart;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Smart on 2017/9/7.
 */

public class CartWholeListViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.item_cart_goods_attribute_textView)
    public TextView attrTextView;
    @BindView(R.id.item_cart_goods_price_textView)
    public TextView priceTextView;
    @BindView(R.id.image_checkbox)
    public ImageView treeCheckBox;
    @BindView(R.id.minus_button)
    public ImageView minusButton;
    @BindView(R.id.goods_number)
    public TextView goodsNumberEditText;
    @BindView(R.id.plus_button)
    public ImageView addButton;

    @BindView(R.id.linearlayout_root)
    public LinearLayout linearlayout_root;

    public CartWholeListViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}