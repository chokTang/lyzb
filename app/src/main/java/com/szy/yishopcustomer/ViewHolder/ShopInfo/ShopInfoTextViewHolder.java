package com.szy.yishopcustomer.ViewHolder.ShopInfo;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 宗仁 on 2017/1/10.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class ShopInfoTextViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.fragment_shop_info_item_text_nameTextView)
    public TextView nameTextView;
    @BindView(R.id.fragment_shop_info_item_text_valueTextView)
    public TextView valueTextView;
    @BindView(R.id.fragment_shop_info_item_text_iconImageView)
    public ImageView iconImageView;

    public ShopInfoTextViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
