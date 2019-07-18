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
public class OrderInfoViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.fragment_order_detail_pay_sn_textView)
    public TextView mPaySnLabel;
    @BindView(R.id.fragment_order_detail_order_sn_textView)
    public TextView mOrderSnLabel;
    @BindView(R.id.fragment_order_detail_add_time_textView)
    public TextView mAddtimeLabel;
    @BindView(R.id.fragment_order_detail_pay_time_textView)
    public TextView mPaytimeLabel;
    @BindView(R.id.fragment_order_detail_shipping_time_textView)
    public TextView mShippingtimeLabel;
    @BindView(R.id.fragment_order_detail_confirm_time_textView)
    public TextView mConfirmtimeLabel;
    @BindView(R.id.fragment_order_detail_pay_type_textView)
    public TextView mPayTypeLabel;
    @BindView(R.id.fragment_order_detail_invoice_textView)
    public TextView mInvoiceLabel;
    @BindView(R.id.fragment_order_detail_postscript_textView)
    public TextView mPostScriptLabel;
    @BindView(R.id.fragment_order_detail_delivery_time_textView)
    public TextView mDeliveryTimeTextView;
    @BindView(R.id.fragment_order_detail_copy)
    public TextView mCopy;

    @BindView(R.id.fragment_order_detail_voucher)
    public TextView fragment_order_detail_voucher;
    @BindView(R.id.fragment_order_detail_deliveryName)
    public TextView fragment_order_detail_deliveryName;

    public OrderInfoViewHolder(View view) {
        super(view);

        ButterKnife.bind(this, view);
    }
}