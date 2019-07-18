package com.szy.yishopcustomer.ViewHolder.Distrib;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liwei on 2017/9/27.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class DistribGoodsItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.item_distrib_goodsImage)
    public ImageView goodsImage;
    @BindView(R.id.item_distrib_goodsName)
    public TextView goodsName;
    @BindView(R.id.item_distrib_goodsPrice)
    public TextView goodsPrice;
    @BindView(R.id.expand_text_view)
    public ExpandableTextView profit;
    @BindView(R.id.fragment_distrib_onSaleButton)
    public Button onSaleButton;

    public DistribGoodsItemViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
