package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;

import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.CardUsageModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.CardUsageViewHolder;

import java.util.ArrayList;

/**
 * Created by Smart on 2017/7/6.
 */

public class CardUsageAdapter extends HeaderFooterAdapter<CardUsageModel.DataBean.ListBean> {

    public View.OnClickListener onClickListener;

    public CardUsageAdapter() {
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
        View view = inflater.inflate(R.layout.fragment_card_usage_item, parent, false);
        return new CardUsageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if(getItemViewType(position) == TYPE_NORMAL){
            if(viewHolder instanceof CardUsageViewHolder) {
                //这里加载数据的时候要注意，是从position-1开始，因为position==0已经被header占用了
                int tempposition = position;
                if(mHeaderView != null) {
                    tempposition = position-1;
                }
                CardUsageModel.DataBean.ListBean object = data.get(tempposition);
                bindShopViewHolder((CardUsageViewHolder) viewHolder, object,
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

    private void bindShopViewHolder(CardUsageViewHolder viewHolder, CardUsageModel.DataBean.ListBean object, int
            position) {
        viewHolder.textView_card_number.setText("编号：" + object.log_id);
        if(Double.parseDouble(object.amount) > 0) {
            viewHolder.textView_card_amount.setText("+" + object.amount);
        } else {
            viewHolder.textView_card_amount.setText(object.amount);
        }
        String type= "消费";
        if("0".equals(object.use_type)) {
            type = "消费";
        } else {
            type = "退回";
        }
        viewHolder.textView_card_type.setText("使用类型：" +type);
        viewHolder.textView_card_order_number.setText("消费订单号：" + object.order_sn);
        viewHolder.textView_card_use_time.setText(Utils.times(object.add_time));
    }
}