package com.szy.yishopcustomer.ViewHolder.Checkout;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 宗仁 on 16/7/20.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ShopViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.fragment_checkout_shop_nameTextView)
    public TextView nameTextView;
    @BindView(R.id.fragment_checkout_shop_iconImageView)
    public ImageView iconImageView;

    @BindView(R.id.textViewTableNum)
    public TextView textViewTableNum;

    public ShopViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
