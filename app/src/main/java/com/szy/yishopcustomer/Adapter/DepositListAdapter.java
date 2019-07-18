package com.szy.yishopcustomer.Adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;
import android.view.ViewGroup;

import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.DepositList.WithdrawalRecordList;
import com.szy.yishopcustomer.ResponseModel.DividerModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.DepositListViewHolder;
import com.szy.yishopcustomer.ViewHolder.DividerViewHolder;
import com.szy.yishopcustomer.ViewHolder.EmptyItemViewHolder;
import com.szy.yishopcustomer.ViewModel.EmptyItemModel;

import java.util.List;

/**
 * Created by liuzhifeng on 2016/6/3 0003.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class DepositListAdapter extends RecyclerView.Adapter {

    private final int ITEM_TYPE_DETAIL = 0;
    private final int ITEM_TYPE_EMPTY = 1;
    private final int ITEM_TYPE_BLANK = 2;

    public View.OnClickListener onClickListener;
    public List<Object> data;

    public DepositListAdapter(List<Object> data) {
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case ITEM_TYPE_DETAIL:
                return createDepositListViewHolder(parent, inflater);
            case ITEM_TYPE_EMPTY:
                return createEmptyViewHolder(parent, inflater);
            case ITEM_TYPE_BLANK:
                return createBlankViewHolder(parent, inflater);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case ITEM_TYPE_DETAIL:
                DepositListViewHolder viewHolder = (DepositListViewHolder) holder;
                WithdrawalRecordList item = (WithdrawalRecordList) data.get(position);
                viewHolder.money.setText(item.amount + "元");


                viewHolder.refuseTip.setVisibility(View.GONE);


                if (item.status.equals("0")) {
                    viewHolder.statuse.setText("待审核");
                    viewHolder.detele.setText("取消");
                    viewHolder.detele.setVisibility(View.VISIBLE);
                    Utils.setPositionForTag(viewHolder.detele, position);
                    Utils.setViewTypeForTag(viewHolder.detele, ViewType.VIEW_TYPE_DEPOSIT_CANCLE);
                    Utils.setExtraInfoForTag(viewHolder.detele, item.id);
                    viewHolder.detele.setOnClickListener(onClickListener);
                } else if (item.status.equals("1")) {
                    viewHolder.statuse.setText("审核通过，转账中");
                    viewHolder.detele.setVisibility(View.INVISIBLE);
                } else if (item.status.equals("2")) {
                    viewHolder.statuse.setText("提现成功");
                    viewHolder.detele.setVisibility(View.INVISIBLE);
                } else if (item.status.equals("3")) {
                    viewHolder.statuse.setText("已取消");
                    viewHolder.detele.setText("删除");
                    viewHolder.detele.setVisibility(View.VISIBLE);
                    Utils.setPositionForTag(viewHolder.detele, position);
                    Utils.setViewTypeForTag(viewHolder.detele, ViewType.VIEW_TYPE_DEPOSIT_DELETE);
                    Utils.setExtraInfoForTag(viewHolder.detele, item.id);
                    viewHolder.detele.setOnClickListener(onClickListener);
                } else if (item.status.equals("4")) {
                    viewHolder.statuse.setText("已拒绝");
                    viewHolder.detele.setText("删除");
                    viewHolder.detele.setVisibility(View.VISIBLE);
                    Utils.setPositionForTag(viewHolder.detele, position);
                    Utils.setViewTypeForTag(viewHolder.detele, ViewType.VIEW_TYPE_DEPOSIT_DELETE);
                    Utils.setExtraInfoForTag(viewHolder.detele, item.id);
                    viewHolder.detele.setOnClickListener(onClickListener);

                    if(!TextUtils.isEmpty(item.admin_note)){
                        viewHolder.refuseTip.setVisibility(View.VISIBLE);
                        viewHolder.refuseTip.setText(Utils.spanStringColor("[拒绝理由] " + item.admin_note ,  ContextCompat.getColor(viewHolder.refuseTip
                                .getContext(), R.color.colorCyan), 0, 6));
                    }
                }

                viewHolder.time.setText(Utils.toTimeString(item.add_time));
                viewHolder.accoutname.setText(item.note);
                break;
            case ITEM_TYPE_EMPTY:
                break;
            case ITEM_TYPE_BLANK:
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        Object object = data.get(position);
        if (object instanceof WithdrawalRecordList) {
            return ITEM_TYPE_DETAIL;
        } else if (object instanceof EmptyItemModel) {
            return ITEM_TYPE_EMPTY;
        } else if (object instanceof DividerModel) {
            return ITEM_TYPE_BLANK;
        } else {
            return -1;
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<Object> data) {
        this.data = data;
    }

    private RecyclerView.ViewHolder createBlankViewHolder(ViewGroup parent, LayoutInflater
            inflater) {
        View view = inflater.inflate(R.layout.item_divider_one, parent, false);
        return new DividerViewHolder(view);
    }

    private RecyclerView.ViewHolder createDepositListViewHolder(ViewGroup parent, LayoutInflater
            inflater) {
        View view = inflater.inflate(R.layout.item_fragment_withdrawal_record, parent, false);
        return new DepositListViewHolder(view);
    }

    private RecyclerView.ViewHolder createEmptyViewHolder(ViewGroup parent, LayoutInflater
            inflater) {
        View view = inflater.inflate(R.layout.fragment_goods_list_item_empty, parent, false);
        return new EmptyItemViewHolder(view);
    }

}
