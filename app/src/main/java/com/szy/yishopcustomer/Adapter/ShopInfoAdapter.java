package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.*;

import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.ShopInfo.ShopInfoImageViewHolder;
import com.szy.yishopcustomer.ViewHolder.ShopInfo.ShopInfoRateViewHolder;
import com.szy.yishopcustomer.ViewHolder.ShopInfo.ShopInfoTextViewHolder;
import com.szy.yishopcustomer.ViewModel.ShopInfo.ShopInfoImageModel;
import com.szy.yishopcustomer.ViewModel.ShopInfo.ShopInfoRateModel;
import com.szy.yishopcustomer.ViewModel.ShopInfo.ShopInfoTextModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 宗仁 on 2017/1/11.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class ShopInfoAdapter extends RecyclerView.Adapter {
    private static final int VIEW_TYPE_TEXT = 0;
    private static final int VIEW_TYPE_RATE = 1;
    private static final int VIEW_TYPE_IMAGE = 2;

    public List<Object> data;
    public View.OnClickListener onClickListener;

    public ShopInfoAdapter() {
        data = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_RATE:
                return createRateViewHolder(parent);
            case VIEW_TYPE_TEXT:
                return createTextViewHolder(parent);
            case VIEW_TYPE_IMAGE:
                return createImageViewHolder(parent);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case VIEW_TYPE_RATE:
                bindRateViewHolder((ShopInfoRateViewHolder) holder, (ShopInfoRateModel) data.get
                        (position));
                break;
            case VIEW_TYPE_TEXT:
                bindTextViewHolder((ShopInfoTextViewHolder) holder, (ShopInfoTextModel) data.get
                        (position));
                break;
            case VIEW_TYPE_IMAGE:
                bindImageViewHolder((ShopInfoImageViewHolder) holder, (ShopInfoImageModel) data.get
                        (position));
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        Object object = data.get(position);
        if (object instanceof ShopInfoRateModel) {
            return VIEW_TYPE_RATE;
        } else if (object instanceof ShopInfoTextModel) {
            return VIEW_TYPE_TEXT;
        } else if(object instanceof ShopInfoImageModel){
            return VIEW_TYPE_IMAGE;
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private void bindImageViewHolder(ShopInfoImageViewHolder holder, ShopInfoImageModel model) {
        if(model.type == 1){
            holder.imageView1.setVisibility(View.VISIBLE);
            holder.imageView2.setVisibility(View.INVISIBLE);
        }else if(model.type == 2){
            holder.imageView1.setVisibility(View.VISIBLE);
            holder.imageView2.setVisibility(View.VISIBLE);
        }else {
            holder.imageView1.setVisibility(View.INVISIBLE);
            holder.imageView2.setVisibility(View.INVISIBLE);
        }

        Utils.setViewTypeForTag(holder.imageView1, ViewType.VIEW_TYPE_DETAIL);
        holder.imageView1.setOnClickListener(onClickListener);
        Utils.setViewTypeForTag(holder.imageView2, ViewType.VIEW_TYPE_DETAIL);
        holder.imageView2.setOnClickListener(onClickListener);
    }

    private void bindRateViewHolder(ShopInfoRateViewHolder holder, ShopInfoRateModel model) {
        holder.descriptionTextView.setText(model.descriptionRate);
        holder.expressTextView.setText(model.expressRate);
        holder.serviceTextView.setText(model.serviceRate);
    }

    private void bindTextViewHolder(ShopInfoTextViewHolder holder, ShopInfoTextModel model) {
        holder.nameTextView.setText(model.name);

        Spanned result = Html.fromHtml(model.value);
        holder.valueTextView.setText(result);
        if (model.icon > 0) {
            holder.iconImageView.setImageResource(model.icon);
            holder.iconImageView.setVisibility(View.VISIBLE);
        } else {
            holder.iconImageView.setVisibility(View.INVISIBLE);
        }
        if (model.type >= 0) {
            Utils.setViewTypeForTag(holder.itemView, ViewType.valueOf(model.type));
            holder.itemView.setOnClickListener(onClickListener);
        } else {
            holder.itemView.setOnClickListener(null);
        }
    }

    private RecyclerView.ViewHolder createRateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_shop_info_item_rate, parent, false);
        return new ShopInfoRateViewHolder(view);
    }

    private RecyclerView.ViewHolder createTextViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_shop_info_item_text, parent, false);
        return new ShopInfoTextViewHolder(view);
    }

    private RecyclerView.ViewHolder createImageViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_shop_info_item_image, parent, false);
        return new ShopInfoImageViewHolder(view);
    }
}
