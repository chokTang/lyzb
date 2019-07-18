package com.szy.yishopcustomer.ViewHolder.Cart;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Smart on 17/11/23.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class CartFullcutViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.textViewActMessgae)
    public TextView textViewActMessgae;
    @BindView(R.id.textViewGoList)
    public TextView textViewGoList;

    @BindView(R.id.linearlayout_goods)
    public LinearLayout linearlayout_goods;

    @BindView(R.id.linearlayoutGift)
    public View linearlayoutGift;

    @BindView(R.id.linearlayoutGiftList)
    public LinearLayout linearlayoutGiftList;

    public CartFullcutViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
