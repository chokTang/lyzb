package com.szy.yishopcustomer.ViewHolder.Goods;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liwei on 16/12/14.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class GoodsCommentDescViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.comment_content)
    public TextView mCommentContent;
    @BindView(R.id.fragment_goods_comment_image_RecyclerView)
    public RecyclerView mRecyclerView;
    @BindView(R.id.comment_time)
    public TextView mCommentTime;
    @BindView(R.id.comment_goods_spec)
    public TextView mCommentGoodsSpec;
    @Nullable
    @BindView(R.id.linearlayout_root)
    public View linearlayout_root;

    public GoodsCommentDescViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}