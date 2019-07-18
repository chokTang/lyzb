package com.szy.yishopcustomer.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.iwgang.countdownview.CountdownView;

/**
 * Created by liwei on 16/5/5.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class GroupBuyViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.group_buy_goods_name)
    public TextView mGroupBuyGoodsName;
    @BindView(R.id.group_buy_goods_img)
    public ImageView mGroupBuyGoodsImage;
    @BindView(R.id.group_buy_goods_price)
    public TextView mGroupBuyMinPrice;
    @BindView(R.id.group_buy_goods_count)
    public TextView mGroupBuySalesNumber;
    @BindView(R.id.group_buy_now)
    public TextView mGroupBuyButton;

    @BindView(R.id.cv_countdownViewGroupBuy)
    public CountdownView mCvCountdownViewGroupBuy;
    @BindView(R.id.group_buy_tip)
    public TextView mGroupBuyTip;

    @BindView(R.id.relativeLayout_price)
    public View relativeLayout_price;

    public GroupBuyViewHolder(View view) {
        super(view);

        ButterKnife.bind(this, view);
    }
}