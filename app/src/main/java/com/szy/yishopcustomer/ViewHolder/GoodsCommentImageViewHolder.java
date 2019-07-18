package com.szy.yishopcustomer.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liwei on 2016/8/30.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class GoodsCommentImageViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.fragment_goods_comment_image)
    public ImageView mGoodsCommentImage;

    public GoodsCommentImageViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, itemView);
        itemView.setTag(this);
    }
}
