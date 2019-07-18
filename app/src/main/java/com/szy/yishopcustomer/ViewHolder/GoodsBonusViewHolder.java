package com.szy.yishopcustomer.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.Button;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liwei on 16/5/5.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class GoodsBonusViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.goods_bonus_money)
    public TextView mBonusMoney;
    @BindView(R.id.goods_bonus_name)
    public TextView mBonusName;
    /*    @BindView(R.id.bonus_type)
        public TextView mBonusType;*/
    @BindView(R.id.goods_bonus_end_date)
    public TextView mBonusEndDate;
    @BindView(R.id.goods_bonus_take_button)
    public Button mTakeBonusButton;

    public GoodsBonusViewHolder(View view) {
        super(view);

        ButterKnife.bind(this, view);
    }
}
