package com.szy.yishopcustomer.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 宗仁 on 16/7/23.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class BindAccountViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.item_fragment_bind_delete)
    public TextView delete;
    @BindView(R.id.item_fragment_bind_account)
    public TextView account;
    @BindView(R.id.item_fragment_bind_name)
    public TextView name;
    @BindView(R.id.item_fragment_bind_bank)
    public TextView bank;
    @BindView(R.id.item_fragment_bind_image)
    public ImageView image;

    public BindAccountViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
