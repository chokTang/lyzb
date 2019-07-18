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
 * Created by Smart on 17/11/23.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class CartPackageViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.item_cart_goods_price_textView)
    public TextView priceTextView;
    @BindView(R.id.image_checkbox)
    public ImageView treeCheckBox;
    @BindView(R.id.item_cart_goods_delete_button)
    public ImageView goodsDeleteButton;

    @BindView(R.id.plus_button)
    public ImageView addButton;
    @BindView(R.id.minus_button)
    public ImageView minusButton;
    @BindView(R.id.goods_number)
    public TextView goodsNumberEditText;

    @BindView(R.id.item_cart_act_name_textView)
    public TextView nameTextView;

    @BindView(R.id.linearlayout_goods)
    public LinearLayout linearlayout_goods;

    @BindView(R.id.item_cart_goods_invalid_tip)
    public View item_cart_goods_invalid_tip;
    @BindView(R.id.item_order_explain)
    public View item_order_explain;
    @BindView(R.id.layout_add_to_cart)
    public View layout_add_to_cart;
    @BindView(R.id.textViewSetPriceTip)
    public TextView textViewSetPriceTip;


    public CartPackageViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
