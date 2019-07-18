package com.szy.yishopcustomer.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Smart on 2017/11/21.
 */

public class PromotionViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.linearlayout_visible)
    public View linearlayout_visible;
    @BindView(R.id.linearlayout_invisible)
    public LinearLayout linearlayout_invisible;
    @BindView(R.id.linearlayout_goods)
    public LinearLayout linearlayout_goods;
    @BindView(R.id.linearlayout_control)
    public View linearlayout_control;
    @BindView(R.id.imageViewArrow)
    public ImageView imageViewArrow;
    @BindView(R.id.textViewTitle)
    public TextView textViewTitle;
    @BindView(R.id.textViewSave)
    public TextView textViewSave;
    @BindView(R.id.textViewActPrice)
    public TextView textViewActPrice;
    @BindView(R.id.textViewAllPrice)
    public TextView textViewAllPrice;
    @BindView(R.id.goods_number)
    public TextView item_cart_goods_number;

    @BindView(R.id.minus_button)
    public View item_cart_goods_minus_button;
    @BindView(R.id.plus_button)
    public View item_cart_goods_add_button;

    @BindView(R.id.button_add_cart)
    public View button_add_cart;
    @BindView(R.id.button_buy_now)
    public View button_buy_now;


    public PromotionViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }
}
