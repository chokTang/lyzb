package com.szy.yishopcustomer.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Smart on 2017/7/6.
 */

public class CardUsageViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.textView_card_number)
    public TextView textView_card_number;
    @BindView(R.id.textView_card_amount)
    public TextView textView_card_amount;
    @BindView(R.id.textView_card_type)
    public TextView textView_card_type;
    @BindView(R.id.textView_card_order_number)
    public TextView textView_card_order_number;
    @BindView(R.id.textView_card_use_time)
    public TextView textView_card_use_time;

    public CardUsageViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
