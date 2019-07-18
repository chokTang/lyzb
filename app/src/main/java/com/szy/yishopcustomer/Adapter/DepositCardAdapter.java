package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.*;

import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.DepositCardModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.DepositCradViewHolder;

import java.util.ArrayList;

/**
 * Created by Smart on 2017/7/3.
 */

public class DepositCardAdapter extends HeaderFooterAdapter<DepositCardModel.DataBean.ListBean> {

    public View.OnClickListener onClickListener;

    public DepositCardAdapter() {
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
        return createShopViewHolder(inflater, parent);
    }

    private RecyclerView.ViewHolder createShopViewHolder(LayoutInflater inflater, ViewGroup
            parent) {
        View view = inflater.inflate(R.layout.fragment_deposit_card_item, parent, false);
        return new DepositCradViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        if(getItemViewType(position) == TYPE_NORMAL){
            if(viewHolder instanceof DepositCradViewHolder) {
                //这里加载数据的时候要注意，是从position-1开始，因为position==0已经被header占用了
                DepositCardModel.DataBean.ListBean object = data.get(position);
                bindShopViewHolder((DepositCradViewHolder) viewHolder, object,
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

    private void bindShopViewHolder(DepositCradViewHolder viewHolder, DepositCardModel.DataBean.ListBean object, int
            position) {
        viewHolder.textView_card_name.setText("名称："+object.type_name);
        viewHolder.textView_card_number.setText("卡号："+object.card_number);
        viewHolder.textView_used_time.setText("使用日期："+Utils.times(object.use_time,"yyyy-MM-dd HH:mm:ss"));


        SpannableStringBuilder style = new SpannableStringBuilder("¥ ");
        SpannableStringBuilder ccc = new SpannableStringBuilder(object.amount);
        ccc.setSpan(new AbsoluteSizeSpan(22, true), 0, object.amount.length(), Spannable
                .SPAN_EXCLUSIVE_INCLUSIVE);
        style.append("");
        style.append(ccc);

        viewHolder.textView_par_value.setText(style);
    }

}