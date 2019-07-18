package com.szy.yishopcustomer.ViewHolder.User;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 宗仁 on 16/7/27.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class UserButtonViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.fragment_user_button_badge_numberTextView)
    public TextView numberTextView;
    @BindView(R.id.fragment_user_button_iconImageView)
    public ImageView iconImageView;
    @BindView(R.id.fragment_user_button_titleTextView)
    public TextView titleTextView;
    @BindView(R.id.rl_user_button_bg)
    public RelativeLayout rl_user_button_bg;
    @BindView(R.id.view_left)
    public View view_left;
    @BindView(R.id.view_right)
    public View view_right;

    public UserButtonViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
