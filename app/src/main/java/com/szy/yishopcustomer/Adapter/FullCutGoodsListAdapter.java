package com.szy.yishopcustomer.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;

import com.szy.yishopcustomer.Constant.Macro;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.GoodsModel;
import com.szy.yishopcustomer.ViewHolder.GoodsListViewHolder;

/**
 * Created by Smart on 18/1/3.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class FullCutGoodsListAdapter extends GoodsListAdapter {
    public FullCutGoodsListAdapter(Context context) {
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
                return createGoodsViewHolder(parent, R.layout.fragment_fullcut_goods_list_item_list);
            case VIEW_TYPE_GOODS_GRID:
                return createGoodsViewHolder(parent, R.layout.fragment_fullcut_goods_list_item_grid);
        }
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void bindGoodsViewHolder(GoodsListViewHolder viewHolder, GoodsModel model, int position) {
        model.goods_number = 99999;
        super.bindGoodsViewHolder(viewHolder, model, position);

        viewHolder.saleTextView.setVisibility(View.VISIBLE);
        viewHolder.saleTextView.setText(model.total_sale_count + "人抢购");
    }
}
