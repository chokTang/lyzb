package com.szy.yishopcustomer.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;

import com.szy.common.Util.ImageLoader;
import com.szy.yishopcustomer.Constant.Macro;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.GoodsModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.EmptyItemViewHolder;
import com.szy.yishopcustomer.ViewHolder.GoodsListViewHolder;
import com.szy.yishopcustomer.ViewHolder.LoadingItemViewHolder;
import com.szy.yishopcustomer.ViewHolder.RequestFailedItemViewHolder;
import com.szy.yishopcustomer.ViewModel.EmptyItemModel;
import com.szy.yishopcustomer.ViewModel.LoadingItemModel;
import com.szy.yishopcustomer.ViewModel.RequestFailedItemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 宗仁 on 16/8/22.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class GoodsListAdapter extends RecyclerView.Adapter {

    public static final int VIEW_TYPE_GOODS_LIST = 0;
    public static final int VIEW_TYPE_GOODS_GRID = 1;
    public static final int VIEW_TYPE_EMPTY = 2;
    public static final int VIEW_TYPE_LOADING = 3;
    public static final int VIEW_TYPE_REQUEST_FAILED = 4;

    public List<Object> data;
    public int style;
    public View.OnClickListener onClickListener;
    public int bindPosition;
    public int targetImageWidth;
    public int targetImageHeight;
    private Context mContext;

    public GoodsListAdapter(Context context) {
        mContext = context;
        data = new ArrayList<>();
        style = Macro.STYLE_GRID;
        this.targetImageWidth = Utils.dpToPx(context, 100);
        this.targetImageHeight = this.targetImageWidth;
    }

    public RecyclerView.ViewHolder createGoodsViewHolder(ViewGroup parent, int layout) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new GoodsListViewHolder(view);
    }

    public void insertEmptyItemAtTheEnd() {
        removeLastNonGoodsItem();
        data.add(new EmptyItemModel());
        this.notifyItemInserted(data.size() - 1);
    }

    public void insertLoadingItemAtTheEnd() {
        removeLastNonGoodsItem();
        data.add(new LoadingItemModel());
        this.notifyItemInserted(data.size() - 1);
    }

    public void insertRequestFailedItemAtTheEnd() {
        removeLastNonGoodsItem();
        data.add(new RequestFailedItemModel());
        this.notifyItemInserted(data.size() - 1);
    }

    public boolean isLastItemEmptyType() {
        if (data.size() == 0) {
            return false;
        }
        int viewType = getItemViewType(getItemCount() - 1);
        return viewType == VIEW_TYPE_EMPTY;
    }

    public boolean isLastItemGoodsType() {
        if (data.size() == 0) {
            return false;
        }
        int viewType = getItemViewType(getItemCount() - 1);
        return viewType == VIEW_TYPE_GOODS_GRID || viewType == VIEW_TYPE_GOODS_LIST;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_EMPTY:
                return createEmptyViewHolder(parent);
            case VIEW_TYPE_GOODS_LIST:
                return createGoodsViewHolder(parent, R.layout.fragment_goods_list_item_list);
            case VIEW_TYPE_GOODS_GRID:
                return createGoodsViewHolder(parent, R.layout.fragment_goods_list_item_grid);
            case VIEW_TYPE_LOADING:
                return createLoadingViewHolder(parent);
            case VIEW_TYPE_REQUEST_FAILED:
                return createRequestFailedViewHolder(parent);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        this.bindPosition = position;
        switch (getItemViewType(position)) {
            case VIEW_TYPE_GOODS_LIST:
            case VIEW_TYPE_GOODS_GRID:
                bindGoodsViewHolder((GoodsListViewHolder) viewHolder, (GoodsModel) data.get
                        (position), position);
                break;
            case VIEW_TYPE_LOADING:
                bindLoadingViewHolder((LoadingItemViewHolder) viewHolder, (LoadingItemModel) data
                        .get(position), position);

                break;
            case VIEW_TYPE_REQUEST_FAILED:
                bindRequestFailedViewHolder((RequestFailedItemViewHolder) viewHolder,
                        (RequestFailedItemModel) data.get(position), position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        Object object = data.get(position);
        if (object instanceof EmptyItemModel) {
            return VIEW_TYPE_EMPTY;
        } else if (object instanceof LoadingItemModel) {
            return VIEW_TYPE_LOADING;
        } else if (object instanceof RequestFailedItemModel) {
            return VIEW_TYPE_REQUEST_FAILED;
        } else if (object instanceof GoodsModel) {
            switch (style) {
                case Macro.STYLE_GRID:
                    return VIEW_TYPE_GOODS_GRID;
                case Macro.STYLE_LIST:
                    return VIEW_TYPE_GOODS_LIST;
            }
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void removeLastNonGoodsItem() {
        while (!isLastItemGoodsType()) {
            int position = data.size() - 1;
            data.remove(position);
            this.notifyItemRemoved(position);
        }
    }

    @SuppressLint("SetTextI18n")
    public void bindGoodsViewHolder(final GoodsListViewHolder viewHolder, GoodsModel model, int
            position) {

        //在购物车，满减商品点击 凑单 跳转到商品列表
        //但是数据没有返回goods_number，所以对满减凑单列表展示卖光的图标
        if (model.goods_number > 0) {
            viewHolder.sellerImageView.setVisibility(View.GONE);
            if (viewHolder.disableView != null) {
                viewHolder.disableView.setVisibility(View.GONE);
            }
        } else {
            viewHolder.sellerImageView.setVisibility(View.VISIBLE);
            if (viewHolder.disableView != null) {
                viewHolder.disableView.setVisibility(View.VISIBLE);
            }
        }

        if (model.is_hot != 0 || model.is_new != 0 || model.is_best != 0) {
//            viewHolder.tipImageView.setVisibility(View.VISIBLE);
//            if (model.is_best != 0) {
//                viewHolder.tipImageView.setImageResource(R.mipmap.ic_goods_best);
//            } else if (model.is_new != 0) {
//                viewHolder.tipImageView.setImageResource(R.mipmap.ic_goods_new);
//            } else if (model.is_hot != 0) {
//                viewHolder.tipImageView.setImageResource(R.mipmap.ic_goods_hot);
//            }
        } else {
            viewHolder.tipImageView.setVisibility(View.GONE);
        }


        viewHolder.goodsNameTextView.setText(model.goods_name);
        if (!Utils.isNull(model.shop_name)) {
            if (viewHolder.shopTextView != null) {
                viewHolder.shopTextView.setText(model.shop_name);
                Utils.setViewTypeForTag(viewHolder.shopTextView, ViewType.VIEW_TYPE_SHOP);
                Utils.setPositionForTag(viewHolder.shopTextView, position);
                viewHolder.shopTextView.setOnClickListener(onClickListener);
            }
            if (viewHolder.shopImageView != null) {
                Utils.setViewTypeForTag(viewHolder.shopImageView, ViewType.VIEW_TYPE_SHOP);
                Utils.setPositionForTag(viewHolder.shopImageView, Integer.parseInt(model.shop_id));
                viewHolder.shopImageView.setOnClickListener(onClickListener);
            }
        }

        /** 商品价格 */
//            viewHolder.goodsPriceTextView.setText("¥"+model.goods_dk_price);
        if (TextUtils.isEmpty(model.goods_dk_price)){
            viewHolder.goodsPriceTextView.setText("¥"+model.goods_price);
        }else {
            viewHolder.goodsPriceTextView.setText("¥"+model.goods_dk_price);

        }

        /** 元宝个数 */
        if (!Utils.isNull(viewHolder.goodsPriceTextViewAcer)) {
            if (Integer.valueOf(model.max_integral_num) == 0) {
                viewHolder.goodsPriceTextViewAcer.setVisibility(View.GONE);
            } else {
                viewHolder.goodsPriceTextViewAcer.setVisibility(View.VISIBLE);
                viewHolder.goodsPriceTextViewAcer.setText("+" + model.max_integral_num + "元宝");
            }
        }

        if (!TextUtils.isEmpty(model.goods_image)) {
            ImageLoader.displayImage(Utils.urlOfImage(model.goods_image), viewHolder.goodsImageView);
        } else {
            viewHolder.goodsImageView.setImageDrawable(ImageLoader.ic_stub);
        }


        if (!TextUtils.isEmpty(model.gift_sku_id)) {
            viewHolder.goodsType.setVisibility(View.VISIBLE);
        } else {
            viewHolder.goodsType.setVisibility(View.GONE);
        }

        Utils.setViewTypeForTag(viewHolder.goodsImageView, ViewType.VIEW_TYPE_GOODS);
        Utils.setPositionForTag(viewHolder.goodsImageView, position);
        viewHolder.goodsImageView.setOnClickListener(onClickListener);

        Utils.setViewTypeForTag(viewHolder.goodsNameTextView, ViewType.VIEW_TYPE_GOODS);
        Utils.setPositionForTag(viewHolder.goodsNameTextView, position);
        viewHolder.goodsNameTextView.setOnClickListener(onClickListener);
        viewHolder.itemView.setOnClickListener(onClickListener);

        //1是批发商品
        if (model.sales_model.equals("1")) {
            viewHolder.minusImageView.setVisibility(View.INVISIBLE);
            viewHolder.numberTextView.setVisibility(View.INVISIBLE);
            viewHolder.plusImageView.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.minusImageView.setVisibility(View.VISIBLE);
            viewHolder.numberTextView.setVisibility(View.VISIBLE);
            viewHolder.plusImageView.setVisibility(View.VISIBLE);

            if (model.cart_num <= 0) {
                viewHolder.minusImageView.setVisibility(View.INVISIBLE);
                viewHolder.numberTextView.setVisibility(View.INVISIBLE);
            } else {
                viewHolder.minusImageView.setVisibility(View.VISIBLE);
                viewHolder.numberTextView.setVisibility(View.VISIBLE);
                viewHolder.numberTextView.setText(String.valueOf(model.cart_num));
            }

            if (model.buy_enable.code.equals("0")) {
                viewHolder.plusImageView.setImageResource(R.mipmap.btn_cart_list_disabled);
                viewHolder.minusImageView.setImageResource(R.mipmap.btn_minus_disabled);
            } else {
                //在购物车，满减商品点击 凑单 跳转到商品列表
                //但是数据没有返回goods_number，所以对满减凑单列表展示卖光的图标
                if (Utils.isNull(model.shop_id)) {
                    viewHolder.plusImageView.setImageResource(R.mipmap.btn_cart_list);
                    viewHolder.minusImageView.setImageResource(R.mipmap.btn_minus_normal);
                } else {
                    if (model.goods_number > 0) {
                        viewHolder.plusImageView.setImageResource(R.mipmap.btn_cart_list);
                        viewHolder.minusImageView.setImageResource(R.mipmap.btn_minus_normal);
                    } else {
                        viewHolder.plusImageView.setImageResource(R.mipmap.btn_cart_list_disabled);
                        viewHolder.minusImageView.setImageResource(R.mipmap.btn_minus_disabled);
                    }
                }
            }

            Utils.setViewTypeForTag(viewHolder.plusImageView, ViewType.VIEW_TYPE_ADD);
            Utils.setPositionForTag(viewHolder.plusImageView, position);
            viewHolder.plusImageView.setOnClickListener(onClickListener);
            viewHolder.plusImageView.setTag(R.layout.fragment_goods_list_item_list, viewHolder);

            Utils.setViewTypeForTag(viewHolder.minusImageView, ViewType.VIEW_TYPE_MINUS);
            Utils.setPositionForTag(viewHolder.minusImageView, position);
            viewHolder.minusImageView.setOnClickListener(onClickListener);
        }

        if (!Utils.isNull(viewHolder.percentSaleProgressBar) || !Utils.isNull(viewHolder.percentSaleTextView)) {
            viewHolder.percentSaleProgressBar.setProgress(TextUtils.isEmpty(model.sold_rate) ? 0 : Integer.parseInt(model.sold_rate));
            viewHolder.percentSaleTextView.setText("已售" + (TextUtils.isEmpty(model.sold_rate) ? "0" : model.sold_rate) + "%");
            viewHolder.saleTextView.setVisibility(View.GONE);
        }

        if (!Utils.isNull(viewHolder.deductibleTextView)) {
            if (!TextUtils.isEmpty(model.goods_bxprice_format)) {
                viewHolder.deductibleTextView.setVisibility(View.VISIBLE);
                viewHolder.deductibleTextView.setText(model.goods_bxprice_format);
            } else {
                viewHolder.deductibleTextView.setVisibility(View.INVISIBLE);
            }
        }

        viewHolder.minusImageView.setVisibility(View.INVISIBLE);
        viewHolder.numberTextView.setVisibility(View.INVISIBLE);
    }

    private void bindLoadingViewHolder(LoadingItemViewHolder viewHolder, LoadingItemModel model,
                                       int position) {
        RotateAnimation rotateAnimation = (RotateAnimation) AnimationUtils.loadAnimation
                (viewHolder.imageView.getContext(), R.anim.rotate_animation);
        viewHolder.imageView.setAnimation(rotateAnimation);

        Utils.setViewTypeForTag(viewHolder.itemView, ViewType.VIEW_TYPE_LOADING);
        Utils.setPositionForTag(viewHolder.itemView, position);
        viewHolder.itemView.setOnClickListener(onClickListener);
    }

    private void bindRequestFailedViewHolder(RequestFailedItemViewHolder viewHolder,
                                             RequestFailedItemModel model, int position) {
        Utils.setViewTypeForTag(viewHolder.itemView, ViewType.VIEW_TYPE_ITEM_REQUEST_FAILED);
        Utils.setPositionForTag(viewHolder.itemView, position);
        viewHolder.itemView.setOnClickListener(onClickListener);
    }

    private RecyclerView.ViewHolder createEmptyViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_goods_list_item_empty, parent, false);
        return new EmptyItemViewHolder(view);
    }

    private RecyclerView.ViewHolder createLoadingViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_index_loading, parent, false);
        return new LoadingItemViewHolder(view);
    }

    private RecyclerView.ViewHolder createRequestFailedViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_goods_list_item_request_failed, parent, false);
        return new RequestFailedItemViewHolder(view);
    }

}
