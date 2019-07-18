package com.szy.yishopcustomer.Adapter;

import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.TextView;
import android.widget.Toast;

import com.szy.common.Util.ImageLoader;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Checkout.CheckoutDividerModel;
import com.szy.yishopcustomer.ResponseModel.GroupBuyList.GroupBuylistModel;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.DividerViewHolder;
import com.szy.yishopcustomer.ViewHolder.GroupBuyListViewHolder;

import java.util.ArrayList;
import java.util.List;

import io.github.erehmi.countdown.CountDownTask;
import io.github.erehmi.countdown.CountDownTimers;

/**
 * Created by 李威 on 16/11/1.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class GroupBuyListAdapter extends RecyclerView.Adapter {

    private static final int VIEW_TYPE_GROUP_BUY_LIST = 0;
    private static final int VIEW_DIVIDER = 1;
    public List<Object> data;
    public View.OnClickListener onClickListener;
    private boolean is = true;

    public View.OnClickListener xiajiaClickListener;

    CountDownTask countDownTask;

    public GroupBuyListAdapter() {
        this.data = new ArrayList<>();
        xiajiaClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(App.getInstance().mContext, "商品已下架", Toast.LENGTH_SHORT).show();
            }
        };
    }

    public void setCountDownTask(CountDownTask countDownTask) {
        this.countDownTask = countDownTask;
        notifyDataSetChanged();
    }


    public GroupBuyListAdapter(List<Object> data) {
        this.data = data;
    }

    public RecyclerView.ViewHolder createDividerViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_goods_list_item_empty, parent, false);
        return new DividerViewHolder(view);
    }

    public RecyclerView.ViewHolder createListViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_group_buy_list, parent, false);
        return new GroupBuyListViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_GROUP_BUY_LIST:
                return createListViewHolder(parent);
            case VIEW_DIVIDER:
                return createDividerViewHolder(parent);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case VIEW_TYPE_GROUP_BUY_LIST:
                bindGroupBuyListViewHolder((GroupBuyListViewHolder) holder, position);
                break;
            case VIEW_DIVIDER:
                bindDividerViewHolder((DividerViewHolder) holder, position);
        }
    }

    public int getItemViewType(int position) {
        Object object = data.get(position);
        if (object instanceof GroupBuylistModel) {
            return VIEW_TYPE_GROUP_BUY_LIST;
        } else if (object instanceof CheckoutDividerModel) {
            return VIEW_DIVIDER;
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private void bindDividerViewHolder(DividerViewHolder holder, int position) {
    }

    private void bindGroupBuyListViewHolder(final GroupBuyListViewHolder viewHolder, int position) {
        final GroupBuylistModel item = (GroupBuylistModel) data.get(position);
        viewHolder.mGoodsName.setText(item.goods_name);
        viewHolder.mGoodsActPrice.setText(Utils.formatMoney(viewHolder.mGoodsActPrice.getContext(), item.act_price));
        viewHolder.mGoodsPrice.setText(Utils.formatMoney(viewHolder.mGoodsPrice.getContext(), item.goods_price));
        viewHolder.mGoodsPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        viewHolder.mGoodsSales.setText(item.total_sale_count + "人已抢购");
        if (!Utils.isNull(item.goods_image)) {
            ImageLoader.displayImage(Utils.urlOfImage(item.goods_image), viewHolder.mGoodsThumb);
        }
        if (item.is_buy == 0) {
            viewHolder.mGoodsButton.setVisibility(View.GONE);
            viewHolder.mGoodsSales.setVisibility(View.GONE);
            viewHolder.mSoldOut.setVisibility(View.VISIBLE);
            //不管能不能购买都能点进商品详情
            //viewHolder.itemView.setOnClickListener(xiajiaClickListener);

            Utils.setViewTypeForTag(viewHolder.itemView, ViewType.VIEW_TYPE_BUY_NOW);
            Utils.setPositionForTag(viewHolder.itemView, position);
            Utils.setExtraInfoForTag(viewHolder.itemView, Integer.valueOf(item.goods_id));
            viewHolder.itemView.setOnClickListener(onClickListener);

        } else {
            viewHolder.mGoodsButton.setVisibility(View.VISIBLE);
            viewHolder.mGoodsSales.setVisibility(View.VISIBLE);
            viewHolder.mSoldOut.setVisibility(View.GONE);

            Utils.setViewTypeForTag(viewHolder.itemView, ViewType.VIEW_TYPE_BUY_NOW);
            Utils.setPositionForTag(viewHolder.itemView, position);
            Utils.setExtraInfoForTag(viewHolder.itemView, Integer.valueOf(item.goods_id));
            viewHolder.itemView.setOnClickListener(onClickListener);
        }
        viewHolder.mFloatTextProgressBar.setProgress(item.rate);

        if ("0".equals(item.is_finish)) {
            viewHolder.linearlayout_countdown.setVisibility(View.VISIBLE);
            viewHolder.linearlayout_last_line.setVisibility(View.GONE);
            viewHolder.mGoodsButton.setText("去看看");
            viewHolder.mGoodsButton.setBackgroundResource(R.drawable.green_border_button_two);


            long time = CountDownTask.elapsedRealtime() + (item.start_time * 1000 - System.currentTimeMillis());

            viewHolder.textView_countdown_prefix.setText("限量" + item.act_stock + "件 | 距开抢还剩");

            int CD_INTERVAL = 1000;
            if(countDownTask != null) {
            countDownTask.until(viewHolder.textView_countdown_prefix, time, CD_INTERVAL, new CountDownTimers.OnCountDownListener() {
                @Override
                public void onTick(View view, long millisUntilFinished) {
                    ((TextView) view).setText("限量" + item.act_stock + "件 | 距开抢还剩" + Utils.formatSeconds(millisUntilFinished/1000));
                }

                @Override
                public void onFinish(View view) {
                    ((TextView) view).setText("限量" + item.act_stock + "件 | 活动已经开始");
                }
            });}


//            viewHolder.mCvCountdownViewGroupBuy.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
//                @Override
//                public void onEnd(CountdownView cv) {
//                    viewHolder.mCvCountdownViewGroupBuy.setVisibility(View.GONE);
//                    viewHolder.textView_countdown_prefix.setText("团购已经结束啦！");
//                }
//            });
//
//            if (time <= 0) {
//                viewHolder.mGoodsButton.setEnabled(false);
//                viewHolder.mGoodsButton.setBackgroundResource(R.drawable.gray_border_button_one);
//            }

        } else {
            viewHolder.linearlayout_countdown.setVisibility(View.GONE);
            viewHolder.linearlayout_last_line.setVisibility(View.VISIBLE);
            viewHolder.mGoodsButton.setText("马上抢");
            viewHolder.mGoodsButton.setBackgroundResource(R.drawable.enable_button);
        }
    }

}
