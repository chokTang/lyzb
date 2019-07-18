package com.szy.yishopcustomer.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.TextView;

import com.szy.common.View.SquareImageView;
import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by buqingqiang on 2016/7/8.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ShopGoodsViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.item_shop_goods_goods_iamge_imageView)
    public SquareImageView goodsImageImageView;
    @BindView(R.id.item_shop_goods_goods_name_textView)
    public TextView goodsNameTextView;
    @BindView(R.id.item_shop_goods_goods_price_textView)
    public TextView goodsPriceTextView;

    public ShopGoodsViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
