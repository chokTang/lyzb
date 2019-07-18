package com.szy.yishopcustomer.ViewHolder.Goods;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liwei on 17/06/09.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class GoodsGiftListViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.gift_image)
    public ImageView mGiftImage;
    @BindView(R.id.gift_number)
    public TextView mGiftNumber;

    public GoodsGiftListViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}