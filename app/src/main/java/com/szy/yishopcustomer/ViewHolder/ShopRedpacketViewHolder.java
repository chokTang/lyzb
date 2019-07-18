package com.szy.yishopcustomer.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.Button;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Smart on 2017/10/16.
 */

public class ShopRedpacketViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.textViewBonusPrice)
    public TextView textViewBonusPrice;
    @BindView(R.id.textViewBonusTip)
    public TextView textViewBonusTip;
    @BindView(R.id.textViewBonusTime)
    public TextView textViewBonusTime;
    @BindView(R.id.textViewUsed)
    public TextView textViewUsed;

    @BindView(R.id.imageViewReceive)
    public View imageViewReceive;

    public ShopRedpacketViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
