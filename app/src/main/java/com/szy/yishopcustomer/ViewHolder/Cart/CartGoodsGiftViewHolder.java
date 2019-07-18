package com.szy.yishopcustomer.ViewHolder.Cart;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 宗仁 on 2016/8/18.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class CartGoodsGiftViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.item_cart_goods_name_textView)
    public TextView nameTextView;
    @BindView(R.id.item_cart_goods_price_textView)
    public TextView priceTextView;
    @BindView(R.id.item_cart_goods_attribute_textView)
    public TextView attrTextView;
    @BindView(R.id.item_cart_goods_imageView)
    public ImageView goodsImageView;
    @BindView(R.id.item_cart_goods_number)
    public TextView goodsNumberEditText;

    public CartGoodsGiftViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
