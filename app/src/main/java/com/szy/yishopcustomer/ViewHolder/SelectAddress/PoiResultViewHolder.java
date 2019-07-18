package com.szy.yishopcustomer.ViewHolder.SelectAddress;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liwei on 17/3/10.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class PoiResultViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.fragment_select_address_poiTitle)
    public TextView titleTextView;
    @BindView(R.id.fragment_select_address_poiContent)
    public TextView contentTextView;

    public PoiResultViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
