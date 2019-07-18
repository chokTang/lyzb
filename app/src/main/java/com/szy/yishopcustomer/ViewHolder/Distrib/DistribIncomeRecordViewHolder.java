package com.szy.yishopcustomer.ViewHolder.Distrib;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liwei on 2017/07/19.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class DistribIncomeRecordViewHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.week)
    public TextView week;
    @BindView(R.id.date)
    public TextView date;
    @BindView(R.id.money)
    public TextView money;
    @BindView(R.id.add_time)
    public TextView add_time;

    public DistribIncomeRecordViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
