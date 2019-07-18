package com.szy.yishopcustomer.ViewHolder.Index;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.lyzb.jbx.R;
import com.viewpagerindicator.CirclePageIndicator;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zongren on 16/6/12.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class GoodsPromotionViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.fragment_index_goods_promotion_viewPager)
    public ViewPager viewPager;
    @BindView(R.id.fragment_index_goods_promotion_iconPageIndicator)
    public CirclePageIndicator pageIndicator;

    @BindView(R.id.fragment_index_goods_promotion_recyclerView)
    public RecyclerView recyclerView;

    public GoodsPromotionViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}