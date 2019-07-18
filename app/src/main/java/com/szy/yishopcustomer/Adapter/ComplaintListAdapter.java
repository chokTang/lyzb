package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;
import android.view.ViewGroup;

import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.OrderList.ComplaintListModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.Order.ComplaintItemViewHolder;

import java.util.ArrayList;


/**
 * Created by liwei on 2017/11/16.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */


public class ComplaintListAdapter extends HeaderFooterAdapter<ComplaintListModel.DataBean.ListBean>   {

    public View.OnClickListener onClickListener;

    public ComplaintListAdapter() {
        data = new ArrayList<>();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(mHeaderView != null && viewType == TYPE_HEADER) {
            return new ListHolder(mHeaderView);
        }
        if(mFooterView != null && viewType == TYPE_FOOTER){
            return new ListHolder(mFooterView);
        }

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return createViewHolder(inflater, parent);

    }

    private RecyclerView.ViewHolder createViewHolder(LayoutInflater inflater, ViewGroup
            parent) {
        View view = inflater.inflate(R.layout.fragment_complaint_list_item, parent, false);
        return new ComplaintItemViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if(getItemViewType(position) == TYPE_NORMAL){
            if(viewHolder instanceof ComplaintItemViewHolder) {
                //这里加载数据的时候要注意，是从position-1开始，因为position==0已经被header占用了
                int tempposition = position;
                if(mHeaderView != null) {
                    tempposition = position-1;
                }
                ComplaintListModel.DataBean.ListBean object = data.get(tempposition);
                bindCompliantViewHolder((ComplaintItemViewHolder) viewHolder, object,
                        tempposition);
                return;
            }
            return;
        }else if(getItemViewType(position) == TYPE_HEADER){
            return;
        }else{
            return;
        }
    }

    private void bindCompliantViewHolder(ComplaintItemViewHolder holder, ComplaintListModel.DataBean.ListBean item, int
            position) {

        holder.shopName.setText(item.shop_name);
        holder.status.setText(item.role_type_string);
        holder.complaintSn.setText(item.complaint_sn);
        holder.orderSn.setText(item.order_sn);
        holder.userName.setText(item.user_name);
        holder.complaintReason.setText(item.complaint_type_string);
        holder.addTime.setText(item.add_time_format);

        Utils.setPositionForTag(holder.shopNameLayout, position);
        Utils.setViewTypeForTag(holder.shopNameLayout, ViewType.VIEW_TYPE_SHOP);
        holder.shopNameLayout.setOnClickListener(onClickListener);

        Utils.setPositionForTag(holder.orderSn, position);
        Utils.setViewTypeForTag(holder.orderSn, ViewType.VIEW_TYPE_ORDER);
        holder.orderSn.setOnClickListener(onClickListener);

        Utils.setPositionForTag(holder.viewDetail, position);
        Utils.setViewTypeForTag(holder.viewDetail, ViewType.VIEW_TYPE_DETAIL);
        holder.viewDetail.setOnClickListener(onClickListener);
    }
}