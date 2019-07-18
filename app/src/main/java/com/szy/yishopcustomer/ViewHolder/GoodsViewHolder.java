package com.szy.yishopcustomer.ViewHolder;

import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import com.szy.common.ViewHolder.CommonViewHolder;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    @BindView(R.id.fragment_index_goods_tag)
    public ImageView goodsImageTag;

    @BindView(R.id.tv_index_goods_ingot)
    public TextView indexGoodsIngot;

    @BindView(R.id.tv_index_goods_prices)
    public TextView indexGoodsPrices;

    public GoodsViewHolder() {
        super();
    }

    public GoodsViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
        int picSize = Utils.getWindowWidth(view.getContext())/3 - Utils.dpToPx(view.getContext(),10);
        goodsImageView.getLayoutParams().height = picSize;
        goodsImageView.getLayoutParams().width = picSize;
    }
}
