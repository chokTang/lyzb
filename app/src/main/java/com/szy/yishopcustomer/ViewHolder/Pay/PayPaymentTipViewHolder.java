package com.szy.yishopcustomer.ViewHolder.Pay;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 宗仁 on 16/8/2.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class PayPaymentTipViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.left_money)
    public TextView leftMoney;

    public PayPaymentTipViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
