package com.szy.yishopcustomer.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by buqingqiang on 2016/8/29.
 */
public class ReviewShareOrderImgViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.fragment_review_share_order_img_ImageView)
    public ImageView mShareOrderImg;
    @BindView(R.id.fragment_review_share_order_img_delete_ImageView)
    public ImageView mShareOrderImgDelete;
    @BindView(R.id.fragment_review_share_order_img_relativeLayout)
    public RelativeLayout mAll;

    public ReviewShareOrderImgViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, itemView);
        itemView.setTag(this);
    }
}
