package com.szy.yishopcustomer.ViewHolder;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.*;

import com.lyzb.jbx.R;
import com.szy.yishopcustomer.View.CirclePageIndicator;
import com.szy.yishopcustomer.View.HeadViewPager;
import com.szy.yishopcustomer.View.HeadWrapContentViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 李威 on 16/8/9.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class GroupBuyBannerViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.fragment_group_buy_banner_viewPager)
    public HeadWrapContentViewPager viewPager;
    @BindView(R.id.fragment_group_buy_banner_pageIndicator)
    public CirclePageIndicator pageIndicator;

    public GroupBuyBannerViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
