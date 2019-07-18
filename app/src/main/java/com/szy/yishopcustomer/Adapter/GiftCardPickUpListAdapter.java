package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;

import com.szy.common.Util.ImageLoader;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.GiftCard.GiftCardPickUpListModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.GiftCardPickUpListViewHolder;

import java.util.ArrayList;

/**
 * Created by Smart on 2017/11/17.
 */

public class GiftCardPickUpListAdapter extends HeaderFooterAdapter<GiftCardPickUpListModel.DataBean.ListBean> {

    public View.OnClickListener onClickListener;
    public String shop_name;

    public GiftCardPickUpListAdapter() {
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
        View view = inflater.inflate(R.layout.fragment_gift_card_pickup_list_item, parent, false);
        return new GiftCardPickUpListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if(getItemViewType(position) == TYPE_NORMAL){
            if(viewHolder instanceof GiftCardPickUpListViewHolder) {
                //这里加载数据的时候要注意，是从position-1开始，因为position==0已经被header占用了
                int tempposition = position;
                if(mHeaderView != null) {
                    tempposition = position-1;
                }
                GiftCardPickUpListModel.DataBean.ListBean object = data.get(tempposition);
                bindViewHolder((GiftCardPickUpListViewHolder) viewHolder, object,
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

    private void bindViewHolder(GiftCardPickUpListViewHolder viewHolder, final GiftCardPickUpListModel.DataBean.ListBean object, final int
            position) {

        viewHolder.textView_mall_name.setText(shop_name);
        viewHolder.textView_goods_name.setText(object.goods_name);
        viewHolder.textView_goods_price.setText(object.goods_price_format);

        Utils.setViewTypeForTag(viewHolder.itemView, ViewType.VIEW_TYPE_GOODS);
        Utils.setPositionForTag(viewHolder.itemView, position);
        viewHolder.itemView.setOnClickListener(onClickListener);

        Utils.setViewTypeForTag(viewHolder.fragment_button_integral, ViewType.VIEW_TYPE_SUBMIT);
        Utils.setPositionForTag(viewHolder.fragment_button_integral, position);
        viewHolder.fragment_button_integral.setOnClickListener(onClickListener);

        ImageLoader.displayImage(Utils.urlOfImage(object.goods_image),viewHolder.imageView_shop);

    }
}