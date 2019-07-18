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

public class ShopInfoImageViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.fragment_shop_info_item_image_nameTextView)
    public TextView nameTextView;
    @BindView(R.id.national_emblem_light)
    public ImageView imageView1;
    @BindView(R.id.national_emblem_light2)
    public ImageView imageView2;

    public ShopInfoImageViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
