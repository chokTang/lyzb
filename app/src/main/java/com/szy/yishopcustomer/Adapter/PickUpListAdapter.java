package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;

import com.szy.common.Util.ImageLoader;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Goods.PickUpModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.PickUpListViewHolder;

import java.util.ArrayList;

/**
 * Created by Smart on 2017/7/3.
 */

public class PickUpListAdapter extends HeaderFooterAdapter<PickUpModel> {

    public View.OnClickListener onClickListener;

    public PickUpListAdapter() {
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
        View view = inflater.inflate(R.layout.fragment_pickup_list_item, parent, false);
        return new PickUpListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if(getItemViewType(position) == TYPE_NORMAL){
            if(viewHolder instanceof PickUpListViewHolder) {
                //这里加载数据的时候要注意，是从position-1开始，因为position==0已经被header占用了
                int tempposition = position;
                if(mHeaderView != null) {
                    tempposition = position-1;
                }
                PickUpModel object = data.get(tempposition);
                bindShopViewHolder((PickUpListViewHolder) viewHolder, object,
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

    private void bindShopViewHolder(PickUpListViewHolder viewHolder, PickUpModel object, int
            position) {
        ImageLoader.displayImage(Utils.urlOfImage(object.pickup_images), viewHolder.imageView_pickup);
        viewHolder.textView_pickup_name.setText("自提点名称："+object.pickup_name);
        viewHolder.textView_pickup_address.setText(object.pickup_address);
        if(!Utils.isNull(object.pickup_desc)){
            viewHolder.textView_pickup_desc.setText("商家推荐："  + Utils.ToDBC(object.pickup_desc));
        }else{
            viewHolder.textView_pickup_desc.setVisibility(View.GONE);
        }

        if(TextUtils.isEmpty(object.pickup_tel)) {
            viewHolder.view_line.setVisibility(View.GONE);
            viewHolder.imageView_pickup_tel.setVisibility(View.GONE);
        } else {
            Utils.setViewTypeForTag(viewHolder.imageView_pickup_tel, ViewType.VIEW_TYPE_PHONE);
            Utils.setPositionForTag(viewHolder.imageView_pickup_tel, position);
            viewHolder.imageView_pickup_tel.setOnClickListener(onClickListener);
        }

        Utils.setViewTypeForTag(viewHolder.itemView, ViewType.VIEW_TYPE_ITEM);
        Utils.setPositionForTag(viewHolder.itemView, position);
        viewHolder.itemView.setOnClickListener(onClickListener);
    }

}