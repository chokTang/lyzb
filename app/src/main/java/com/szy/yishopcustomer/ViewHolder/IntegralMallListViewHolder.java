package com.szy.yishopcustomer.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Smart on 2017/7/4.
 */

public class IntegralMallListViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.imageView_shop)
    public ImageView imageView_shop;
    @BindView(R.id.textView_goods_name)
    public TextView textView_goods_name;
    @BindView(R.id.textView_use_condition)
    public TextView textView_use_condition;
    @BindView(R.id.textView_shop_integral)
    public TextView textView_shop_integral;
    @BindView(R.id.textView_exchange_figures)
    public TextView textView_exchange_figures;
    @BindView(R.id.textView_mall_name)
    public TextView textView_mall_name;

    @BindView(R.id.fragment_button_integral)
    public Button fragment_button_integral;

    @BindView(R.id.fragment_goods_list_item_seller_all_goodsImageView)
    public ImageView sellerImageView;


    public IntegralMallListViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
