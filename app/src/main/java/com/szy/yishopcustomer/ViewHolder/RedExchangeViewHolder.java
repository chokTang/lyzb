package com.szy.yishopcustomer.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Smart on 2017/5/16.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class RedExchangeViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.textView_red_amount)
    public TextView textView_red_amount;
    @BindView(R.id.textView_shop_name)
    public TextView textView_shop_name;
    @BindView(R.id.textView_limit)
    public TextView textView_limit;
    @BindView(R.id.textView_number)
    public TextView textView_number;
    @BindView(R.id.textView_effective_time)
    public TextView textView_effective_time;
    @BindView(R.id.textView_exchange_time)
    public TextView textView_exchange_time;
    @BindView(R.id.textView_integral_required)
    public TextView textView_integral_required;
    @BindView(R.id.textView_exchange_figures)
    public TextView textView_exchange_figures;
    @BindView(R.id.textView_use_limit)
    public TextView textView_use_limit;

    @BindView(R.id.textView_exchange)
    public TextView textView_exchange;

    public RedExchangeViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
