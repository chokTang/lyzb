package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;

import com.szy.common.Util.ImageLoader;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Distrib.DistribIncomeDetailsModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.Distrib.DistribIncomeDetailsViewHolder;

import java.util.ArrayList;

/**
 * Created by liwei on 2017/7/19.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class DistribIncomeDetailsAdapter extends HeaderFooterAdapter<DistribIncomeDetailsModel.DataBean.ListModel> {

    public View.OnClickListener onClickListener;

    public DistribIncomeDetailsAdapter() {
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
        View view = inflater.inflate(R.layout.fragment_distrib_income_details_item, parent, false);
        return new DistribIncomeDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        if(getItemViewType(position) == TYPE_NORMAL){
            if(viewHolder instanceof DistribIncomeDetailsViewHolder) {

                DistribIncomeDetailsModel.DataBean.ListModel object = data.get(position);
                bindDetailsViewHolder((DistribIncomeDetailsViewHolder) viewHolder,object,
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

    private void bindDetailsViewHolder(DistribIncomeDetailsViewHolder viewHolder, DistribIncomeDetailsModel.DataBean.ListModel object, int
            position) {
        viewHolder.remark.setText(object.remark);
        viewHolder.userName.setText("买家："+object.user_name);
        viewHolder.orderSn.setText("订单号："+object.order_sn);
        viewHolder.addTime.setText(Utils.times(object.add_time));

        if(object.dis_status.equals("2")){
            viewHolder.disMoney.setText(object.dis_money);
            viewHolder.disMoney.setTextColor(viewHolder.disMoney.getResources().getColor(R.color.colorPrimary));
        }else if(object.dis_status.equals("1")){
            viewHolder.disMoney.setText("+"+object.dis_money);
            viewHolder.disMoney.setTextColor(viewHolder.disMoney.getResources().getColor(R.color.colorGreen));
        }

        if(!Utils.isNull(object.headimg)){
            ImageLoader.displayImage(Utils.urlOfImage(object.headimg),viewHolder.userPhoto);
        }else{
            viewHolder.userPhoto.setImageResource(R.mipmap.pl_user_avatar);
        }
    }
}