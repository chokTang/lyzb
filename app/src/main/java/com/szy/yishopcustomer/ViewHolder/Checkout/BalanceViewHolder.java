package com.szy.yishopcustomer.ViewHolder.Checkout;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 宗仁 on 16/7/21.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class BalanceViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.fragment_checkout_balance_balanceTextView)
    public TextView balanceTextView;
    @BindView(R.id.fragment_checkout_balance_switchImageView)
    public ImageView switchImageView;
    @BindView(R.id.fragment_checkout_balance_userBalance)
    public TextView balanceUsed;

    @BindView(R.id.linearlayout_root)
    public View linearlayout_root;

    public BalanceViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
