package com.szy.yishopcustomer.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;

import com.szy.yishopcustomer.Constant.Macro;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ViewHolder.GoodsListViewHolder;

/**
 * Created by 宗仁 on 16/8/22.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ShopGoodsListAdapter extends GoodsListAdapter {
    public ShopGoodsListAdapter(Context context) {
        super(context);
        this.style = Macro.STYLE_LIST;
    }

    public RecyclerView.ViewHolder createGoodsViewHolder(ViewGroup parent, int layout) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new GoodsListViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_GOODS_LIST:
                return createGoodsViewHolder(parent, R.layout.fragment_shop_goods_list_item_list);
            case VIEW_TYPE_GOODS_GRID:
                return createGoodsViewHolder(parent, R.layout.fragment_shop_goods_list_item_grid);
        }
        return super.onCreateViewHolder(parent, viewType);
    }
}
