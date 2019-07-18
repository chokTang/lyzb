package com.szy.yishopcustomer.ViewHolder.Cart;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zongren on 16/5/21.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class CartGoodsViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.item_cart_goods_treeCheckBox)
    public ImageView treeCheckBox;
    @BindView(R.id.item_cart_goods_name_textView)
    public TextView nameTextView;
    @BindView(R.id.item_cart_goods_price_textView)
    public TextView priceTextView;
    @BindView(R.id.item_cart_goods_attribute_textView)
    public TextView attrTextView;
    @BindView(R.id.item_cart_goods_imageView)
    public ImageView goodsImageView;
    @BindView(R.id.plus_button)
    public ImageView addButton;
    @BindView(R.id.minus_button)
    public ImageView minusButton;
    @BindView(R.id.goods_number)
    public TextView goodsNumberEditText;
    @BindView(R.id.item_cart_goods_delete_button)
    public ImageView goodsDeleteButton;
    @BindView(R.id.fragment_cart_goods_activity)
    public TextView goodsActivityTip;
    @BindView(R.id.fragment_cart_goods_moq)
    public TextView goodsMoq;
    @BindView(R.id.item_cart_deductibleTextView)
    public TextView deductibleTextView;

    public CartGoodsViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
