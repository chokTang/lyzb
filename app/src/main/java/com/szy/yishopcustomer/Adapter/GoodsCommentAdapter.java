package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;

import com.szy.common.Util.ImageLoader;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.DividerModel;
import com.szy.yishopcustomer.ResponseModel.Goods.CommentUserInfoModel;
import com.szy.yishopcustomer.ResponseModel.Goods.GoodsCommentDescModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.DividerViewHolder;
import com.szy.yishopcustomer.ViewHolder.Goods.GoodsCommentDescViewHolder;
import com.szy.yishopcustomer.ViewHolder.Goods.GoodsCommentUserInfoViewHolder;
import com.szy.yishopcustomer.ViewModel.EmptyItemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品详情评论Tab Adapter
 */
public class GoodsCommentAdapter extends RecyclerView.Adapter {

    private final int VIEW_TYPE_USER_INFO = 0;
    private final int VIEW_TYPE_COMMENT_DESC = 1;
    private final int VIEW_DIVIDER = 2;
    private final int ITEM_TYPE_BLANK = 3;

    public List<Object> data;

    public GoodsCommentAdapter() {
        this.data = new ArrayList<>();
    }

    public RecyclerView.ViewHolder createCommentDescViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_goods_comment_desc, parent, false);
        return new GoodsCommentDescViewHolder(view);
    }

    public RecyclerView.ViewHolder createDividerViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_goods_list_item_empty, parent, false);
        return new DividerViewHolder(view);
    }

    public RecyclerView.ViewHolder createUserInfoViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_goods_comment_user_info, parent, false);
        return new GoodsCommentUserInfoViewHolder(view);
    }

    private RecyclerView.ViewHolder createBlankViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_divider_three,
                parent, false);
        return new DividerViewHolder(view);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_USER_INFO:
                return createUserInfoViewHolder(parent);
            case VIEW_TYPE_COMMENT_DESC:
                return createCommentDescViewHolder(parent);
            case VIEW_DIVIDER:
                return createDividerViewHolder(parent);
            case ITEM_TYPE_BLANK:
                return createBlankViewHolder(parent);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case VIEW_TYPE_USER_INFO:
                bindUserInfoViewHolder((GoodsCommentUserInfoViewHolder) holder, position);
                break;
            case VIEW_TYPE_COMMENT_DESC:
                bindCommentDescViewHolder((GoodsCommentDescViewHolder) holder, position);
                break;
            case VIEW_DIVIDER:
                bindDividerViewHolder((DividerViewHolder) holder, position);
                break;
            case ITEM_TYPE_BLANK:
                DividerViewHolder blankViewHolder = (DividerViewHolder) holder;
                break;
        }
    }

    public int getItemViewType(int position) {
        Object object = data.get(position);
        if (object instanceof CommentUserInfoModel) {
            return VIEW_TYPE_USER_INFO;
        } else if (object instanceof GoodsCommentDescModel) {
            return VIEW_TYPE_COMMENT_DESC;
        } else if (object instanceof DividerModel) {
            return VIEW_DIVIDER;
        } else if (object instanceof EmptyItemModel) {
            return ITEM_TYPE_BLANK;
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private void bindCommentDescViewHolder(GoodsCommentDescViewHolder holder, int position) {
        GoodsCommentDescModel goodsCommentDescData = (GoodsCommentDescModel) data.get(position);
        int descTime = goodsCommentDescData.time;
        holder.mCommentTime.setText(Utils.times(descTime + ""));
        holder.mCommentGoodsSpec.setText(goodsCommentDescData.goodsSpec);
        if (Utils.isNull(goodsCommentDescData.value)) {
            holder.mCommentContent.setVisibility(View.GONE);
        } else {
            holder.mCommentContent.setVisibility(View.VISIBLE);
            holder.mCommentContent.setText(goodsCommentDescData.value);
        }

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

    private void bindDividerViewHolder(DividerViewHolder holder, int position) {
    }

    private void bindUserInfoViewHolder(GoodsCommentUserInfoViewHolder holder, int position) {
        CommentUserInfoModel userInfoModel = (CommentUserInfoModel) data.get(position);

        ImageLoader.displayImage(Utils.urlOfImage(userInfoModel.headimg), holder.mCommentHeadimg);
        if (!Utils.isNull(userInfoModel.rank_img)) {
            holder.mCommentUserLevel.setVisibility(View.VISIBLE);
            ImageLoader.displayImage(Utils.urlOfImage(userInfoModel.rank_img), holder.mCommentUserLevel);
        } else {
            holder.mCommentUserLevel.setVisibility(View.GONE);
        }

        holder.mCommentNickName.setText(userInfoModel.userName);
    }

}
