package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;

import com.bumptech.glide.Glide;
import com.szy.common.Util.ImageLoader;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.AppIndex.ShopListItemModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.CheckoutDividerModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.DividerViewHolder;
import com.szy.yishopcustomer.ViewHolder.Index.ShopListItemViewHolder;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 店铺街Adapter
 */

public class ShopStreetAdapter extends RecyclerView.Adapter {

    public static final int ITEM_TYPE_SHOP = 0;
    public static final int ITEM_TYPE_DIVIDER = 1;

    public List<Object> data;
    public View.OnClickListener onClickListener;

    public ShopStreetAdapter() {
        data = new ArrayList<>();
    }

/*    public List<ShopListItemModel> getData() {
        return data;
    }*/

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_TYPE_SHOP:
                return createShopViewHolder(parent);
            case ITEM_TYPE_DIVIDER:
                return createDividerViewHolder(parent);
        }
        return null;
    }


    public RecyclerView.ViewHolder createShopViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_shop_street_item, parent, false);
        return new ShopListItemViewHolder(view);
    }


    public RecyclerView.ViewHolder createDividerViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_goods_list_item_empty, parent, false);
        return new DividerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case ITEM_TYPE_SHOP:
                bindShopViewHolder((ShopListItemViewHolder) holder, position);
                break;
            case ITEM_TYPE_DIVIDER:
                bindDividerViewHolder((DividerViewHolder) holder, position);
                break;
        }
    }

    private void bindShopViewHolder(ShopListItemViewHolder viewHolder, int position) {
        ShopListItemModel item = (ShopListItemModel) data.get(position);

        viewHolder.nameTextView.setText(item.shop_name);
        TextPaint tp = viewHolder.nameTextView.getPaint();
        tp.setFakeBoldText(true);

        if (TextUtils.isEmpty(item.user_id) || item.user_id.equals("0")) {
            viewHolder.imageView.setImageResource(R.mipmap.icon_support_shop_logo);

            //隐藏控件
            viewHolder.rankImageView.setVisibility(View.INVISIBLE);
            viewHolder.location.setVisibility(View.INVISIBLE);
            viewHolder.distanceTextView.setVisibility(View.INVISIBLE);
            viewHolder.synopsisTextView.setVisibility(View.INVISIBLE);
            viewHolder.saleTextView.setVisibility(View.INVISIBLE);
            viewHolder.sleep.setVisibility(View.INVISIBLE);
            //设置数据
            viewHolder.fragment_shop_recommend_num.setVisibility(View.VISIBLE);
            viewHolder.fragment_shop_recommend_num.setText("支持热度 : " + item.recommend_num);
            viewHolder.supportShopTip.setVisibility(View.VISIBLE);

            //加入点击事件
            Utils.setViewTypeForTag(viewHolder.all, ViewType.VIEW_TYPE_SHOP_PREPARE);
            Utils.setPositionForTag(viewHolder.all, position);
            viewHolder.all.setOnClickListener(onClickListener);
            return;
        } else {
            ImageLoader.displayImage(Utils.urlOfImage(item.shop_image), viewHolder.imageView);
            viewHolder.synopsisTextView.setVisibility(View.VISIBLE);
            viewHolder.fragment_shop_recommend_num.setVisibility(View.INVISIBLE);
            viewHolder.supportShopTip.setVisibility(View.INVISIBLE);
        }

        Glide.with(viewHolder.imageView.getContext()).load(Utils.urlOfImage(item.credit_img)).into(viewHolder.rankImageView);

        if (item.distance > 0) {
            viewHolder.distanceTextView.setVisibility(View.VISIBLE);
            viewHolder.location.setVisibility(View.VISIBLE);
            String address = "";
            if(item.address.length()>6){
                address = item.address.substring(0,6)+"...";
            }else {
                address = item.address;
            }

            DecimalFormat df = new DecimalFormat("#.##");
            if (item.distance < 1) {
                viewHolder.distanceTextView.setText(address+" "+df.format(item.distance * 1000) + "m");
            } else {
                viewHolder.distanceTextView.setText(address+" "+df.format(item.distance) + "km");
            }
            
        } else {
            viewHolder.distanceTextView.setVisibility(View.INVISIBLE);
            viewHolder.location.setVisibility(View.INVISIBLE);
        }
        if (item.is_open) {
            viewHolder.saleTextView.setVisibility(View.VISIBLE);
            viewHolder.rankImageView.setVisibility(View.VISIBLE);
            viewHolder.sleep.setVisibility(View.INVISIBLE);

        } else {
            viewHolder.saleTextView.setVisibility(View.INVISIBLE);
            viewHolder.rankImageView.setVisibility(View.INVISIBLE);
            viewHolder.sleep.setVisibility(View.VISIBLE);
        }

        if (!Utils.isNull(item.shop_description)) {
            viewHolder.synopsisTextView.setText(Utils.removeHTMLLabel
                    (item.shop_description));
        } else {
            viewHolder.synopsisTextView.setText(Utils.removeHTMLLabel
                    (item.simply_introduce));
        }

        viewHolder.saleTextView.setText(String.format(viewHolder.saleTextView.getContext()
                .getString(R.string.saleFormat), item.sale_num));

        Utils.setViewTypeForTag(viewHolder.location, ViewType.VIEW_TYPE_SHOP_LOCATION);
        Utils.setPositionForTag(viewHolder.location, position);
        viewHolder.location.setOnClickListener(onClickListener);

        Utils.setViewTypeForTag(viewHolder.distanceTextView, ViewType.VIEW_TYPE_SHOP_LOCATION);
        Utils.setPositionForTag(viewHolder.distanceTextView, position);
        viewHolder.distanceTextView.setOnClickListener(onClickListener);

        Utils.setViewTypeForTag(viewHolder.all, ViewType.VIEW_TYPE_SHOP);
        Utils.setPositionForTag(viewHolder.all, position);
        viewHolder.all.setOnClickListener(onClickListener);
    }

    private void bindDividerViewHolder(DividerViewHolder holder, int position) {
    }

    public int getItemViewType(int position) {
        Object object = data.get(position);
        if (object instanceof ShopListItemModel) {
            return ITEM_TYPE_SHOP;
        } else if (object instanceof CheckoutDividerModel) {
            return ITEM_TYPE_DIVIDER;
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
