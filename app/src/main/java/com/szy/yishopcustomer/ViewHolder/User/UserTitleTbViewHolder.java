package com.szy.yishopcustomer.ViewHolder.User;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 用户中心上下图标 title  我的工具箱下面的
 *
 */
public class UserTitleTbViewHolder extends RecyclerView.ViewHolder {
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

    @BindView(R.id.view_title_left)
    public View view_title_left;
    @BindView(R.id.view_title_right)
    public View view_title_right;

    @BindView(R.id.ll_title_tb_bg)
    public LinearLayout ll_title_tb_bg;

    public UserTitleTbViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
