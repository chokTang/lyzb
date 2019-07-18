package com.szy.yishopcustomer.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.TextView;

import com.lyzb.jbx.R;
import com.szy.yishopcustomer.View.CircleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liwei on 2017/7/14.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class RecommendListViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.imageView_user_photo)
    public CircleImageView imageView_user_photo;
    @BindView(R.id.textView_user_name)
    public TextView textView_user_name;
    @BindView(R.id.textView_reg_time)
    public TextView textView_reg_time;

    public RecommendListViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
