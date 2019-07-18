package com.szy.yishopcustomer.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lw on 2016/6/13.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司
 */
public class ShopTitleViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.item_shop_name)
    public TextView mShopNameTextView;
    @BindView(R.id.item_status)
    public TextView mStatus;

    //桌号
    @BindView(R.id.textViewTableNum)
    public TextView textViewTableNum;

    public ShopTitleViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
