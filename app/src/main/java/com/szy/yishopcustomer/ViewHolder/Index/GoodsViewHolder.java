package com.szy.yishopcustomer.ViewHolder.Index;

import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import com.szy.common.ViewHolder.CommonViewHolder;
import com.lyzb.jbx.R;

import butterknife.BindView;

/**
 * Created by zongren on 16/5/19.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class GoodsViewHolder extends CommonViewHolder {
    @BindView(R.id.fragment_index_goods_nameTextView)
    public TextView goodsNameTextView;
    @BindView(R.id.fragment_index_goods_priceTextView)
    public TextView goodsPriceTextView;
    @BindView(R.id.fragment_index_goods_thumbImageView)
    public ImageView goodsImageView;

    public GoodsViewHolder() {
        super();
    }

    public GoodsViewHolder(View view) {
        super(view);
    }
}
