package com.szy.yishopcustomer.ViewHolder.User;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Smart on 18/2/6.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class UserMyAssetsViewHolder extends RecyclerView.ViewHolder {
    @Nullable
    @BindView(R.id.linearlayoutPaymentCode)
    public View linearlayoutPaymentCode;

    @BindView(R.id.linearlayoutRedPacket)
    public View linearlayoutRedPacket;
    @BindView(R.id.textViewUserBalance)
    public TextView textViewUserBalance;

    @BindView(R.id.ll_bg_my_assets)
    public LinearLayout ll_bg_my_assets;

    @BindView(R.id.linearlayoutUserIntegral)
    public View linearlayoutUserIntegral;
    @BindView(R.id.textViewUserIntegral)
    public TextView textViewUserIntegral;

    @BindView(R.id.linearlayoutUserBalance)
    public View linearlayoutUserBalance;
    @BindView(R.id.textViewRedPacket)
    public TextView textViewRedPacket;

    public UserMyAssetsViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
