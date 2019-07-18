package com.szy.yishopcustomer.ViewHolder.User;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 宗仁 on 16/7/27.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class UserTitleViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.fragment_user_title_titleTextView)
    public TextView titleTextView;
    @BindView(R.id.fragment_user_title_logoff)
    public TextView titleLogOff;
    @BindView(R.id.fragment_user_title_subTitleTextView)
    public TextView subTitleTextView;
    @BindView(R.id.fragment_user_title_iconImageView)
    public ImageView iconImageView;
    @BindView(R.id.fragment_user_title_arrowImageView)
    public ImageView arrowImageView;

    @BindView(R.id.ll_bg_title)
    public LinearLayout ll_bg_title;

    @BindView(R.id.rl_bg_title)
    public RelativeLayout rl_bg_title;

    public UserTitleViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
