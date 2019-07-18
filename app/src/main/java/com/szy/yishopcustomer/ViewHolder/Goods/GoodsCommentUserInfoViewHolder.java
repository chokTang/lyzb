package com.szy.yishopcustomer.ViewHolder.Goods;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by liwei on 16/12/14.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class GoodsCommentUserInfoViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.comment_headimg)
    public CircleImageView mCommentHeadimg;
    @BindView(R.id.comment_nickname)
    public TextView mCommentNickName;
    @BindView(R.id.comment_user_rank)
    public ImageView mCommentUserLevel;

    public GoodsCommentUserInfoViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}