package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;
import android.view.ViewGroup;

import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.AddressList.AddressItemModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.AddressItemViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 宗仁 on 16/7/23.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class AddressListAdapter extends RecyclerView.Adapter<AddressItemViewHolder> {

    private boolean isShowEdit = false;

    public List<AddressItemModel> data;
    public View.OnClickListener onClickListener;

    public void setShowEdit(Boolean isShowEdit){
        this.isShowEdit = isShowEdit;
    }
    public AddressListAdapter() {
        this.data = new ArrayList<>();
    }

    public AddressListAdapter(List<AddressItemModel> data) {
        this.data = data;
    }

    public AddressItemModel getModel(int position) {
        AddressItemModel model = data.get(position);
        return model;
    }

    @Override
    public AddressItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_address_list_item, parent, false);
        AddressItemViewHolder viewHolder = new AddressItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AddressItemViewHolder holder, int position) {

        AddressItemModel item = data.get(position);

        holder.nameTextView.setText(item.consignee);
        holder.phoneTextView.setText(item.mobile);
        String region_name = item.region_name != null ? item.region_name : item.region_code_format;
        holder.addressTextView.setText(item.region_code_format + " " + item.address_detail);

//        if (item.editing) {
        if (isShowEdit) {
            holder.bottomWrapperRelativeLayout.setVisibility(View.VISIBLE);
            holder.checkboxTextView.setText(R.string.setDefaultAddressLong);
            if (item.is_default == 1) {
                holder.checkboxImageView.setSelected(true);
            } else {
                holder.checkboxImageView.setSelected(false);
            }
            holder.defaultTip.setVisibility(View.GONE);
            holder.itemView.setOnClickListener(null);
        } else {

            holder.bottomWrapperRelativeLayout.setVisibility(View.GONE);
            if (item.is_default == 1) {
                holder.defaultTip.setVisibility(View.VISIBLE);
            } else {
                holder.defaultTip.setVisibility(View.GONE);
            }
            Utils.setPositionForTag(holder.itemView, position);
            holder.itemView.setOnClickListener(onClickListener);

        }
        Utils.setPositionForTag(holder.checkboxImageView, position);
        Utils.setPositionForTag(holder.checkboxTextView, position);
        Utils.setPositionForTag(holder.deleteTextView, position);
        Utils.setPositionForTag(holder.editTextView, position);

        Utils.setPositionForTag(holder.itemView, position);

        Utils.setViewTypeForTag(holder.checkboxImageView, ViewType.VIEW_TYPE_SET_DEFAULT);
        Utils.setViewTypeForTag(holder.checkboxTextView, ViewType.VIEW_TYPE_SET_DEFAULT);
        Utils.setViewTypeForTag(holder.deleteTextView, ViewType.VIEW_TYPE_DELETE);
        Utils.setViewTypeForTag(holder.editTextView, ViewType.VIEW_TYPE_EDIT);

        Utils.setViewTypeForTag(holder.itemView, ViewType.VIEW_TYPE_SELECT);

        holder.checkboxImageView.setOnClickListener(onClickListener);
        holder.checkboxTextView.setOnClickListener(onClickListener);
        holder.deleteTextView.setOnClickListener(onClickListener);
        holder.editTextView.setOnClickListener(onClickListener);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
