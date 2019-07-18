package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;

import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Checkout.CheckoutDividerModel;
import com.szy.yishopcustomer.ResponseModel.Review.EvaluateItemModel;
import com.szy.yishopcustomer.ResponseModel.Review.GoodsCommentDescModel;
import com.szy.yishopcustomer.ResponseModel.Review.ShopComment;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.View.RatingBar;
import com.szy.yishopcustomer.ViewHolder.DividerViewHolder;
import com.szy.yishopcustomer.ViewHolder.EvaluateGoodsViewHolder;
import com.szy.yishopcustomer.ViewHolder.EvaluateShopViewHolder;
import com.szy.yishopcustomer.ViewHolder.Goods.GoodsCommentDescViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liwei on 2016/11/29.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class OrderReviewAdapter extends RecyclerView.Adapter {

    private static final int VIEW_TYPE_EVALUATE_GOODS = 0;
    private static final int VIEW_TYPE_EVALUATE_SHOP = 1;
    private static final int VIEW_TYPE_DIVIDER = 2;
    private static final int VIEW_TYPE_EVALUATE_GOODS_ITEM = 3;
    public View.OnClickListener onClickListener;
    public List<Float> shopNumList = new ArrayList<>();
    public Float shopService;
    public Float shopSpeed;
    public Float logisticsSpeed;

    public List<Object> data;

    public OrderReviewAdapter() {
        data = new ArrayList<>();
    }

    public RecyclerView.ViewHolder createEvaluateGoodsViewHolder(LayoutInflater inflater,
                                                                 ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_evaluate_goods, parent, false);
        return new EvaluateGoodsViewHolder(view);
    }

    public RecyclerView.ViewHolder createShopReviewViewHolder(LayoutInflater inflater, ViewGroup
            parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_evaluate_shop,
                parent, false);
        return new EvaluateShopViewHolder(view);
    }

    public RecyclerView.ViewHolder createDividerViewHolder(LayoutInflater inflater, ViewGroup
            parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_divider_one,
                parent, false);
        return new DividerViewHolder(view);
    }

    public RecyclerView.ViewHolder createEvaluateGoodsItemViewHolder(LayoutInflater inflater,
                                                                     ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_evaluate_desc, parent, false);
        return new GoodsCommentDescViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case VIEW_TYPE_EVALUATE_GOODS:
                return createEvaluateGoodsViewHolder(inflater, parent);
            case VIEW_TYPE_EVALUATE_SHOP:
                return createShopReviewViewHolder(inflater, parent);
            case VIEW_TYPE_DIVIDER:
                return createDividerViewHolder(inflater, parent);
            case VIEW_TYPE_EVALUATE_GOODS_ITEM:
                return createEvaluateGoodsItemViewHolder(inflater, parent);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        Object object = data.get(position);
        switch (getItemViewType(position)) {
            case VIEW_TYPE_EVALUATE_GOODS:
                bindEvaluateGoodsViewHolder((EvaluateGoodsViewHolder) viewHolder,
                        (EvaluateItemModel) object, position);
                break;
            case VIEW_TYPE_EVALUATE_SHOP:
                bindShopReviewViewHolder((EvaluateShopViewHolder) viewHolder, (ShopComment)
                        object, position);
                break;
            case VIEW_TYPE_DIVIDER:
                break;
            case VIEW_TYPE_EVALUATE_GOODS_ITEM:

                bindOrderReviewItemViewHolder((GoodsCommentDescViewHolder) viewHolder, position);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        Object object = data.get(position);
        if (object instanceof EvaluateItemModel) {
            return VIEW_TYPE_EVALUATE_GOODS;
        } else if (object instanceof ShopComment) {
            return VIEW_TYPE_EVALUATE_SHOP;
        } else if (object instanceof CheckoutDividerModel) {
            return VIEW_TYPE_DIVIDER;
        } else if (object instanceof GoodsCommentDescModel) {
            return VIEW_TYPE_EVALUATE_GOODS_ITEM;
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private void bindEvaluateGoodsViewHolder(EvaluateGoodsViewHolder viewHolder,
                                             EvaluateItemModel object, int position) {
        com.szy.common.Util.ImageLoader.displayImage(Utils.urlOfImage(object.goods_image),
                viewHolder.evaluateGoodsImage);
        viewHolder.evaluateGoodsName.setText(object.goods_name);
        viewHolder.evaluateGoodsSpec.setText(object.spec_info);
        if (object.evaluate_status.equals("0")) {
            viewHolder.evaluateGoodsButton.setText("点击评价");
            Utils.setViewTypeForTag(viewHolder.evaluateGoodsButton, ViewType.VIEW_TYPE_EVALUATE);
            Utils.setPositionForTag(viewHolder.evaluateGoodsButton, position);
        } else if (object.evaluate_status.equals("1")) {
            viewHolder.evaluateGoodsButton.setText("追加评价");
            Utils.setViewTypeForTag(viewHolder.evaluateGoodsButton, ViewType.VIEW_TYPE_EVALUATE);
            Utils.setPositionForTag(viewHolder.evaluateGoodsButton, position);
        } else if (object.evaluate_status.equals("2")) {
            viewHolder.evaluateGoodsButton.setText("查看评价");
            Utils.setViewTypeForTag(viewHolder.evaluateGoodsButton, ViewType
                    .VIEW_TYPE_CHECK_EVALUATE);
            Utils.setPositionForTag(viewHolder.evaluateGoodsButton, position);
        }
        viewHolder.evaluateGoodsButton.setOnClickListener(onClickListener);
    }

    private void bindShopReviewViewHolder(EvaluateShopViewHolder viewHolder, ShopComment
            shopComment, int position) {
        if (!Utils.isNull(shopComment.shop_service)) {
            viewHolder.publishShopRelativeLayout.setVisibility(View.GONE);
            viewHolder.tv_five_star_tip.setVisibility(View.GONE);
            viewHolder.lineTwoView.setVisibility(View.GONE);
            //服务态度
            viewHolder.shopServiceRatingBar.setStar(shopComment.shop_service.intValue());
            //发货速度
            viewHolder.shopSpeedRatingBar.setStar(shopComment.shop_speed.intValue());
            //物流服务
            viewHolder.logisticsSpeedRatingBar.setStar(shopComment.logistics_speed.intValue());

            viewHolder.shopServiceRatingBar.setClickable(false);
            viewHolder.shopSpeedRatingBar.setClickable(false);
            viewHolder.logisticsSpeedRatingBar.setClickable(false);

        } else {
            viewHolder.shopServiceRatingBar.setClickable(true);
            viewHolder.shopSpeedRatingBar.setClickable(true);
            viewHolder.logisticsSpeedRatingBar.setClickable(true);

            viewHolder.publishShopRelativeLayout.setVisibility(View.VISIBLE);
            viewHolder.tv_five_star_tip.setVisibility(View.VISIBLE);
            viewHolder.lineTwoView.setVisibility(View.VISIBLE);

        }
        Utils.setViewTypeForTag(viewHolder.publishShopNumTextView, ViewType.VIEW_TYPE_PUBLISH_SHOP);
        viewHolder.publishShopNumTextView.setOnClickListener(onClickListener);


        viewHolder.shopServiceRatingBar.setOnStarChangeListener(new RatingBar.OnChangeListener() {
            @Override
            public void onChange(int star) {
                shopService = star * 1.0f;
            }
        });

        viewHolder.shopSpeedRatingBar.setOnStarChangeListener(new RatingBar.OnChangeListener() {
            @Override
            public void onChange(int star) {
                shopSpeed = star * 1.0f;
            }
        });

        viewHolder.logisticsSpeedRatingBar.setOnStarChangeListener(new RatingBar.OnChangeListener() {
            @Override
            public void onChange(int star) {
                logisticsSpeed = star * 1.0f;
            }
        });
    }

    private void bindOrderReviewItemViewHolder(GoodsCommentDescViewHolder holder, int position) {
        GoodsCommentDescModel goodsCommentDescData = (GoodsCommentDescModel) data.get(position);
        int descTime = goodsCommentDescData.time;
        holder.mCommentTime.setText(Utils.times(descTime + ""));
        holder.mCommentGoodsSpec.setText(goodsCommentDescData.goodsSpec);
        holder.mCommentContent.setText(goodsCommentDescData.value);
        if (!Utils.isNull(goodsCommentDescData.image) && goodsCommentDescData.image.size() != 0) {
            holder.mRecyclerView.setVisibility(View.VISIBLE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(holder.mRecyclerView
                    .getContext());
            layoutManager.setOrientation(OrientationHelper.HORIZONTAL);
            holder.mRecyclerView.setLayoutManager(layoutManager);
            GoodsCommentImageAdapter goodsCommentImageAdater = new GoodsCommentImageAdapter
                    (goodsCommentDescData.image);
            goodsCommentImageAdater.itemPosition = position;
            holder.mRecyclerView.setAdapter(goodsCommentImageAdater);
        } else {
            holder.mRecyclerView.setVisibility(View.GONE);
        }
    }
}
