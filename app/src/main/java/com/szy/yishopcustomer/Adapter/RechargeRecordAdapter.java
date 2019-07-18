package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.*;

import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Checkout.CheckoutDividerModel;
import com.szy.yishopcustomer.ResponseModel.DividerModel;
import com.szy.yishopcustomer.ResponseModel.RechargeRecord.ListModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.DividerViewHolder;
import com.szy.yishopcustomer.ViewHolder.RechargeRecordViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liwei on 2017/5/5
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司
 */
public class RechargeRecordAdapter extends RecyclerView.Adapter {
    private static final int ITEM_TYPE_DETAIL = 0;
    private static final int ITEM_TYPE_BLANK = 1;
    private static final int ITEM_TYPE_EMPTY = 2;

    public View.OnClickListener onClickListener;
    public List<Object> mData;

    public RechargeRecordAdapter() {
        this.mData = new ArrayList<Object>() {
        };
    }

    private RecyclerView.ViewHolder createBlankViewHolder(ViewGroup parent, LayoutInflater
            inflater) {
        View view = inflater.inflate(R.layout.item_divider_one, parent, false);
        return new DividerViewHolder(view);
    }

    private RecyclerView.ViewHolder createDetailViewHolder(ViewGroup parent, LayoutInflater
            inflater) {
        View view = inflater.inflate(R.layout.item_fragment_recharge_record, parent, false);
        return new RechargeRecordViewHolder(view);
    }

    public RecyclerView.ViewHolder createDividerViewHolder(ViewGroup parent, LayoutInflater
            inflater) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_goods_list_item_empty, parent, false);
        return new DividerViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case ITEM_TYPE_DETAIL:
                return createDetailViewHolder(parent, inflater);
            case ITEM_TYPE_BLANK:
                return createBlankViewHolder(parent, inflater);
            case ITEM_TYPE_EMPTY:
                return createDividerViewHolder(parent, inflater);
            default:
                return null;
        }
    }

    @Override
    public int getItemViewType(int position) {
        Object object = mData.get(position);
        if (object instanceof ListModel) {
            return ITEM_TYPE_DETAIL;
        } else if (object instanceof CheckoutDividerModel) {
            return ITEM_TYPE_BLANK;
        } else if (object instanceof DividerModel) {
            return ITEM_TYPE_EMPTY;
        } else {
            return -1;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case ITEM_TYPE_DETAIL:
                RechargeRecordViewHolder viewHolder = (RechargeRecordViewHolder) holder;
                ListModel item = (ListModel) mData.get(position);
                viewHolder.mPaymentName.setText(Html.fromHtml(item.payment_name));
                if (item.status == 0) {
                    viewHolder.mStatus.setText("未支付");
                } else {
                    viewHolder.mStatus.setText("已支付");
                }

                if("-".startsWith(item.amount) || "+".startsWith(item.amount)) {
                    viewHolder.mAmount.setText(item.amount);
                } else {
                    viewHolder.mAmount.setText("+"+item.amount);
                }

                viewHolder.mAddTime.setText(Utils.times(item.add_time));
                break;
            case ITEM_TYPE_BLANK:

                break;
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(ArrayList<Object> data) {
        this.mData = data;
    }
}
