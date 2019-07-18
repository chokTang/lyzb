package com.szy.yishopcustomer.ViewHolder.ReviewList;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liwei on 16/12/20.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ReviewTitleViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.item_review_title_value)
    public TextView reviewRankValue;
    @BindView(R.id.item_review_date)
    public TextView reviewDate;

    @BindView(R.id.item_review_star_one_img)
    public ImageView reviewStarOneImg;
    @BindView(R.id.item_review_star_two_img)
    public ImageView reviewStarTwoImg;
    @BindView(R.id.item_review_star_three_img)
    public ImageView reviewStarThreeImg;
    @BindView(R.id.item_review_star_four_img)
    public ImageView reviewStarFourImg;
    @BindView(R.id.item_review_star_five_img)
    public ImageView reviewStarFiveImg;

    public ReviewTitleViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
