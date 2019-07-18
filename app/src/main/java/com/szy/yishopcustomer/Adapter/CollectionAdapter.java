package com.szy.yishopcustomer.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;
import android.view.ViewGroup;

import com.szy.common.Util.ImageLoader;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Checkout.CheckoutDividerModel;
import com.szy.yishopcustomer.ResponseModel.Collection.CollectionGoodsModel;
import com.szy.yishopcustomer.ResponseModel.Collection.CollectionShopModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.Collection.CollectionGoodsViewHolder;
import com.szy.yishopcustomer.ViewHolder.Collection.CollectionShopViewHolder;
import com.szy.yishopcustomer.ViewHolder.DividerViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liwei on 2017/1/6.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class CollectionAdapter extends RecyclerView.Adapter {

    private final int VIEW_TYPE_COLLECTION_SHOP = 0;
    private final int VIEW_TYPE_COLLECTION_GOODS = 1;
    private final int VIEW_TYPE_DIVIDER = 2;

    public boolean editing;
    public List<Object> data;
    public View.OnClickListener onClickListener;

    public CollectionAdapter() {
        this.data = new ArrayList<>();
    }

    public CollectionAdapter(List<Object> data) {
        this.data = data;
    }

    public RecyclerView.ViewHolder createDividerViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_goods_list_item_empty, parent, false);
        return new DividerViewHolder(view);
    }

    public RecyclerView.ViewHolder createGoodsViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_collection_goods, parent, false);
        return new CollectionGoodsViewHolder(view);
    }

    public RecyclerView.ViewHolder createShopViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_collection_shop, parent, false);
        return new CollectionShopViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_COLLECTION_SHOP:
                return createShopViewHolder(parent);
            case VIEW_TYPE_COLLECTION_GOODS:
                return createGoodsViewHolder(parent);
            case VIEW_TYPE_DIVIDER:
                return createDividerViewHolder(parent);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case VIEW_TYPE_COLLECTION_SHOP:
                bindShopViewHolder((CollectionShopViewHolder) holder, position);
                break;
            case VIEW_TYPE_COLLECTION_GOODS:
                bindGoodsViewHolder((CollectionGoodsViewHolder) holder, position);
                break;
            case VIEW_TYPE_DIVIDER:
                bindDividerViewHolder((DividerViewHolder) holder, position);
                break;
        }
    }

    public int getItemViewType(int position) {
        Object object = data.get(position);
        if (object instanceof CollectionShopModel) {
            return VIEW_TYPE_COLLECTION_SHOP;
        } else if (object instanceof CollectionGoodsModel) {
            return VIEW_TYPE_COLLECTION_GOODS;
        } else if (object instanceof CheckoutDividerModel) {
            return VIEW_TYPE_DIVIDER;
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private void bindDividerViewHolder(DividerViewHolder holder, int position) {
    }

    private void bindGoodsViewHolder(CollectionGoodsViewHolder holder, int position) {
        CollectionGoodsModel item = (CollectionGoodsModel) data.get(position);
        holder.goodsNameTextView.setText(item.goods_name);

        if (Utils.isNumber(item.goods_dk_price)) {
            holder.goodsPriceTextView.setText(Utils.formatMoney(holder.goodsPriceTextView.getContext(), item.goods_dk_price));
        } else {
            holder.goodsPriceTextView.setText("¥"+item.goods_dk_price);
        }
        if (Integer.valueOf(item.max_integral_num)==0){//+元宝显示
            holder.goodsDudePrice.setVisibility(View.GONE);
        }else {
            holder.goodsDudePrice.setVisibility(View.VISIBLE);
            holder.goodsDudePrice.setText("+"+item.max_integral_num+"元宝");
        }


        if (!Utils.isNull(item.goods_image)) {
            ImageLoader.displayImage(Utils.urlOfImage(item.goods_image), holder.goodsImage);
        }

        if (editing) {
            holder.checkboxImageView.setVisibility(View.VISIBLE);
            holder.cartLayout.setVisibility(View.GONE);
            if (item.selected) {
                holder.checkboxImageView.setSelected(true);
            } else {
                holder.checkboxImageView.setSelected(false);
            }

/*            holder.goodsNameTextView.setVisibility(View.INVISIBLE);
            holder.goodsImage.setVisibility(View.INVISIBLE);
            holder.plusButton.setVisibility(View.INVISIBLE);*/

            holder.goodsNameTextView.setClickable(false);
            holder.goodsImage.setClickable(false);
            holder.plusButton.setClickable(false);

            Utils.setViewTypeForTag(holder.itemView, ViewType.VIEW_TYPE_SELECT_GOOD);
            Utils.setPositionForTag(holder.itemView, position);
            Utils.setExtraInfoForTag(holder.itemView, Integer.valueOf(item.sku_id));
            holder.itemView.setOnClickListener(onClickListener);
        } else {
            holder.checkboxImageView.setVisibility(View.GONE);
            holder.cartLayout.setVisibility(View.VISIBLE);

/*            holder.goodsNameTextView.setVisibility(View.VISIBLE);
            holder.goodsImage.setVisibility(View.VISIBLE);
            holder.plusButton.setVisibility(View.VISIBLE);*/

            holder.goodsNameTextView.setClickable(true);
            holder.goodsImage.setClickable(true);
            holder.plusButton.setClickable(true);

            Utils.setViewTypeForTag(holder.goodsNameTextView, ViewType.VIEW_TYPE_GOODS);
            Utils.setPositionForTag(holder.goodsNameTextView, position);
            Utils.setExtraInfoForTag(holder.goodsNameTextView, Integer.valueOf(item.sku_id));
            holder.goodsNameTextView.setOnClickListener(onClickListener);

            Utils.setViewTypeForTag(holder.goodsImage, ViewType.VIEW_TYPE_GOODS);
            Utils.setPositionForTag(holder.goodsImage, position);
            Utils.setExtraInfoForTag(holder.goodsImage, Integer.valueOf(item.sku_id));
            holder.goodsImage.setOnClickListener(onClickListener);

            Utils.setViewTypeForTag(holder.plusButton, ViewType.VIEW_TYPE_ADD);
            Utils.setPositionForTag(holder.plusButton, position);
            holder.plusButton.setOnClickListener(onClickListener);
            holder.plusButton.setTag(R.layout
                    .item_collection_goods, holder);

            Utils.setViewTypeForTag(holder.minusButton, ViewType.VIEW_TYPE_MINUS);
            Utils.setPositionForTag(holder.minusButton, position);
            holder.minusButton.setOnClickListener(onClickListener);

            if ("1".equals(item.sales_model)) {
                holder.minusButton.setVisibility(View.INVISIBLE);
                holder.goodsNumber.setVisibility(View.INVISIBLE);
                holder.plusButton.setVisibility(View.INVISIBLE);
            } else {
                holder.plusButton.setVisibility(View.VISIBLE);
                if (Utils.isNull(item.cart_num)) {
                    holder.minusButton.setVisibility(View.INVISIBLE);
                    holder.goodsNumber.setVisibility(View.INVISIBLE);
                } else {
                    if (Integer.parseInt(item.cart_num) <= 0) {
                        holder.minusButton.setVisibility(View.INVISIBLE);
                        holder.goodsNumber.setVisibility(View.INVISIBLE);
                    } else {
                        holder.minusButton.setVisibility(View.VISIBLE);
                        holder.goodsNumber.setVisibility(View.VISIBLE);
                        holder.goodsNumber.setText(item.cart_num);
                    }
                }
            }
        }

    }

    private void bindShopViewHolder(CollectionShopViewHolder holder, int position) {
        CollectionShopModel item = (CollectionShopModel) data.get(position);
        holder.shopNameTextView.setText(item.shop_name);
        ImageLoader.displayImage(Utils.urlOfImage(item.shop_logo), holder.collectionShopLogo);

        Context mContext = holder.itemView.getContext();
        if (editing) {
            holder.rightArrowImageView.getLayoutParams().width = Utils.dpToPx(mContext, 20);
            holder.rightArrowImageView.getLayoutParams().height = Utils.dpToPx(mContext, 20);

            holder.rightArrowImageView.setImageResource(R.mipmap.bg_check_normal);
            if (item.selected) {
                holder.rightArrowImageView.setImageResource(R.mipmap.bg_check_selected);
            } else {
                holder.rightArrowImageView.setImageResource(R.mipmap.bg_check_normal);
            }

            Utils.setViewTypeForTag(holder.itemView, ViewType.VIEW_TYPE_SELECT_SHOP);
            Utils.setPositionForTag(holder.itemView, position);

            int shopid = 0;
            try {
                shopid = Integer.valueOf(item.shop_id);
            } catch (Exception e) {

            }
            Utils.setExtraInfoForTag(holder.itemView, shopid);
            holder.itemView.setOnClickListener(onClickListener);
        } else {
            holder.rightArrowImageView.getLayoutParams().width = Utils.dpToPx(mContext, 15);
            holder.rightArrowImageView.getLayoutParams().height = Utils.dpToPx(mContext, 15);
            holder.rightArrowImageView.setImageResource(R.mipmap.btn_right_arrow);

            Utils.setViewTypeForTag(holder.itemView, ViewType.VIEW_TYPE_SHOP);
            Utils.setPositionForTag(holder.itemView, position);
            if (!Utils.isNull(item.shop_id)) {
                Utils.setExtraInfoForTag(holder.itemView, Integer.valueOf(item.shop_id));
            }
            holder.itemView.setOnClickListener(onClickListener);
        }

    }

}
