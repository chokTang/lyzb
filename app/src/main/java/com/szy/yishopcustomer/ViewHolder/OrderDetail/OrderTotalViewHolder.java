package com.szy.yishopcustomer.ViewHolder.OrderDetail;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liwei on 16/12/06.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class OrderTotalViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.product_amount)
    public TextView mProductAmount;
    @BindView(R.id.shipping_fee)
    public TextView mShippingFee;
    @BindView(R.id.shipping_fee_label)
    public TextView mShippingFeeLabel;
    @BindView(R.id.bonus)
    public TextView mBonus;
    @BindView(R.id.benefit)
    public TextView mBenefit;
    @BindView(R.id.balance)
    public TextView mBalance;
    @BindView(R.id.order_amount)
    public TextView mOrderAmount;
    @BindView(R.id.money_paid_online_label)
    public TextView mMoneyPaidLabel;
    @BindView(R.id.money_paid_online)
    public TextView mMoneyPaid;
    @BindView(R.id.cod_cash_more_label)
    public TextView mCodCashMoreLabel;
    @BindView(R.id.cod_cash_more)
    public TextView mCodCashMore;
    @BindView(R.id.order_money_label)
    public TextView mOrderMoneyLabel;
    @BindView(R.id.order_money)
    public TextView mOrderMoney;
    @BindView(R.id.bonus_label)
    public TextView mBonusLabel;
    @BindView(R.id.deductible_label)
    public TextView mDeductibleLabel;
    @BindView(R.id.deductible)
    public TextView mDeductible;

    @BindView(R.id.relativeLayout_order_amount)
    public View relativeLayout_order_amount;

    public OrderTotalViewHolder(View view) {
        super(view);

        ButterKnife.bind(this, view);
    }
}