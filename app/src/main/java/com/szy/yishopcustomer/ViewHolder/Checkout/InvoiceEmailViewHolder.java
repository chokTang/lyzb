package com.szy.yishopcustomer.ViewHolder.Checkout;

import android.support.v7.widget.RecyclerView;
import android.view.*;

import com.szy.common.View.CommonEditText;
import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 宗仁 on 16/7/22.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class InvoiceEmailViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.et_inv_email)
    public CommonEditText et_inv_email;

    public InvoiceEmailViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

}
