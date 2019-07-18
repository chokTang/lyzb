package com.szy.yishopcustomer.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liwei on 17/6/3.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class PickUpViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.image_checkbox)
    public ImageView imageView_check;

    @BindView(R.id.pick_up_shop_name)
    public TextView mShopName;
    @BindView(R.id.pick_up_shop_address)
    public TextView mShopAddress;
    @BindView(R.id.pick_up_shop_tel)
    public TextView pick_up_shop_tel;

    @BindView(R.id.linearlayout_shop_address)
    public View linearlayout_shop_address;
    @BindView(R.id.linearlayout_shop_tel)
    public View linearlayout_shop_tel;

    public PickUpViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setTag(this);
    }
}
