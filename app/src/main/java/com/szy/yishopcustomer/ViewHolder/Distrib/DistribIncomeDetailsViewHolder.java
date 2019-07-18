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

public class DistribIncomeDetailsViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.user_photo)
    public CircleImageView userPhoto;
    @BindView(R.id.remark)
    public TextView remark;
    @BindView(R.id.textView_use_name)
    public TextView userName;
    @BindView(R.id.textView_order_sn)
    public TextView orderSn;
    @BindView(R.id.dis_money)
    public TextView disMoney;
    @BindView(R.id.add_time)
    public TextView addTime;

    public DistribIncomeDetailsViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
