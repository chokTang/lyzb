package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;
import android.view.ViewGroup;

import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.SelectAddress.PoiListModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.SelectAddress.PoiResultViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Smart on 2017/5/4.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 *
 * 收货地址详情点击下拉的POI列表适配器
 */

public class AddressReceiveDetailPoiAdapter extends RecyclerView.Adapter{
    private final int VIEW_TYPE_ITEM = 0;

    public List<Object> data;
    public View.OnClickListener onClickListener;

    public AddressReceiveDetailPoiAdapter() {
        this(null);
    }

    public AddressReceiveDetailPoiAdapter(List<Object> data) {
        if(data == null) {
            this.data = new ArrayList<>();
        } else {
            this.data = data;
        }
    }


    public RecyclerView.ViewHolder createPoiNearListViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_select_address_receive_poi, parent, false);
        return new PoiResultViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_ITEM:
                return createPoiNearListViewHolder(parent);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case VIEW_TYPE_ITEM:
                bindPoiListViewHolder((PoiResultViewHolder) holder, position);
                break;
        }
    }

    public int getItemViewType(int position) {
        Object object = data.get(position);
        if (object instanceof PoiListModel) {
            return VIEW_TYPE_ITEM;
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private void bindPoiListViewHolder(PoiResultViewHolder holder, int position) {
        PoiListModel dataModel = (PoiListModel) data.get(position);
        holder.titleTextView.setText(dataModel.title);
        holder.contentTextView.setText(dataModel.content);

        Utils.setViewTypeForTag(holder.itemView, ViewType.VIEW_TYPE_NEAR_POI_ITEM);
        Utils.setPositionForTag(holder.itemView, position);
        holder.itemView.setOnClickListener(onClickListener);
    }
}
