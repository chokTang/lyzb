package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;

import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.AppIndex.ShopListItemModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.CheckoutDividerModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.DividerViewHolder;
import com.szy.yishopcustomer.ViewHolder.ShopListSelectItemViewHolder;

import java.util.ArrayList;
import java.util.List;

public class SelectShopListAdapter extends RecyclerView.Adapter {
    public static final int ITEM_TYPE_SHOP = 0;
    public static final int ITEM_TYPE_DIVIDER = 1;

    public List<Object> data;
    public View.OnClickListener onClickListener;

    public SelectShopListAdapter() {
        data = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_TYPE_SHOP:
                return createShopViewHolder(parent);
            case ITEM_TYPE_DIVIDER:
                return createDividerViewHolder(parent);
        }
        return null;
    }


    public RecyclerView.ViewHolder createShopViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_select_shop_list_item, parent, false);
        return new ShopListSelectItemViewHolder(view);
    }


    public RecyclerView.ViewHolder createDividerViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_goods_list_item_empty, parent, false);
        return new DividerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case ITEM_TYPE_SHOP:
                bindShopViewHolder((ShopListSelectItemViewHolder) holder, position);
                break;
            case ITEM_TYPE_DIVIDER:
                bindDividerViewHolder((DividerViewHolder) holder, position);
                break;
        }
    }

    private void bindShopViewHolder(ShopListSelectItemViewHolder viewHolder, int position) {
        viewHolder.distanceTextView.setVisibility(View.VISIBLE);
        viewHolder.location.setVisibility(View.VISIBLE);

        ShopListItemModel item = (ShopListItemModel) data.get(position);

        viewHolder.nameTextView.setText(item.shop_name);
        TextPaint tp = viewHolder.nameTextView.getPaint();
        tp.setFakeBoldText(true);

        if (item.distance > 0) {
            viewHolder.distanceTextView.setVisibility(View.VISIBLE);
            viewHolder.location.setVisibility(View.VISIBLE);
            if (item.distance < 1) {
                viewHolder.distanceTextView.setText(item.distance * 1000 + "m");
            } else {
                viewHolder.distanceTextView.setText(item.distance + "km");
            }
        } else {
            viewHolder.distanceTextView.setVisibility(View.INVISIBLE);
            viewHolder.location.setVisibility(View.INVISIBLE);
        }

        if(TextUtils.isEmpty(item.address)) {
            viewHolder.addressTextView.setText("");
        } else {
            viewHolder.addressTextView.setText("[" + item.address + "]");
        }


        Utils.setViewTypeForTag(viewHolder.location, ViewType.VIEW_TYPE_SHOP_LOCATION);
        Utils.setPositionForTag(viewHolder.location, position);
        viewHolder.location.setOnClickListener(onClickListener);

        Utils.setViewTypeForTag(viewHolder.distanceTextView, ViewType.VIEW_TYPE_SHOP_LOCATION);
        Utils.setPositionForTag(viewHolder.distanceTextView, position);
        viewHolder.distanceTextView.setOnClickListener(onClickListener);

        Utils.setViewTypeForTag(viewHolder.itemView, ViewType.VIEW_TYPE_SHOP);
        Utils.setPositionForTag(viewHolder.itemView, position);
        viewHolder.itemView.setOnClickListener(onClickListener);
    }

    private void bindDividerViewHolder(DividerViewHolder holder, int position) {
    }

    public int getItemViewType(int position) {
        Object object = data.get(position);
        if (object instanceof ShopListItemModel) {
            return ITEM_TYPE_SHOP;
        } else if (object instanceof CheckoutDividerModel) {
            return ITEM_TYPE_DIVIDER;
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
