package com.szy.yishopcustomer.ViewHolder.Collection;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liwei on 17/1/6.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class CollectionGoodsViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.collection_goods_image)
    public ImageView goodsImage;
    @BindView(R.id.collection_goods_name_textView)
    public TextView goodsNameTextView;
    @BindView(R.id.collection_goods_price_textView)
    public TextView goodsPriceTextView;
    @BindView(R.id.tv_collec_dedu)
    public TextView goodsDudePrice;

    @BindView(R.id.plus_button)
    public ImageView plusButton;
    @BindView(R.id.minus_button)
    public ImageView minusButton;
    @BindView(R.id.item_collection_goods_number)
    public TextView goodsNumber;
    @BindView(R.id.image_checkbox)
    public ImageView checkboxImageView;
    @BindView(R.id.collection_goods_edit_cart_layout)
    public LinearLayout cartLayout;

    public CollectionGoodsViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
