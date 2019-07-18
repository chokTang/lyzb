package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.*;

import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.DepositCardModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.DepositCradShopViewHolder;

import java.util.ArrayList;

/**
 * Created by Smart on 2017/7/3.
 */

public class DepositCardShopAdapter extends HeaderFooterAdapter<DepositCardModel.DataBean.ListBean> {

    public View.OnClickListener onClickListener;

    public DepositCardShopAdapter() {
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
        View view = inflater.inflate(R.layout.fragment_deposit_card_shop_item, parent, false);
        return new DepositCradShopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if(getItemViewType(position) == TYPE_NORMAL){
            int tempposition = position;
            if(mHeaderView != null) {
                tempposition = position-1;
            }
            Object object = data.get(tempposition);

            if(viewHolder instanceof DepositCradShopViewHolder) {
                bindShopViewHolder((DepositCradShopViewHolder) viewHolder, (DepositCardModel.DataBean.ListBean) object,
                        tempposition);
                return;
            }
            return;
        }else{
            return;
        }
    }

    private void bindShopViewHolder(DepositCradShopViewHolder viewHolder, DepositCardModel.DataBean.ListBean object, int
            position) {
        viewHolder.textView_shop_name.setText("店铺："+object.shop_name);
        viewHolder.textView_card_name.setText("名称："+object.type_name);
        viewHolder.textView_card_number.setText("卡号："+object.card_number);

//        user_range
//        0 全部商品
//        1 指定商品
//        2 指定分类
        String limit = "";
        switch (object.use_range){
            case "0":
                limit = "限品类：全部商品";
                break;
            case "1":
                limit = "限品类：指定商品";
                break;
            case "2":
                limit = "限品类：指定分类";
                break;
        }

        viewHolder.textView_card_limit.setText(limit);
        viewHolder.textView_card_price.setText("金额：" + object.amount + "元");
        viewHolder.textView_used_time.setText("使用日期："+Utils.times(object.start_time,"yyyy-MM-dd") + " ~ " + Utils.times(object.end_time,"yyyy-MM-dd"));

        SpannableStringBuilder style = new SpannableStringBuilder("¥ ");
        SpannableStringBuilder ccc = new SpannableStringBuilder(object.surplus_amount);
        ccc.setSpan(new AbsoluteSizeSpan(22, true), 0, object.surplus_amount.length(), Spannable
                .SPAN_EXCLUSIVE_INCLUSIVE);
        style.append("");
        style.append(ccc);

        viewHolder.textView_par_value.setText(style);

        if("0".equals(object.is_used)) {
            viewHolder.imageView_used_state.setVisibility(View.GONE);
        } else {
            viewHolder.imageView_used_state.setVisibility(View.VISIBLE);
        }

        Utils.setViewTypeForTag(viewHolder.textView_shop_name, ViewType.VIEW_TYPE_SHOP);
        Utils.setPositionForTag(viewHolder.textView_shop_name, position);
        Utils.setExtraInfoForTag(viewHolder.textView_exchange, Integer.valueOf
                (object.shop_id));

        viewHolder.textView_shop_name.setOnClickListener(onClickListener);


        Utils.setViewTypeForTag(viewHolder.textView_exchange, ViewType.VIEW_TYPE_ITEM);
        Utils.setPositionForTag(viewHolder.textView_exchange, position);
        Utils.setExtraInfoForTag(viewHolder.textView_exchange, Integer.valueOf
                (object.card_id));

        viewHolder.textView_exchange.setOnClickListener(onClickListener);

        if("2".equals(object.is_over)  ) {
            viewHolder.linearlayout_left_bg.setBackgroundResource(R.mipmap.bg_bonus_border_left_dark);
            viewHolder.imageView_over_state.setVisibility(View.VISIBLE);
        } else {
            viewHolder.linearlayout_left_bg.setBackgroundResource(R.mipmap.bg_bonus_border_left);
            viewHolder.imageView_over_state.setVisibility(View.GONE);
        }

        if (Double.parseDouble(object.surplus_amount) <= 0) {
            viewHolder.linearlayout_left_bg.setBackgroundResource(R.mipmap.bg_bonus_border_left_dark);
        }
    }
}