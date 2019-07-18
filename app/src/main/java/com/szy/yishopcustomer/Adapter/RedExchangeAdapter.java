package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;

import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.RedExchangeViewHolder;
import com.szy.yishopcustomer.ViewModel.RedExchangeModel;

import java.util.ArrayList;

/**
 * Created by Smart on 2017/7/3.
 */

public class RedExchangeAdapter extends HeaderFooterAdapter<RedExchangeModel.DataBean.ListBean> {

    public View.OnClickListener onClickListener;

    public String site_name;

    public RedExchangeAdapter() {
        data = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (mHeaderView != null && viewType == TYPE_HEADER) {
            return new ListHolder(mHeaderView);
        }
        if (mFooterView != null && viewType == TYPE_FOOTER) {
            return new ListHolder(mFooterView);
        }
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return createShopViewHolder(inflater, parent);
    }

    private RecyclerView.ViewHolder createShopViewHolder(LayoutInflater inflater, ViewGroup
            parent) {
        View view = inflater.inflate(R.layout.fragment_red_exchange_item, parent, false);
        return new RedExchangeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        if (getItemViewType(position) == TYPE_NORMAL) {
            if (viewHolder instanceof RedExchangeViewHolder) {
                //这里加载数据的时候要注意，是从position-1开始，因为position==0已经被header占用了
                int tempposition = position;
                if (mHeaderView != null) {
                    tempposition = position - 1;
                }
                Object object = data.get(tempposition);
                bindShopViewHolder((RedExchangeViewHolder) viewHolder, (RedExchangeModel.DataBean.ListBean) object,
                        tempposition);
                return;
            }
            return;
        } else if (getItemViewType(position) == TYPE_HEADER) {
            return;
        } else {
            return;
        }
    }

    private void bindShopViewHolder(RedExchangeViewHolder viewHolder, RedExchangeModel.DataBean.ListBean object, int
            position) {
        viewHolder.textView_red_amount.setText(object.bonus_amount);

        if(!"0".equals(object.shop_id)) {
            viewHolder.textView_shop_name.setText("发行方：" + object.shop_name);
        } else {
            viewHolder.textView_shop_name.setText("发行方：" + site_name);
        }

        String limit = "";
        if ("0".equals(object.use_range)) {
            if ("0".equals(object.shop_id)) {
                limit = "全场通用";
            } else {
                limit = "全店通用";
            }
        } else if ("1".equals(object.use_range)) {
            if ("0".equals(object.shop_id)) {
                limit = "指定分类";
            } else {
                limit = "指定商品";
            }
        }
        viewHolder.textView_limit.setText("限品类：" + limit);
        viewHolder.textView_number.setText("发行数量：" + object.bonus_number);
        viewHolder.textView_effective_time.setText(object.start_time_format + " ~ " + object.end_time_format);
        viewHolder.textView_integral_required.setText(object.bonus_data.exchange_points);
        if (object.min_goods_amount.equals("0.00")) {
            viewHolder.textView_use_limit.setText("");
        } else {
            viewHolder.textView_use_limit.setText("满" + object.min_goods_amount + "元可用");
        }

        viewHolder.textView_exchange_figures.setText(object.receive_number + "人兑换");

        if ("1".equals(object.is_enable)) {
            viewHolder.textView_exchange.setText("立即兑换");
            Utils.setViewTypeForTag(viewHolder.textView_exchange, ViewType.VIEW_TYPE_RED_EXCHANGE);
            Utils.setPositionForTag(viewHolder.textView_exchange, position);
            viewHolder.textView_exchange.setOnClickListener(onClickListener);
        } else {
            viewHolder.textView_exchange.setText("今已领完");
            viewHolder.textView_exchange.setOnClickListener(null);
        }

        if("0".equals(object.bonus_data.exchange_limit)) {
            viewHolder.textView_exchange_time.setText("不限日期");
        } else {
            viewHolder.textView_exchange_time.setText(Utils.times(object.bonus_data.exchange_start_time,"yyyy-MM-dd") + " ~ " + Utils.times(object.bonus_data.exchange_end_time,"yyyy-MM-dd"));
        }

    }
}