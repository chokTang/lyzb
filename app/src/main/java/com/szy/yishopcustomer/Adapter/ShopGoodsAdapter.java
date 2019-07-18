package com.szy.yishopcustomer.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;

import com.szy.common.Util.ImageLoader;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.ShopGoods.ShopGoodsItemModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.EmptyItemViewHolder;
import com.szy.yishopcustomer.ViewHolder.ShopGoodsViewHolder;
import com.szy.yishopcustomer.ViewModel.EmptyItemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by buqingqiang on 2016/7/8.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ShopGoodsAdapter extends RecyclerView.Adapter {
    private static final int ITEM_TYPE_DETAIL = 0;
    private static final int ITEM_TYPE_BLANK = 1;
    public List<Object> mData;
    public View.OnClickListener onClickListener;

    public ShopGoodsAdapter(List<Object> data) {
        this.mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {

            case ITEM_TYPE_DETAIL:
                return createGoodsListViewHolder(parent, inflater);
            case ITEM_TYPE_BLANK:
                return createBlankViewHolder(parent, inflater);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case ITEM_TYPE_DETAIL:
                ShopGoodsViewHolder viewHolder = (ShopGoodsViewHolder) holder;
                ShopGoodsItemModel item = (ShopGoodsItemModel) mData.get(position);
                if (!Utils.isNull(item.goods_image)) {
                    ImageLoader.displayImage(Utils.urlOfImage(item.goods_image), viewHolder
                            .goodsImageImageView);
                }
                viewHolder.goodsNameTextView.setText(item.goods_name);
                viewHolder.goodsPriceTextView.setText(item.goods_price);

                Utils.setViewTypeForTag(viewHolder.goodsImageImageView, ViewType
                        .VIEW_TYPE_SHOP_GOODS_LIST);
                Utils.setPositionForTag(viewHolder.goodsImageImageView, item.sku_id);
                viewHolder.goodsImageImageView.setOnClickListener(onClickListener);
                break;
            case ITEM_TYPE_BLANK:
                EmptyItemViewHolder goodsListEmptyViewHolder = (EmptyItemViewHolder) holder;
                break;
        }

    }

    @Override
    public int getItemViewType(int position) {
        Object object = mData.get(position);
        if (object instanceof ShopGoodsItemModel) {
            return ITEM_TYPE_DETAIL;
        } else if (object instanceof EmptyItemModel) {
            return ITEM_TYPE_BLANK;
        } else {
            return -1;
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(ArrayList<Object> data) {
        this.mData = data;
    }

    @NonNull
    private RecyclerView.ViewHolder createBlankViewHolder(ViewGroup parent, LayoutInflater
            inflater) {
        View view = inflater.inflate(R.layout.fragment_goods_list_item_empty, parent, false);
        return new EmptyItemViewHolder(view);
    }

    @NonNull
    private RecyclerView.ViewHolder createGoodsListViewHolder(ViewGroup parent, LayoutInflater
            inflater) {
        View view = inflater.inflate(R.layout.item_shop_goods, parent, false);
        return new ShopGoodsViewHolder(view);
    }
}
