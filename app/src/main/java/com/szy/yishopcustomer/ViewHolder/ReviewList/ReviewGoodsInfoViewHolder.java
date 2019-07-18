package com.szy.yishopcustomer.ViewHolder.ReviewList;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liwei on 16/12/20.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ReviewGoodsInfoViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.item_review_goods_img)
    public ImageView reviewGoodsImg;

    @BindView(R.id.item_review_goods_name)
    public TextView reviewGoodsName;
    @BindView(R.id.item_review_spec_info)
    public TextView reviewSpecInfo;
    @BindView(R.id.fragment_goods_comment_image_RecyclerView)
    public RecyclerView mRecyclerView;

    @BindView(R.id.comment_content)
    public TextView mCommentContent;
    @BindView(R.id.comment_time)
    public TextView mCommentTime;

    @BindView(R.id.relativeLayout_comment_content)
    public View relativeLayout_comment_content;

    public ReviewGoodsInfoViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
