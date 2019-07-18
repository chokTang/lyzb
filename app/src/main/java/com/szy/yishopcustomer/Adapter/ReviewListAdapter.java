package com.szy.yishopcustomer.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;

import com.szy.common.Util.ImageLoader;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Checkout.BlankModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.CheckoutDividerModel;
import com.szy.yishopcustomer.ResponseModel.Goods.GoodsCommentDescModel;
import com.szy.yishopcustomer.ResponseModel.ReviewList.ReviewGoodsInfoModel;
import com.szy.yishopcustomer.ResponseModel.ReviewList.ReviewTitleModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.DividerThreeViewHolder;
import com.szy.yishopcustomer.ViewHolder.DividerViewHolder;
import com.szy.yishopcustomer.ViewHolder.Goods.GoodsCommentDescViewHolder;
import com.szy.yishopcustomer.ViewHolder.ReviewList.ReviewGoodsInfoViewHolder;
import com.szy.yishopcustomer.ViewHolder.ReviewList.ReviewTitleViewHolder;

import java.util.ArrayList;

/**
 * Created by liwei on 2016/12/20.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ReviewListAdapter extends RecyclerView.Adapter {
    private static final int ITEM_TYPE_BLANK = 0;
    private static final int ITEM_TYPE_TITLE = 1;
    private static final int ITEM_TYPE_GOODS_INFO = 2;
    private static final int ITEM_TYPE_DETAIL = 3;
    private static final int VIEW_DIVIDER = 4;

    public  View.OnClickListener onClickListener;

    public ArrayList<Object> data;

    public ReviewListAdapter() {
        this.data = new ArrayList<>();
    }

    public RecyclerView.ViewHolder createDividerViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_goods_list_item_empty, parent, false);
        return new DividerViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case ITEM_TYPE_BLANK:
                return createBlankViewHolder(parent, inflater);
            case ITEM_TYPE_TITLE:
                return createTitleViewHolder(parent, inflater);
            case ITEM_TYPE_GOODS_INFO:
                return createGoodsInfoViewHolder(parent, inflater);
            case ITEM_TYPE_DETAIL:
                return createDetailViewHolder(parent, inflater);
            case VIEW_DIVIDER:
                return createDividerViewHolder(parent);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case ITEM_TYPE_BLANK:
                bindEmptyViewHolder((DividerThreeViewHolder) holder, position);
                break;
            case ITEM_TYPE_TITLE:
                bindTitleViewHolder((ReviewTitleViewHolder) holder, position);
                break;
            case ITEM_TYPE_GOODS_INFO:
                bindGoodsInfoViewHolder((ReviewGoodsInfoViewHolder) holder, position);
                break;
            case ITEM_TYPE_DETAIL:
                bindCommentDescViewHolder((GoodsCommentDescViewHolder) holder, position);
                break;
            case VIEW_DIVIDER:
                bindDividerViewHolder((DividerViewHolder) holder, position);
                break;
        }
    }

    private void bindEmptyViewHolder(DividerThreeViewHolder holder, int position) {
        if (position == 0) {
            holder.divider.setVisibility(View.INVISIBLE);
        } else {
            holder.divider.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemViewType(int position) {
        Object object = data.get(position);
        if (object instanceof BlankModel) {
            return ITEM_TYPE_BLANK;
        } else if (object instanceof ReviewTitleModel) {
            return ITEM_TYPE_TITLE;
        } else if (object instanceof ReviewGoodsInfoModel) {
            return ITEM_TYPE_GOODS_INFO;
        } else if (object instanceof GoodsCommentDescModel) {
            return ITEM_TYPE_DETAIL;
        } else if (object instanceof CheckoutDividerModel) {
            return VIEW_DIVIDER;
        } else {
            return -1;
        }
    }

    @Override
    public int getItemCount() {
        if (!Utils.isNull(data)) {
            return data.size();
        } else {
            return 0;
        }

    }

    public void setData(ArrayList<Object> data) {
        this.data = data;
    }

    private void bindCommentDescViewHolder(GoodsCommentDescViewHolder holder, int position) {

        Context mContext = holder.itemView.getContext();

        GoodsCommentDescModel goodsCommentDescData = (GoodsCommentDescModel) data.get(position);
        holder.mCommentTime.setText(Utils.times(goodsCommentDescData.time + ""));
        holder.mCommentContent.setText(goodsCommentDescData.value);
        if (!Utils.isNull(goodsCommentDescData.image) && goodsCommentDescData.image.size() != 0) {
            holder.mRecyclerView.setVisibility(View.VISIBLE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(holder.mRecyclerView
                    .getContext());
            layoutManager.setOrientation(OrientationHelper.HORIZONTAL);
            ArrayList<String> imageList = new ArrayList<String>();
            for (int i = 0; i < goodsCommentDescData.image.size(); i++) {
                if (!Utils.isNull(goodsCommentDescData.image.get(i)) && !goodsCommentDescData
                        .image.get(i).toString().equals("")) {
                    imageList.add(goodsCommentDescData.image.get(i).toString());
                }
            }
            holder.mRecyclerView.setLayoutManager(layoutManager);
            GoodsCommentImageAdapter goodsCommentImageAdater = new GoodsCommentImageAdapter
                    (imageList);
            goodsCommentImageAdater.itemPosition = position;
            holder.mRecyclerView.setAdapter(goodsCommentImageAdater);
        } else {
            holder.mRecyclerView.setVisibility(View.GONE);
        }

        if(goodsCommentDescData.isLastReview) {
            holder.linearlayout_root.setPadding(holder.linearlayout_root.getPaddingLeft(),holder.linearlayout_root.getPaddingTop(),holder.linearlayout_root.getPaddingRight(),Utils.dpToPx(mContext,10));
        } else {
            holder.linearlayout_root.setPadding(holder.linearlayout_root.getPaddingLeft(),holder.linearlayout_root.getPaddingTop(),holder.linearlayout_root.getPaddingRight(),Utils.dpToPx(mContext,5));
        }
    }

    private void bindDividerViewHolder(DividerViewHolder holder, int position) {
    }

    private void bindGoodsInfoViewHolder(ReviewGoodsInfoViewHolder viewHolder, int position) {
        final ReviewGoodsInfoModel item = (ReviewGoodsInfoModel) data.get(position);

        ImageLoader.displayImage(Utils.urlOfImage(item.goods_image), viewHolder.reviewGoodsImg);
        viewHolder.reviewGoodsName.setText(item.goods_name);
        viewHolder.mCommentTime.setText(Utils.times(item.time + ""));

        if(TextUtils.isEmpty(item.message)) {
            viewHolder.relativeLayout_comment_content.setVisibility(View.GONE);
        } else {
            viewHolder.relativeLayout_comment_content.setVisibility(View.VISIBLE);
            viewHolder.mCommentContent.setText("    " + item.message);
        }


        if (!Utils.isNull(item.spec_info)) {
            viewHolder.reviewSpecInfo.setVisibility(View.VISIBLE);
            viewHolder.reviewSpecInfo.setText(item.spec_info);
        } else {
            viewHolder.reviewSpecInfo.setVisibility(View.GONE);
        }


        for(int j = 0 ;j < item.images.size()  ; j ++) {
            if(TextUtils.isEmpty(item.images.get(j))) {
                item.images.remove(j);
                --j;
            }
        }

        if (!Utils.isNull(item.images)) {
            viewHolder.mRecyclerView.setVisibility(View.VISIBLE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(viewHolder.mRecyclerView
                    .getContext());
            layoutManager.setOrientation(OrientationHelper.HORIZONTAL);
            ArrayList<String> imageList = new ArrayList<String>();
            for (int i = 0; i < item.images.size(); i++) {
                if (!Utils.isNull(item.images.get(i)) && !item.images.get(i).equals("")) {
                    imageList.add(item.images.get(i));
                }
            }
            viewHolder.mRecyclerView.setLayoutManager(layoutManager);
            GoodsCommentImageAdapter goodsCommentImageAdater = new GoodsCommentImageAdapter
                    (imageList);
            goodsCommentImageAdater.itemPosition = position;
            viewHolder.mRecyclerView.setAdapter(goodsCommentImageAdater);
        } else {
            viewHolder.mRecyclerView.setVisibility(View.GONE);
        }

        Utils.setViewTypeForTag(viewHolder.reviewGoodsName, ViewType.VIEW_TYPE_GIFT);
        Utils.setPositionForTag(viewHolder.reviewGoodsName, position);
        viewHolder.reviewGoodsName.setOnClickListener(onClickListener);

        Utils.setViewTypeForTag(viewHolder.reviewGoodsImg, ViewType.VIEW_TYPE_GIFT);
        Utils.setPositionForTag(viewHolder.reviewGoodsImg, position);
        viewHolder.reviewGoodsImg.setOnClickListener(onClickListener);
    }

    private void bindTitleViewHolder(ReviewTitleViewHolder viewHolder, int position) {
        ReviewTitleModel item = (ReviewTitleModel) data.get(position);
        viewHolder.reviewRankValue.setText(item.score_desc);
        viewHolder.reviewDate.setText("购买时间："+item.order_add_time);

        if (item.desc_mark.equals("1")) {
            viewHolder.reviewStarOneImg.setVisibility(View.VISIBLE);
            viewHolder.reviewStarTwoImg.setVisibility(View.GONE);
            viewHolder.reviewStarThreeImg.setVisibility(View.GONE);
            viewHolder.reviewStarFourImg.setVisibility(View.GONE);
            viewHolder.reviewStarFiveImg.setVisibility(View.GONE);
        }
        if (item.desc_mark.equals("2")) {
            viewHolder.reviewStarOneImg.setVisibility(View.VISIBLE);
            viewHolder.reviewStarTwoImg.setVisibility(View.VISIBLE);
            viewHolder.reviewStarThreeImg.setVisibility(View.GONE);
            viewHolder.reviewStarFourImg.setVisibility(View.GONE);
            viewHolder.reviewStarFiveImg.setVisibility(View.GONE);
        }
        if (item.desc_mark.equals("3")) {
            viewHolder.reviewStarOneImg.setVisibility(View.VISIBLE);
            viewHolder.reviewStarTwoImg.setVisibility(View.VISIBLE);
            viewHolder.reviewStarThreeImg.setVisibility(View.VISIBLE);
            viewHolder.reviewStarFourImg.setVisibility(View.GONE);
            viewHolder.reviewStarFiveImg.setVisibility(View.GONE);
        }
        if (item.desc_mark.equals("4")) {
            viewHolder.reviewStarOneImg.setVisibility(View.VISIBLE);
            viewHolder.reviewStarTwoImg.setVisibility(View.VISIBLE);
            viewHolder.reviewStarThreeImg.setVisibility(View.VISIBLE);
            viewHolder.reviewStarFourImg.setVisibility(View.VISIBLE);
            viewHolder.reviewStarFiveImg.setVisibility(View.GONE);
        }
        if (item.desc_mark.equals("5")) {
            viewHolder.reviewStarOneImg.setVisibility(View.VISIBLE);
            viewHolder.reviewStarTwoImg.setVisibility(View.VISIBLE);
            viewHolder.reviewStarThreeImg.setVisibility(View.VISIBLE);
            viewHolder.reviewStarFourImg.setVisibility(View.VISIBLE);
            viewHolder.reviewStarFiveImg.setVisibility(View.VISIBLE);
        }
    }

    @NonNull
    private RecyclerView.ViewHolder createBlankViewHolder(ViewGroup parent, LayoutInflater
            inflater) {
        View view = inflater.inflate(R.layout.item_divider_three, parent, false);
        return new DividerThreeViewHolder(view);
    }

    @NonNull
    private RecyclerView.ViewHolder createDetailViewHolder(ViewGroup parent, LayoutInflater
            inflater) {
        View view = inflater.inflate(R.layout.item_review_comment_desc, parent, false);
        return new GoodsCommentDescViewHolder(view);
    }

    @NonNull
    private RecyclerView.ViewHolder createGoodsInfoViewHolder(ViewGroup parent, LayoutInflater
            inflater) {
        View view = inflater.inflate(R.layout.item_review_goods_info, parent, false);
        return new ReviewGoodsInfoViewHolder(view);
    }

    @NonNull
    private RecyclerView.ViewHolder createTitleViewHolder(ViewGroup parent, LayoutInflater
            inflater) {
        View view = inflater.inflate(R.layout.item_review_title, parent, false);
        return new ReviewTitleViewHolder(view);
    }
}
