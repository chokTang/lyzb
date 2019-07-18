package com.szy.yishopcustomer.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liuzhifeng on 16/7/25.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class DepositListViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.item_fragment_status_text_view)
    public TextView statuse;
    @BindView(R.id.item_fragment_money_text_view)
    public TextView money;
    @BindView(R.id.item_fragment_name_text_view)
    public TextView accoutname;
    @BindView(R.id.item_fragment_time_text_view)
    public TextView time;
    @BindView(R.id.item_fragment_delete_text_view)
    public TextView detele;
    @BindView(R.id.refuse_tip)
    public TextView refuseTip;

    public DepositListViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}