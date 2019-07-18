package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;
import android.view.ViewGroup;

import com.szy.common.Util.ImageLoader;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Back.BackItemModel;
import com.szy.yishopcustomer.ResponseModel.DividerModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.Back.BackItemViewHolder;
import com.szy.yishopcustomer.ViewHolder.DividerViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 李威 on 16/11/1.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class BackListAdapter extends RecyclerView.Adapter {

    private final int VIEW_TYPE_DIVIDER = 0;
    private final int VIEW_TYPE_ITEM = 1;

    public List<Object> data;
    public View.OnClickListener onClickListener;

    public BackListAdapter() {
        this.data = new ArrayList<>();
    }

    public BackListAdapter(List<Object> data) {
        this.data = data;
    }

    public RecyclerView.ViewHolder createDividerViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_divider_one,
                parent, false);
        return new DividerViewHolder(view);
    }

    public RecyclerView.ViewHolder createItemViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_back,
                parent, false);
        return new BackItemViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_DIVIDER:
                return createDividerViewHolder(parent);
            case VIEW_TYPE_ITEM:
                return createItemViewHolder(parent);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case VIEW_TYPE_DIVIDER:
                break;
            case VIEW_TYPE_ITEM:
                bindItemViewHolder((BackItemViewHolder) holder, position);
                break;
        }
    }

    public int getItemViewType(int position) {
        Object object = data.get(position);
        if (object instanceof DividerModel) {
            return VIEW_TYPE_DIVIDER;
        } else if (object instanceof BackItemModel) {
            return VIEW_TYPE_ITEM;
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private void bindItemViewHolder(BackItemViewHolder holder, int position) {
        BackItemModel item = (BackItemModel) data.get(position);
        holder.mBackShopName.setText(item.shop.shop.shop_name);
        holder.mBackStatus.setText(item.back_status_format);

        ImageLoader.displayImage(Utils.urlOfImage(item.sku_image), holder.mBackGoodsThumb);
        holder.mBackGoodsName.setText(item.sku_name);

        //holder.mBackOrderAmount.setText("订单金额："+Utils.formatMoney(holder.mBackOrderAmount.getContext(),item.order_amount));

        if (!TextUtils.isEmpty(item.refund_money) && !TextUtils.isEmpty(item.refund_freight)) {

            double freight = Double.valueOf(item.refund_freight);
            double money = Double.valueOf(item.refund_money);
            double total_money = freight + money;

            holder.mBackAmount.setText("退款总金额：" + Utils.formatMoney(holder.mBackAmount.getContext(), Double.toString(total_money)));
        } else if (!TextUtils.isEmpty(item.refund_money)) {
            holder.mBackAmount.setText("退款总金额：" + Utils.formatMoney(holder.mBackAmount.getContext(), item.refund_money));
        }


        holder.mAttr.setText("");

        Utils.setViewTypeForTag(holder.itemView, ViewType.VIEW_TYPE_BACK_DETAIL);
        Utils.setPositionForTag(holder.itemView, position);
        Utils.setExtraInfoForTag(holder.itemView, Integer.parseInt(item.back_id));
        holder.itemView.setOnClickListener(onClickListener);

        Utils.setViewTypeForTag(holder.mDetail, ViewType.VIEW_TYPE_BACK_DETAIL);
        Utils.setPositionForTag(holder.mDetail, position);
        Utils.setExtraInfoForTag(holder.mDetail, Integer.parseInt(item.back_id));
        holder.mDetail.setOnClickListener(onClickListener);
    }
}
