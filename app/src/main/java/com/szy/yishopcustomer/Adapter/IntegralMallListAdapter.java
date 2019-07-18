package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;
import android.view.ViewGroup;
import android.widget.Toast;

import com.szy.common.Util.ImageLoader;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.IntegralMallListViewHolder;
import com.szy.yishopcustomer.ViewModel.IntegralMallListModel;

import java.util.ArrayList;

/**
 * Created by Smart on 2017/7/3.
 */

public class IntegralMallListAdapter extends HeaderFooterAdapter<IntegralMallListModel.DataBean.ListBean> {

    public View.OnClickListener onClickListener;

    public IntegralMallListAdapter() {
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
        View view = inflater.inflate(R.layout.fragment_integral_mall_item, parent, false);
        return new IntegralMallListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        if(getItemViewType(position) == TYPE_NORMAL){
            if(viewHolder instanceof IntegralMallListViewHolder) {
                //这里加载数据的时候要注意，是从position-1开始，因为position==0已经被header占用了
                int tempposition = position;
                if(mHeaderView != null) {
                    tempposition = position-1;
                }

                IntegralMallListModel.DataBean.ListBean object = data.get(tempposition);
                bindShopViewHolder((IntegralMallListViewHolder) viewHolder,object,
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

    private void bindShopViewHolder(IntegralMallListViewHolder viewHolder, IntegralMallListModel.DataBean.ListBean object, int
            position) {


        if (Integer.parseInt(object.goods_number) > 0) {
            viewHolder.sellerImageView.setVisibility(View.GONE);
        } else {
            viewHolder.sellerImageView.setVisibility(View.VISIBLE);
        }


        if("0".equals(object.shop_id)) {
            viewHolder.textView_mall_name.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.textView_mall_name.setVisibility(View.VISIBLE);
            viewHolder.textView_mall_name.setText(object.shop_name);
        }

        viewHolder.textView_goods_name.setText(object.goods_name);
        viewHolder.textView_exchange_figures.setText(object.exchange_number+"人兑换");

        if(object.start_time.equals("0")) {
            viewHolder.textView_use_condition.setText("无时间条件限制");
        } else {
            viewHolder.textView_use_condition.setText("有效期：" + Utils.times(object.start_time,"yyyy-MM-dd") + " 至 " + Utils.times(object.end_time,"yyyy-MM-dd"));
        }
        viewHolder.textView_shop_integral.setText(object.goods_integral);

        Utils.setViewTypeForTag(viewHolder.itemView, ViewType.VIEW_TYPE_GOODS);
        Utils.setPositionForTag(viewHolder.itemView, position);
        viewHolder.itemView.setOnClickListener(onClickListener);

        int diff = Integer.parseInt(object.diff);
        int goods_number = Integer.parseInt(object.goods_number);

        String noNuyMessage = "";
        if(diff < 0) {
            noNuyMessage = "积分不足";
        } else if(goods_number <= 0) {
            noNuyMessage = "库存不足";
        }

        if(TextUtils.isEmpty(noNuyMessage)) {
            viewHolder.fragment_button_integral.setBackgroundResource(R.drawable.enable_button);
        } else {
            viewHolder.fragment_button_integral.setBackgroundResource(R.drawable.disable_button);
        }


        Utils.setViewTypeForTag(viewHolder.fragment_button_integral, ViewType.VIEW_TYPE_RED_EXCHANGE);
        Utils.setPositionForTag(viewHolder.fragment_button_integral, position);


        final String finalNoNuyMessage = noNuyMessage;
        viewHolder.fragment_button_integral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(finalNoNuyMessage)) {
                    onClickListener.onClick(v);
                } else {
                    Toast.makeText(v.getContext(),finalNoNuyMessage,Toast.LENGTH_SHORT).show();
                }

            }
        });

        ImageLoader.displayImage(Utils.urlOfImage(object.goods_image),viewHolder.imageView_shop);
    }
}