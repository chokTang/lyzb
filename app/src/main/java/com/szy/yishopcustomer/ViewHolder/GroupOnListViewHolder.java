package com.szy.yishopcustomer.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liuzhifeng on 2017/3/21.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class GroupOnListViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.item_group_on_imageView)
    public ImageView mGoodsImage;
    @BindView(R.id.item_group_on_soldout)
    public ImageView mSoldOutImage;
    @BindView(R.id.item_group_on_goods_name_textView)
    public TextView mGoodsName;
    @BindView(R.id.item_group_on_shop_name_textView)
    public TextView mShopName;
    @BindView(R.id.item_group_on_price)
    public TextView mShopPrice;
    @BindView(R.id.item_group_on_old_price)
    public TextView mShopOlderPrice;
    @BindView(R.id.item_group_on_go_group_on)
    public TextView mGoGroupOn;
    @BindView(R.id.item_group_on_fight_num)
    public TextView mFightNum;


    public GroupOnListViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
