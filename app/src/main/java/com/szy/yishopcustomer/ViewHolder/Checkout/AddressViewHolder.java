package com.szy.yishopcustomer.ViewHolder.Checkout;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 宗仁 on 16/7/20.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class AddressViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.fragment_checkout_address_layout)
    public RelativeLayout addressLayout;
    @BindView(R.id.fragment_checkout_address_consigneeTextView)
    public TextView consigneeTextView;
    @BindView(R.id.fragment_checkout_address_phoneTextView)
    public TextView phoneTextView;
    @BindView(R.id.fragment_checkout_address_consigneeAddressTextView)
    public TextView consigneeAddressTextView;
    @BindView(R.id.identity_verification_layout)
    public LinearLayout realNameLayout;
    @BindView(R.id.fragment_checkout_address_id_textView)
    public TextView idTextView;
    @BindView(R.id.fragment_checkout_address_name_textView)
    public TextView nameTextView;

    public AddressViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
