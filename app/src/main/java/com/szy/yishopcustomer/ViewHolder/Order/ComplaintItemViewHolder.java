package com.szy.yishopcustomer.ViewHolder.Order;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 宗仁 on 16/7/23.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ComplaintItemViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.layout_shop_item)
    public LinearLayout shopNameLayout;
    @BindView(R.id.item_shop_name)
    public TextView shopName;
    @BindView(R.id.item_status)
    public TextView status;

    @BindView(R.id.complaint_sn)
    public TextView complaintSn;
    @BindView(R.id.order_sn)
    public TextView orderSn;
    @BindView(R.id.user_name)
    public TextView userName;
    @BindView(R.id.complaint_reason)
    public TextView complaintReason;
    @BindView(R.id.add_time)
    public TextView addTime;
    @BindView(R.id.item_list_bottom_button)
    public TextView viewDetail;

    public ComplaintItemViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
