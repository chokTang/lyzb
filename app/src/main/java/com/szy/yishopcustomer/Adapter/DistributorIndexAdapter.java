package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;

import com.szy.common.Util.ImageLoader;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.AppIndex.GoodsItemModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.DividerViewHolder;
import com.szy.yishopcustomer.ViewHolder.Index.GoodsColumnItemViewHolder;
import com.szy.yishopcustomer.ViewModel.EmptyItemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liwei on 2017/9/26.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class DistributorIndexAdapter extends RecyclerView.Adapter {

    private static final int VIEW_TYPE_GOODS = 0;
    private static final int VIEW_EMPTY = 1;

    public int itemWidth;
    public int itemHeight;
    public List<Object> data;
    public View.OnClickListener onClickListener;


    public DistributorIndexAdapter(List<Object> data) {
        this.data = data;
    }
    public DistributorIndexAdapter() {
        this.data = new ArrayList<>();
    }

    public RecyclerView.ViewHolder createDividerViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_goods_list_item_empty, parent, false);
        return new DividerViewHolder(view);
    }

    public RecyclerView.ViewHolder createListViewHolder(ViewGroup parent) {
        ViewGroup view = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_index_goods, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = itemHeight;
        layoutParams.width = itemWidth;
        return new GoodsColumnItemViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_GOODS:
                return createListViewHolder(parent);
            case VIEW_EMPTY:
                return createDividerViewHolder(parent);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case VIEW_TYPE_GOODS:
                bindListViewHolder((GoodsColumnItemViewHolder) holder, position);
                break;
            case VIEW_EMPTY:
                bindDividerViewHolder((DividerViewHolder) holder, position);
        }
    }

    public int getItemViewType(int position) {
        Object object = data.get(position);
        if (object instanceof GoodsItemModel) {
            return VIEW_TYPE_GOODS;
        } else if (object instanceof EmptyItemModel) {
            return VIEW_EMPTY;
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private void bindDividerViewHolder(DividerViewHolder holder, int position) {
    }

    private void bindListViewHolder(GoodsColumnItemViewHolder viewHolder, int position) {
        GoodsItemModel item = (GoodsItemModel)data.get(position);
        if (!Utils.isNull(item.goods_image)) {
            int windowWidth = Utils.getWindowWidth(viewHolder.itemView.getContext());
            int imageWidth = (int) (windowWidth / 2.5);
            if (imageWidth > 350) {
                imageWidth = 350;
            }
            ImageLoader.displayImage(Utils.urlOfImage(item.goods_image), viewHolder
                    .thumbImageView, imageWidth, imageWidth);

            if (item.is_best.equals("1")) {
                viewHolder.goodsTag.setVisibility(View.VISIBLE);
                viewHolder.goodsTag.setImageResource(R.mipmap.ic_index_best);
            } else if (item.is_hot.equals("1")) {
                viewHolder.goodsTag.setVisibility(View.VISIBLE);
                viewHolder.goodsTag.setImageResource(R.mipmap.ic_index_hot);
            } else if (item.is_new.equals("1")) {
                viewHolder.goodsTag.setVisibility(View.VISIBLE);
                viewHolder.goodsTag.setImageResource(R.mipmap.ic_index_new);
            } else {
                viewHolder.goodsTag.setVisibility(View.GONE);
            }
            viewHolder.priceTextView.setText(item.goods_price_format);
            viewHolder.priceTextView.setTextSize(18);

            viewHolder.nameTextView.setText(item.goods_name);
            Utils.setPositionForTag(viewHolder.itemView, position);
            Utils.setViewTypeForTag(viewHolder.itemView, ViewType.VIEW_TYPE_GOODS);
            viewHolder.itemView.setOnClickListener(onClickListener);
        }
    }
}