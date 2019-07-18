package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;

import com.szy.common.Util.ImageLoader;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Distrib.DistribGoodsListModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.Distrib.DistribGoodsItemViewHolder;

import java.util.ArrayList;

/**
 * Created by liwei on 17/9/26.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class DistribGoodsListAdapter extends HeaderFooterAdapter<DistribGoodsListModel.DataBean.DistribGoodsItemModel> {
    public View.OnClickListener onClickListener;

    public DistribGoodsListAdapter() {
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
        View view = inflater.inflate(R.layout.fragment_distrib_goods_item, parent, false);
        return new DistribGoodsItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        if(getItemViewType(position) == TYPE_NORMAL){
            if(viewHolder instanceof DistribGoodsItemViewHolder) {
                //这里加载数据的时候要注意，是从position-1开始，因为position==0已经被header占用了
                int tempposition = position;
                if(mHeaderView != null) {
                    tempposition = position-1;
                }

                DistribGoodsListModel.DataBean.DistribGoodsItemModel object = data.get(tempposition);
                onBindGoodsItemViewHolder((DistribGoodsItemViewHolder)viewHolder,object,tempposition);
                return;
            }
            return;
        }else if(getItemViewType(position) == TYPE_HEADER){
            return;
        }else{
            return;
        }

    }

    private void onBindGoodsItemViewHolder(DistribGoodsItemViewHolder holder, DistribGoodsListModel.DataBean.DistribGoodsItemModel item,int position){
        ImageLoader.displayImage(Utils.urlOfImage(item.goods_image),holder.goodsImage);
        holder.goodsName.setText(item.goods_name);
        holder.goodsPrice.setText(Utils.formatMoney(holder.itemView.getContext(),item.goods_price));

        String profit = "";
        for(int i=0;i<item.profit.size();i++){
            profit+=item.profit.get(i);
            if(i!=item.profit.size()-1){
                profit+="\n";
            }
        }
        holder.profit.setText(profit);

        if("1".equals(item.is_sale)){
            holder.onSaleButton.setText("取消上架");
        }else{
            holder.onSaleButton.setText("上架");
        }

        Utils.setPositionForTag(holder.goodsImage, position);
        Utils.setViewTypeForTag(holder.goodsImage, ViewType.VIEW_TYPE_GOODS);
        holder.goodsImage.setOnClickListener(onClickListener);
        Utils.setPositionForTag(holder.goodsName, position);
        Utils.setViewTypeForTag(holder.goodsName, ViewType.VIEW_TYPE_GOODS);
        holder.goodsName.setOnClickListener(onClickListener);
        Utils.setPositionForTag(holder.onSaleButton, position);
        Utils.setViewTypeForTag(holder.onSaleButton, ViewType.VIEW_TYPE_SALE);
        holder.onSaleButton.setOnClickListener(onClickListener);
    }
}
