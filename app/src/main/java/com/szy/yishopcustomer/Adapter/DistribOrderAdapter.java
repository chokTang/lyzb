package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;

import com.szy.common.Util.ImageLoader;
import com.szy.yishopcustomer.Constant.Config;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Distrib.DistribOrderModel;
import com.szy.yishopcustomer.ViewHolder.Distrib.DistribOrderViewHolder;

import java.util.ArrayList;

/**
 * Created by liwei on 2017/7/20.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class DistribOrderAdapter extends HeaderFooterAdapter<DistribOrderModel.DataBean.ListModel> {

    public View.OnClickListener onClickListener;

    public DistribOrderAdapter() {
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
        View view = inflater.inflate(R.layout.fragment_distrib_order_item, parent, false);
        return new DistribOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        if(getItemViewType(position) == TYPE_NORMAL){
            if(viewHolder instanceof DistribOrderViewHolder) {

                DistribOrderModel.DataBean.ListModel object = data.get(position);
                bindOrderViewHolder((DistribOrderViewHolder) viewHolder,object,
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

    private void bindOrderViewHolder(DistribOrderViewHolder viewHolder, DistribOrderModel.DataBean.ListModel object, int
            position) {
        viewHolder.userName.setText("买家："+object.user_name);
        ImageLoader.displayImage(Config.BASE_URL+object.rank_info.img,viewHolder.userRank);
        viewHolder.shopName.setText(object.shop_name);
        viewHolder.orderSn.setText(object.order_sn);
        viewHolder.disMoney.setText("¥"+object.dis_money);
        viewHolder.orderRate.setText(object.rank_info.rate+"%");

        if(object.distrib_status.equals("1")){
            viewHolder.disStatus.setBackgroundResource(R.mipmap.bg_distrib_success);
        }else if(object.distrib_status.equals("0")){
            viewHolder.disStatus.setBackgroundResource(R.mipmap.bg_distrib_unsuccessful);
        }else{
            viewHolder.disStatus.setBackgroundResource(R.mipmap.bg_distrib_failed);
        }


    }
}