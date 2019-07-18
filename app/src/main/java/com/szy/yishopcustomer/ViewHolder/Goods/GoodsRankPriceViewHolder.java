package com.szy.yishopcustomer.ViewHolder.Goods;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liwei on 17/6/3.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class GoodsRankPriceViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.rank_name)
    public TextView mRankName;
    @BindView(R.id.rank_price_format)
    public TextView mRankPrice;

    public GoodsRankPriceViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setTag(this);
    }
}
