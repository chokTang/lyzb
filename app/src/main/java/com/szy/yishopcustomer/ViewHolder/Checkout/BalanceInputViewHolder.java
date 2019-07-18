package com.szy.yishopcustomer.ViewHolder.Checkout;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szy.common.View.CommonEditText;
import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 宗仁 on 16/7/21.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class BalanceInputViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.fragment_checkout_balance_input_balanceEditText)
    public CommonEditText balanceEditText;
    @BindView(R.id.fragment_checkout_balance_input_balanceTextView)
    public TextView balanceTextView;
    @BindView(R.id.fragment_checkout_payment_label_moneyTextView)
    public TextView moneyTextView;
    @BindView(R.id.fragment_checkout_payment_label_layout)
    public LinearLayout paymentLaybelLayout;

    public BalanceInputViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
