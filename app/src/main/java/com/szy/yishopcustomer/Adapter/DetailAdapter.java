package com.szy.yishopcustomer.Adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;
import android.view.ViewGroup;

import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Checkout.CheckoutDividerModel;
import com.szy.yishopcustomer.ViewHolder.DetailViewHolder;
import com.szy.yishopcustomer.ViewHolder.DividerViewHolder;
import com.szy.yishopcustomer.ViewHolder.EmptyItemViewHolder;
import com.szy.yishopcustomer.ViewModel.DetailModel;
import com.szy.yishopcustomer.ViewModel.EmptyItemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuzhifeng on 2016/7/19 0019.
 */
public class DetailAdapter extends RecyclerView.Adapter {
    private final int ITEM_TYPE_DETAIL = 0;
    private final int ITEM_TYPE_BLANK = 1;
    private final int ITEM_TYPE_EMPTY = 2;
    public List<Object> mData;

    public DetailAdapter() {
        this.mData = new ArrayList<Object>() {
        };
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
                return createEmptyViewHolder(parent, inflater);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case ITEM_TYPE_DETAIL:
                DetailViewHolder viewHolder = (DetailViewHolder) holder;
                DetailModel item = (DetailModel) mData.get(position);
                viewHolder.mTextViewOne.setText(item.getName());
                if (item.getMoney().indexOf("-") == 0) {
                    viewHolder.mTextViewTwo.setText(item.getMoney());
                    viewHolder.mTextViewTwo.setTextColor(ContextCompat.getColor(viewHolder
                            .mTextViewTwo.getContext(), R.color.colorPrimary));
                } else {
                    viewHolder.mTextViewTwo.setText("+" + item.getMoney());
                    viewHolder.mTextViewTwo.setTextColor(ContextCompat.getColor(viewHolder
                            .mTextViewTwo.getContext(), R.color.colorGreen));
                }
                //                viewHolder.mTextViewTwo.setText(item.getMoney());
                viewHolder.mTextViewThree.setText(item.getType());
                viewHolder.mTextViewFour.setText(item.getTime());

                break;
            case ITEM_TYPE_BLANK:
                break;
            case ITEM_TYPE_EMPTY:
                break;
        }

    }

    @Override
    public int getItemViewType(int position) {
        Object object = mData.get(position);
        if (object instanceof DetailModel) {
            return ITEM_TYPE_DETAIL;
        } else if (object instanceof CheckoutDividerModel) {
            return ITEM_TYPE_BLANK;
        } else if (object instanceof EmptyItemModel) {
            return ITEM_TYPE_EMPTY;
        } else {
            return -1;
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(ArrayList<Object> data) {
        this.mData = data;
    }

    private RecyclerView.ViewHolder createBlankViewHolder(ViewGroup parent, LayoutInflater
            inflater) {
        View view = inflater.inflate(R.layout.item_divider_one, parent, false);
        return new DividerViewHolder(view);
    }

    private RecyclerView.ViewHolder createDetailViewHolder(ViewGroup parent, LayoutInflater
            inflater) {
        View view = inflater.inflate(R.layout.item_fragment_detail, parent, false);
        return new DetailViewHolder(view);
    }

    private RecyclerView.ViewHolder createEmptyViewHolder(ViewGroup parent, LayoutInflater
            inflater) {
        View view = inflater.inflate(R.layout.fragment_goods_list_item_empty, parent, false);
        return new EmptyItemViewHolder(view);
    }
}
