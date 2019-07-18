package com.szy.yishopcustomer.ViewHolder.Index;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lyzb.jbx.R;
import com.szy.yishopcustomer.View.CustomViewPager;
import com.viewpagerindicator.CirclePageIndicator;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zongren on 16/5/30.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class NavigationViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.fragment_index_goods_navigation_viewPager)
    public CustomViewPager viewPager;
    @BindView(R.id.fragment_index_goods_navigation_iconPageIndicator)
    public CirclePageIndicator pageIndicator;

    @BindView(R.id.relativeLayout_root)
    public View relativeLayout_root;

    public NavigationViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
