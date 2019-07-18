package com.szy.yishopcustomer.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.ShopArticleDetailViewHolder;
import com.szy.yishopcustomer.ViewHolder.ShopArticleTitleViewHolder;
import com.szy.yishopcustomer.ViewModel.ShopArticleDetailModel;
import com.szy.yishopcustomer.ViewModel.ShopArticleTitleModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuzhifeng on 16/7/18.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class
ShopArticleAdapter extends RecyclerView.Adapter {
    private static final int ITEM_TYPE_TITLE = 0;
    private static final int ITEM_TYPE_ARTICLE = 1;
    public RelativeLayout.OnClickListener onClickListener;
    public List<Object> data;

    public ShopArticleAdapter() {
        this.data = new ArrayList<>();
    }

    public void setData(List<Object> list) {
        this.data = list;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case ITEM_TYPE_TITLE:
                return createTitleViewHolder(parent, inflater);
            case ITEM_TYPE_ARTICLE:
                return createDetailViewHolder(parent, inflater);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case ITEM_TYPE_TITLE:
                upDataTitle((ShopArticleTitleViewHolder) holder, position);
                break;
            case ITEM_TYPE_ARTICLE:
                upDataDetail((ShopArticleDetailViewHolder) holder, position);
                break;
        }
    }

    private void upDataDetail(ShopArticleDetailViewHolder holder, int position) {
        ShopArticleDetailViewHolder viewHolder = holder;
        ShopArticleDetailModel model = (ShopArticleDetailModel) data.get(position);
        viewHolder.mDetail.setText(model.detail);
    }

    private void upDataTitle(ShopArticleTitleViewHolder holder, int position) {
        ShopArticleTitleViewHolder viewHolder = holder;
        ShopArticleTitleModel model = (ShopArticleTitleModel) data.get(position);
        viewHolder.mShopName.setText(model.shopName);
        viewHolder.mShopName.getPaint().setFakeBoldText(true);
        viewHolder.mShopIntroduce.setText(model.detail);

        Glide.with(holder.itemView.getContext()).load(Utils.urlOfImage(model.url)).into(viewHolder.mShopRank);
//        ImageLoader.displayImage(Utils.urlOfImage(model.url),viewHolder.mShopRank);
    }

    @Override
    public int getItemViewType(int position) {
        Object object = data.get(position);
        if (object instanceof ShopArticleTitleModel) {
            return ITEM_TYPE_TITLE;
        } else if (object instanceof ShopArticleDetailModel) {
            return ITEM_TYPE_ARTICLE;
        } else {
            return -1;
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(ArrayList<Object> data) {
        this.data = data;
    }

    @NonNull
    private RecyclerView.ViewHolder createDetailViewHolder(ViewGroup parent, LayoutInflater
            inflater) {
        View view = inflater.inflate(R.layout.item_shop_article_detail, parent, false);
        return new ShopArticleDetailViewHolder(view);
    }

    @NonNull
    private RecyclerView.ViewHolder createTitleViewHolder(ViewGroup parent, LayoutInflater
            inflater) {
        View view = inflater.inflate(R.layout.item_shop_article_title, parent, false);
        return new ShopArticleTitleViewHolder(view);
    }
}
