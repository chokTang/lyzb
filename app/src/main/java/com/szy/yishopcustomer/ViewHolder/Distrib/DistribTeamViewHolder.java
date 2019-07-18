package com.szy.yishopcustomer.ViewHolder.Distrib;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.TextView;

import com.lyzb.jbx.R;
import com.szy.yishopcustomer.View.CircleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liwei on 2017/7/19.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class DistribTeamViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.headimg)
    public CircleImageView headImg;
    @BindView(R.id.user_name)
    public TextView user_name;
    @BindView(R.id.total_user_count)
    public TextView total_user_count;
    @BindView(R.id.reg_time_format)
    public TextView reg_time_format;
    @BindView(R.id.dis_total_money)
    public TextView dis_total_money;

    public DistribTeamViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
