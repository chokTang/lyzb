package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;

import com.szy.common.Util.ImageLoader;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.ShopCartViewHolder;

import java.util.ArrayList;

/**
 * Created by Smart on 2017/7/14.
 */

public class ShopCartAdapter extends HeaderFooterAdapter<com.szy.yishopcustomer.ResponseModel.CartIndex.GoodsModel> {

    public View.OnClickListener onClickListener;

    public ShopCartAdapter() {
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
        return createViewHolder(inflater, parent);
    }

    private RecyclerView.ViewHolder createViewHolder(LayoutInflater inflater, ViewGroup
            parent) {
        View view = inflater.inflate(R.layout.fragment_shop_cart_item, parent, false);
        return new ShopCartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            if (viewHolder instanceof ShopCartViewHolder) {
                //这里加载数据的时候要注意，是从position-1开始，因为position==0已经被header占用了
                int tempposition = position;
                if (mHeaderView != null) {
                    tempposition = position - 1;
                }
                com.szy.yishopcustomer.ResponseModel.CartIndex.GoodsModel object = data.get(tempposition);
                bindShopViewHolder((ShopCartViewHolder) viewHolder, object,
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

    private void bindShopViewHolder(ShopCartViewHolder viewHolder, com.szy.yishopcustomer.ResponseModel.CartIndex.GoodsModel object, int
            position) {

        ImageLoader.displayImage(Utils.urlOfImage(object.sku_image), viewHolder.tipImageView);
        viewHolder.textView_goods_name.setText(object.goods_name);

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < object.spec_names.size(); i++) {
            sb.append(object.spec_names.get(i));
            if (i == object.spec_names.size() - 1) {
                sb.append(",");
            }
        }

        viewHolder.textView_use_condition.setText(sb.toString());
        viewHolder.textView_shop_integral.setText(object.goods_price_format);

        viewHolder.item_cart_goods_number.setText(object.goods_number + "");


        Utils.setViewTypeForTag(viewHolder.item_cart_goods_add_button, ViewType.VIEW_TYPE_ADD);
        Utils.setPositionForTag(viewHolder.item_cart_goods_add_button, position);
        viewHolder.item_cart_goods_add_button.setOnClickListener(onClickListener);

        Utils.setViewTypeForTag(viewHolder.item_cart_goods_minus_button, ViewType.VIEW_TYPE_MINUS);
        Utils.setPositionForTag(viewHolder.item_cart_goods_minus_button, position);
        viewHolder.item_cart_goods_minus_button.setOnClickListener(onClickListener);

        if ("0".equals(object.sku_number)) {
            viewHolder.imageView_check.setVisibility(View.INVISIBLE);
            viewHolder.textView_invalid.setVisibility(View.VISIBLE);

            viewHolder.linearlayout_number.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.imageView_check.setVisibility(View.VISIBLE);
            viewHolder.textView_invalid.setVisibility(View.INVISIBLE);

            viewHolder.linearlayout_number.setVisibility(View.VISIBLE);

            viewHolder.imageView_check.setSelected(object.select);

            Utils.setViewTypeForTag(viewHolder.itemView, ViewType.VIEW_TYPE_ITEM);
            Utils.setPositionForTag(viewHolder.itemView, position);
            viewHolder.itemView.setOnClickListener(onClickListener);
        }
    }

    public void selectAll(boolean flag) {
        for (int i = 0; i < data.size(); i++) {
            if (flag) {
                data.get(i).select = true;
            } else {
                data.get(i).select = false;
            }
        }

        notifyDataSetChanged();
    }
}