package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;

import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Distrib.DistribIncomeRecordModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.Distrib.DistribIncomeRecordViewHolder;

import java.util.ArrayList;

/**
 * Created by liwei on 2017/7/19.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class DistribIncomeRecordAdapter extends HeaderFooterAdapter<DistribIncomeRecordModel.DataBean.ListModel> {

    public View.OnClickListener onClickListener;

    public DistribIncomeRecordAdapter() {
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
        return createRecordViewHolder(inflater, parent);
    }

    private RecyclerView.ViewHolder createRecordViewHolder(LayoutInflater inflater, ViewGroup
            parent) {
        View view = inflater.inflate(R.layout.fragment_distrib_income_record_item, parent, false);
        return new DistribIncomeRecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if(getItemViewType(position) == TYPE_NORMAL){
            if(viewHolder instanceof DistribIncomeRecordViewHolder) {
                //这里加载数据的时候要注意，是从position-1开始，因为position==0已经被header占用了
                int tempposition = position;
                if(mHeaderView != null) {
                    tempposition = position-1;
                }
                Object object = data.get(tempposition);
                bindDistribIncomeRecordViewHolder((DistribIncomeRecordViewHolder) viewHolder, (DistribIncomeRecordModel.DataBean.ListModel) object,
                        position);
                return;
            }
            return;
        }else if(getItemViewType(position) == TYPE_HEADER){
            return;
        }else{
            return;
        }
    }

    private void bindDistribIncomeRecordViewHolder(DistribIncomeRecordViewHolder viewHolder, DistribIncomeRecordModel.DataBean.ListModel object, int
            position) {
        viewHolder.week.setText(object.week);
        viewHolder.date.setText(Utils.times(object.add_time,"MM-dd"));
        viewHolder.money.setText(Utils.formatMoney(viewHolder.money.getContext(), object.money));
        viewHolder.add_time.setText(Utils.date(object.add_time)+"-提现金额");
    }
}