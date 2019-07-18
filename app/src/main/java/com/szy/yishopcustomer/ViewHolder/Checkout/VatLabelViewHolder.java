package com.szy.yishopcustomer.ViewHolder.Checkout;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.TextView;

import com.szy.common.View.CommonEditText;
import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 宗仁 on 16/7/22.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class VatLabelViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.fragment_invoice_info_vat_label_starTextView)
    public TextView starTextView;
    @BindView(R.id.fragment_invoice_info_vat_label_textView)
    public TextView textView;
    @BindView(R.id.fragment_invoice_info_vat_label_editText)
    public CommonEditText editText;

    public VatLabelViewHolder(View view) {
        super(view);

        ButterKnife.bind(this, view);
    }
}
