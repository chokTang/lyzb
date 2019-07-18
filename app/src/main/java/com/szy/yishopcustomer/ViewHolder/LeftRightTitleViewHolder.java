package com.szy.yishopcustomer.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liwei on 16/5/5.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class LeftRightTitleViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_left_title)
    public TextView tv_left_title;
    @BindView(R.id.tv_right_title)
    public TextView tv_right_title;


    public LeftRightTitleViewHolder(View view) {
        super(view);

        ButterKnife.bind(this, view);
    }
}