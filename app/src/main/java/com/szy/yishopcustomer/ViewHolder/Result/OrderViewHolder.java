package com.szy.yishopcustomer.ViewHolder.Result;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 宗仁 on 16/8/1.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class OrderViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.fragment_result_order_moneyTextView)
    public TextView moneyTextView;
    @BindView(R.id.fragment_result_order_orderTextView)
    public TextView orderTextView;

    public OrderViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
