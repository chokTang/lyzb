package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;

import com.szy.common.Util.ImageLoader;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Goods.GiftModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.Goods.GoodsGiftListViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 李威 on 17/06/09.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class GoodsGiftListAdapter extends RecyclerView.Adapter {

    private final int VIEW_TYPE_GIFT = 0;
    public List<Object> data;
    public View.OnClickListener onClickListener;

    public GoodsGiftListAdapter() {
        this.data = new ArrayList<>();
    }

    public GoodsGiftListAdapter(List<Object> data) {
        this.data = data;
    }


    public RecyclerView.ViewHolder createGiftViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gift_goods,
                parent, false);
        return new GoodsGiftListViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_GIFT:
                return createGiftViewHolder(parent);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case VIEW_TYPE_GIFT:
                bindGiftViewHolder((GoodsGiftListViewHolder) holder, position);
                break;
        }
    }

    public int getItemViewType(int position) {
        Object object = data.get(position);
        if (object instanceof GiftModel) {
            return VIEW_TYPE_GIFT;
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    private void bindGiftViewHolder(GoodsGiftListViewHolder holder, int position) {
        GiftModel dataModel = (GiftModel) data.get(position);

        ImageLoader.displayImage(dataModel.goods_image_thumb, holder.mGiftImage);
        holder.mGiftNumber.setText("x"+dataModel.gift_number);

        Utils.setViewTypeForTag(holder.mGiftImage, ViewType.VIEW_TYPE_GIFT);
        Utils.setPositionForTag(holder.mGiftImage, position);
        Utils.setExtraInfoForTag(holder.mGiftImage, dataModel.gift_sku_id);
        holder.mGiftImage.setOnClickListener(onClickListener);
    }
}
