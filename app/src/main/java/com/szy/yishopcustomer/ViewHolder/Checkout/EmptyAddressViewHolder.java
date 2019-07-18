package com.szy.yishopcustomer.ViewHolder.Checkout;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.LinearLayout;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liwei on 2016/11/29.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class EmptyAddressViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.fragment_checkout_empty_address_layout)
    public LinearLayout emptyAddressLayout;

    public EmptyAddressViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
