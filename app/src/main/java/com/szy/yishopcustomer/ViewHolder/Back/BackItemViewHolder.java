package com.szy.yishopcustomer.ViewHolder.Back;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liwei on 2017/3/14.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class BackItemViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.item_shop_name)
    public TextView mBackShopName;
    @BindView(R.id.item_status)
    public TextView mBackStatus;
    @BindView(R.id.item_back_goods_imageView)
    public ImageView mBackGoodsThumb;
    @BindView(R.id.item_back_goods_name_textView)
    public TextView mBackGoodsName;
   @BindView(R.id.item_back_amount)
    public TextView mBackAmount;
    @BindView(R.id.item_back_goods_attr_textView)
    public TextView mAttr;
    @BindView(R.id.item_list_bottom_button)
    public TextView mDetail;

    public BackItemViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, itemView);
    }
}
