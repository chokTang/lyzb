package com.szy.yishopcustomer.ViewHolder.Index;

import android.support.v7.widget.RecyclerView;
import android.view.*;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zongren on 16/6/24.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class GoodsPromotionWrapperViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.fragment_index_goods_promotion_firstGoods)
    public View firstGoodsView;
    @BindView(R.id.fragment_index_goods_promotion_secondGoods)
    public View secondGoodsView;
    @BindView(R.id.fragment_index_goods_promotion_thirdGoods)
    public View thirdGoodsView;

    public GoodsPromotionWrapperViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
