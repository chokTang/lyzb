package com.szy.yishopcustomer.ViewHolder.ShopGoodsList;

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

public class ShopCategoryViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.fragment_shop_category_list_item_titleTextView)
    public TextView titleTextView;
    @BindView(R.id.fragment_shop_category_list_item_numberTextView)
    public ImageView numberTextView;

    public ShopCategoryViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
