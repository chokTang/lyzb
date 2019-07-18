package com.szy.yishopcustomer.ViewHolder.Index;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.TextView;

import com.lyzb.jbx.R;
import com.szy.yishopcustomer.View.BannerScroller;
import com.szy.yishopcustomer.View.CirclePageIndicator;
import com.szy.yishopcustomer.View.HeadViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zongren on 16/5/30.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class AdBannerViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.fragment_index_banner_viewPager)
    public HeadViewPager viewPager;
    @BindView(R.id.fragment_index_banner_pageIndicator)
    public CirclePageIndicator pageIndicator;

    @BindView(R.id.tv_banner0_my_ingots)
    public TextView tvMyIngots;

    public AdBannerViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
