package com.szy.yishopcustomer.ViewHolder;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.model.Text;
import com.szy.common.View.SquareImageView;
import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Smart on 2017/7/5.
 */

public class ShopCartViewHolder extends RecyclerView.ViewHolder {


    @BindView(R.id.imageView_shop)
    public ImageView tipImageView;
    @BindView(R.id.textView_goods_name)
    public TextView textView_goods_name;
    @BindView(R.id.textView_use_condition)
    public TextView textView_use_condition;
    @BindView(R.id.textView_shop_integral)
    public TextView textView_shop_integral;

    @BindView(R.id.minus_button)
    public ImageView item_cart_goods_minus_button;
    @BindView(R.id.plus_button)
    public ImageView item_cart_goods_add_button;
    @BindView(R.id.goods_number)
    public TextView item_cart_goods_number;
    @BindView(R.id.textView_invalid)
    public TextView textView_invalid;
    @BindView(R.id.layout_add_to_cart)
    public View linearlayout_number;

    @BindView(R.id.image_checkbox)
    public ImageView imageView_check;


    public ShopCartViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
