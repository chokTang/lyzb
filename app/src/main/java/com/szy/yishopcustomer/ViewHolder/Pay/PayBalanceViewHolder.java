package com.szy.yishopcustomer.ViewHolder.Pay;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 宗仁 on 16/8/2.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class PayBalanceViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.fragment_rela_user_balance)
    public RelativeLayout mRelativeLayout_Balance;

    @BindView(R.id.fragment_pay_balance_checkboxImageView)
    public ImageView checkboxImageView;

    @BindView(R.id.fragment_pay_balance_editText)
    public EditText editText;
    @BindView(R.id.fragment_pay_balance_labelTextView)
    public TextView payBalanceTextView;
    @BindView(R.id.fragment_pay_balance_unitTextView)
    public TextView unitTextView;
    @BindView(R.id.fragment_pay_balance_minusTextView)
    public TextView minusTextView;

    @BindView(R.id.fragment_pay_balance_moneyTextView)
    public TextView moneyTextView;
    @BindView(R.id.fragment_pay_balance_availableBalanceTextView)
    public TextView availableBalanceTextView;
    @BindView(R.id.fragment_pay_balance_currentBalanceLabelTextView)
    public TextView balanceLabelTextView;

    public PayBalanceViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
