package com.szy.yishopcustomer.ViewHolder.Distrib;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liwei on 2017/7/19.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class DistribHelpViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.title)
    public TextView title;

    public DistribHelpViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
