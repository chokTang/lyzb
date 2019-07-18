package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;

import com.szy.common.Util.ImageLoader;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.DividerModel;
import com.szy.yishopcustomer.ResponseModel.GroupBuy.DataModel;
import com.szy.yishopcustomer.ResponseModel.GroupBuy.GroupBuyModel;
import com.szy.yishopcustomer.Util.BrowserUrlManager;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.DividerViewHolder;
import com.szy.yishopcustomer.ViewHolder.EmptyViewHolder;
import com.szy.yishopcustomer.ViewHolder.GroupBuyBannerViewHolder;
import com.szy.yishopcustomer.ViewHolder.GroupBuyViewHolder;
import com.szy.yishopcustomer.ViewModel.EmptyItemModel;

import java.util.ArrayList;
import java.util.List;

import cn.iwgang.countdownview.CountdownView;

/**
 * Created by 李威 on 16/11/1.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class GroupBuyAdapter extends RecyclerView.Adapter {

    private static final int VIEW_TYPE_GROUP_BUY_BANNER = 0;
    private static final int VIEW_TYPE_GROUP_BUY_LIST = 1;
    private static final int VIEW_TYPE_DIVIDER = 2;
    private static final int VIEW_TYPE_EMPTY = 3;
    public List<Object> data;
    public View.OnClickListener onClickListener;

    public GroupBuyAdapter() {
        this.data = new ArrayList<>();
    }

    public GroupBuyAdapter(List<Object> data) {
        this.data = data;
    }

    public RecyclerView.ViewHolder createBannerViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_group_buy_banner, parent, false);
        return new GroupBuyBannerViewHolder(view);
    }

    public RecyclerView.ViewHolder createDividerViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_divider_four,
                parent, false);
        return new DividerViewHolder(view);
    }

    public RecyclerView.ViewHolder createEmptyViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_default_empty,
                parent, false);
        return new EmptyViewHolder(view);
    }

    public RecyclerView.ViewHolder createListViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group_buy,
                parent, false);
        return new GroupBuyViewHolder(view);
    }

    /* public static String formatDuring(long mss) {
         long days = mss / (60 * 60 * 24);
         long hours = (mss % (60 * 60 * 24)) / (60 * 60);
         long minutes = (mss % (60 * 60)) / (60);
         long seconds = (mss % (60));
         return days + " 天 " + hours + " 小时 " + minutes + " 分 " + seconds + " 秒 ";
     }
 */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_GROUP_BUY_BANNER:
                return createBannerViewHolder(parent);
            case VIEW_TYPE_GROUP_BUY_LIST:
                return createListViewHolder(parent);
            case VIEW_TYPE_DIVIDER:
                return createDividerViewHolder(parent);
            case VIEW_TYPE_EMPTY:
                return createEmptyViewHolder(parent);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case VIEW_TYPE_GROUP_BUY_BANNER:
                bindBannerViewHolder((GroupBuyBannerViewHolder) holder, position);
                break;
            case VIEW_TYPE_GROUP_BUY_LIST:
                bindGroupBuyListViewHolder((GroupBuyViewHolder) holder, position);
                break;
            case VIEW_TYPE_DIVIDER:
                break;
            case VIEW_TYPE_EMPTY:
                bindEmptyViewHolder((EmptyViewHolder) holder, position);
                break;
        }
    }

    public int getItemViewType(int position) {
        Object object = data.get(position);
        if (object instanceof DataModel) {
            return VIEW_TYPE_GROUP_BUY_BANNER;
        } else if (object instanceof GroupBuyModel) {
            return VIEW_TYPE_GROUP_BUY_LIST;
        } else if (object instanceof DividerModel) {
            return VIEW_TYPE_DIVIDER;
        } else if (object instanceof EmptyItemModel) {
            return VIEW_TYPE_EMPTY;
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    private void bindBannerViewHolder(final GroupBuyBannerViewHolder holder, int position) {
        final DataModel dataModel = (DataModel) data.get(position);

        if (Utils.isNull(dataModel.slide_list)) {
            return;
        }


        ArrayList<String> imgs;
        ImageAdapter imageAdapter;
        final BrowserUrlManager mBrowserUrlManager = new BrowserUrlManager();

        imgs = new ArrayList<>();
        holder.viewPager.setAdapter(imageAdapter = new ImageAdapter(holder.viewPager.getContext(), imgs));
        imageAdapter.listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Object obj = view.getTag(R.id.imagePosition);
                int position = obj == null ? 0 : (int) obj;
                try {
                    mBrowserUrlManager.parseUrl(holder.viewPager.getContext(), dataModel.slide_list.get(position).link);
                } catch (Exception e) {
                }
            }
        };


        holder.pageIndicator.setViewPager(holder.viewPager);
        holder.pageIndicator.setSnap(true);

        imgs.clear();
        for (int i = 0; i < dataModel.slide_list.size(); i++) {
            imgs.add(Utils.urlOfImage(dataModel.slide_list.get(i).img));
        }
        holder.viewPager.getMyPagerAdapter().notifyDataSetChanged();


        /*GroupBuyBannerAdapter adapter = new GroupBuyBannerAdapter(dataModel.slide_list);
        adapter.itemPosition = position;
        adapter.onClickListener = onClickListener;
        holder.viewPager.setAdapter(adapter);
        holder.pageIndicator.setViewPager(holder.viewPager);*/
    }

    private void bindEmptyViewHolder(EmptyViewHolder holder, int position) {

    }

    private void bindGroupBuyListViewHolder(final GroupBuyViewHolder holder, int position) {
        GroupBuyModel item = (GroupBuyModel) data.get(position);
        holder.mGroupBuyGoodsName.setText(item.act_name);
        if (!TextUtils.isEmpty(item.min_price)) {
            holder.mGroupBuyMinPrice.setText(item.min_price);
        }
        if (!"0".equals(item.is_finish)) {
            holder.mGroupBuySalesNumber.setText(Utils.formatNum(item.sales_num, false) + "人已抢");
        } else {
            holder.mGroupBuySalesNumber.setText("");
        }

        ImageLoader.displayImage(Utils.urlOfImage(item.act_img), holder.mGroupBuyGoodsImage);

        Utils.setViewTypeForTag(holder.itemView, ViewType.VIEW_TYPE_GROUP);
        Utils.setPositionForTag(holder.itemView, position);
        Utils.setExtraInfoForTag(holder.itemView, Integer.valueOf(item.act_id));
        holder.itemView.setOnClickListener(onClickListener);

        holder.mCvCountdownViewGroupBuy.setVisibility(View.VISIBLE);
        holder.mGroupBuyTip.setText("剩余");

        long time = 0;

        if ("0".equals(item.is_finish)) {
            holder.relativeLayout_price.setVisibility(View.INVISIBLE);
            holder.mGroupBuyTip.setText("距活动开始还剩");
            holder.mGroupBuyButton.setText("抢先看看");
            holder.mGroupBuyButton.setBackgroundResource(R.drawable.green_border_button_two);

            time = item.start_time - item.current_time;
        } else {
            holder.relativeLayout_price.setVisibility(View.VISIBLE);
            holder.mGroupBuyTip.setText("距活动结束还剩");
            holder.mGroupBuyButton.setText("马上抢");
            holder.mGroupBuyButton.setBackgroundResource(R.drawable.enable_button);

            time = item.end_time - item.current_time;

            if (time <= 0) {
                holder.mGroupBuyButton.setEnabled(false);
            }
        }

        holder.mCvCountdownViewGroupBuy.start(time * 1000);

        holder.mCvCountdownViewGroupBuy.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
            @Override
            public void onEnd(CountdownView cv) {
                holder.mCvCountdownViewGroupBuy.setVisibility(View.GONE);
                holder.mGroupBuyTip.setText("团购已经结束啦！");
            }
        });
    }
}