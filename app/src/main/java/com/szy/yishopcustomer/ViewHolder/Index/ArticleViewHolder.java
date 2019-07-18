package com.szy.yishopcustomer.ViewHolder.Index;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyzb.jbx.R;
import com.szy.yishopcustomer.View.ArticleViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zongren on 16/6/2.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ArticleViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.fragment_index_article_textview)
    public TextView articleTextView;
    @BindView(R.id.fragment_index_article_articleViewPager)
    public ArticleViewPager verticalBannerView;
    @BindView(R.id.fragment_index_article_title_imageView)
    public ImageView imageview;
    @BindView(R.id.fragment_index_article_viewPagerCoverView)
    public View coverView;

    public ArticleViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
