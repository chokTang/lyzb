package com.szy.yishopcustomer.ViewHolder.Order;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lw on 2016/6/12.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司
 */
public class OrderListGoodsListViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.fragment_order_list_goods_layout)
    public LinearLayout mLayout;

    public OrderListGoodsListViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
