package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;

import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Goods.RankPriceModel;
import com.szy.yishopcustomer.ViewHolder.Goods.GoodsRankPriceViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 李威 on 16/11/1.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class GoodsRankPriceAdapter extends RecyclerView.Adapter {

    private static final int VIEW_TYPE_ITEM = 0;

    public List<Object> data;
    public View.OnClickListener onClickListener;

    public GoodsRankPriceAdapter() {
        this.data = new ArrayList<>();
    }

    public GoodsRankPriceAdapter(List<Object> data) {
        this.data = data;
    }

    public RecyclerView.ViewHolder createItemViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_goods_rank_price, parent, false);
        return new GoodsRankPriceViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_ITEM:
                return createItemViewHolder(parent);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case VIEW_TYPE_ITEM:
                bindItemViewHolder((GoodsRankPriceViewHolder) holder, position);
                break;
        }
    }

    public int getItemViewType(int position) {
        Object object = data.get(position);
        if (object instanceof RankPriceModel) {
            return VIEW_TYPE_ITEM;
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    private void bindItemViewHolder(GoodsRankPriceViewHolder holder, int position) {
        RankPriceModel dataModel = (RankPriceModel) data.get(position);

        holder.mRankName.setText(dataModel.rank_name);
        holder.mRankPrice.setText(dataModel.rank_price_format);
    }
}
