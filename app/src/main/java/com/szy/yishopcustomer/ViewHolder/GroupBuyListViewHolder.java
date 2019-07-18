package com.szy.yishopcustomer.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyzb.jbx.R;
import com.szy.yishopcustomer.View.FloatTextProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.iwgang.countdownview.CountdownView;

/**
 * Created by liwei on 2016/11/21.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司
 */
public class GroupBuyListViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.item_group_buy_goods_thumbImageView)
    public ImageView mGoodsThumb;
    @BindView(R.id.item_group_buy_goods_nameTextView)
    public TextView mGoodsName;
    @BindView(R.id.item_group_buy_goods_actPriceTextView)
    public TextView mGoodsActPrice;
    @BindView(R.id.item_group_buy_goods_price)
    public TextView mGoodsPrice;
    @BindView(R.id.item_goods_buy_sales)
    public TextView mGoodsSales;
    @BindView(R.id.item_group_buy_button)
    public TextView mGoodsButton;
    @BindView(R.id.item_group_buy_sold_out)
    public ImageView mSoldOut;
    @BindView(R.id.floatTextProgressBar)
    public FloatTextProgressBar mFloatTextProgressBar;

    @BindView(R.id.textView_countdown_prefix)
    public TextView textView_countdown_prefix;
    @BindView(R.id.linearlayout_countdown)
    public View linearlayout_countdown;
    @BindView(R.id.linearlayout_last_line)
    public View linearlayout_last_line;

    public GroupBuyListViewHolder(View view) {
        super(view);

        ButterKnife.bind(this, view);
    }
}
