package com.szy.yishopcustomer.ViewHolder.ShopInfo;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 宗仁 on 2017/1/10.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class ShopInfoRateViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.fragment_shop_info_item_descriptionTextView)
    public TextView descriptionTextView;
    @BindView(R.id.fragment_shop_info_item_expressTextView)
    public TextView expressTextView;
    @BindView(R.id.fragment_shop_info_item_serviceTextView)
    public TextView serviceTextView;

    public ShopInfoRateViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
