package com.szy.yishopcustomer.ViewHolder.Cart;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Optional;

/**
 * Created by zongren on 16/5/21.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class CartShopViewHolder extends RecyclerView.ViewHolder {
    @Nullable
    @BindView(R.id.image_checkbox)
    public ImageView treeCheckBox;
    @BindView(R.id.item_cart_shop_name_textView)
    public TextView nameTextView;
    @BindView(R.id.item_cart_shop_grab_bonus)
    public TextView nameCartShopGrabBonus;
    @BindView(R.id.textViewTableNum)
    public TextView textViewTableNum;

    public CartShopViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
