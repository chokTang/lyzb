package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;

import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Goods.PickUpModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.PickUpViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 李威 on 16/11/1.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class PickUpAdapter extends RecyclerView.Adapter {

    private static final int VIEW_TYPE_SHOP_ITEM = 0;

    public List<Object> data;
    public View.OnClickListener onClickListener;

    public PickUpAdapter() {
        this.data = new ArrayList<>();
    }

    public PickUpAdapter(List<Object> data) {
        this.data = data;
    }

    public String pickid = "";

    public RecyclerView.ViewHolder createShopItemViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pick_up_shop,
                parent, false);
        return new PickUpViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_SHOP_ITEM:
                return createShopItemViewHolder(parent);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case VIEW_TYPE_SHOP_ITEM:
                bindShopItemViewHolder((PickUpViewHolder) holder, position);
                break;
        }
    }

    public int getItemViewType(int position) {
        Object object = data.get(position);
        if (object instanceof PickUpModel) {
            return VIEW_TYPE_SHOP_ITEM;
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private void bindShopItemViewHolder(PickUpViewHolder holder, int position) {
        PickUpModel dataModel = (PickUpModel) data.get(position);

        holder.mShopName.setText(dataModel.pickup_name);
        holder.mShopAddress.setText("地址：" + dataModel.pickup_address);

        if(!TextUtils.isEmpty(dataModel.pickup_tel)) {
            holder.linearlayout_shop_tel.setVisibility(View.VISIBLE);
            holder.pick_up_shop_tel.setText("电话：" + dataModel.pickup_tel);
        } else {
            holder.linearlayout_shop_tel.setVisibility(View.GONE);
            holder.pick_up_shop_tel.setText("");
        }

        Utils.setViewTypeForTag(holder.itemView, ViewType.VIEW_TYPE_ITEM);
        Utils.setPositionForTag(holder.itemView, position);
        holder.itemView.setOnClickListener(onClickListener);

        if(pickid.equals(dataModel.pickup_id)) {
            holder.imageView_check.setSelected(true);
        }else{
            holder.imageView_check.setSelected(false);
        }
    }
}
