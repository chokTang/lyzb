package com.szy.yishopcustomer.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liwei on 2017/5/5.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司
 */
public class RechargeRecordViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.item_fragment_recharge_record_payment_name)
    public TextView mPaymentName;
    @BindView(R.id.item_fragment_recharge_record_status)
    public TextView mStatus;
    @BindView(R.id.item_fragment_recharge_record_amount)
    public TextView mAmount;
    @BindView(R.id.item_fragment_recharge_record_add_time)
    public TextView mAddTime;

    public RechargeRecordViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
