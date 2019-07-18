package com.szy.yishopcustomer.ViewHolder.Index;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyzb.jbx.R;
import com.viewpagerindicator.CirclePageIndicator;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Smart.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class GoodsPromotionItemViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.fragment_index_goods_nameTextView)
    public TextView goodsNameTextView;
    @BindView(R.id.fragment_index_goods_priceTextView)
    public TextView goodsPriceTextView;
    @BindView(R.id.fragment_index_goods_thumbImageView)
    public ImageView goodsImageView;
    @BindView(R.id.fragment_index_goods_tag)
    public ImageView goodsImageTag;


    public GoodsPromotionItemViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}