package com.szy.yishopcustomer.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by buqingqiang on 2016/6/3.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class EvaluateGoodsViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.item_evaluate_goods_image)
    public ImageView evaluateGoodsImage;
    @BindView(R.id.item_evaluate_goods_name)
    public TextView evaluateGoodsName;
    @BindView(R.id.item_evaluate_goods_spec)
    public TextView evaluateGoodsSpec;
    @BindView(R.id.item_evaluate_goods_button)
    public TextView evaluateGoodsButton;

    public EvaluateGoodsViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
