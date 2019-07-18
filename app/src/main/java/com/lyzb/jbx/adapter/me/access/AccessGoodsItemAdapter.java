package com.lyzb.jbx.adapter.me.access;

import android.content.Context;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.lyzb.jbx.R;
import com.lyzb.jbx.model.me.access.AccessGoodsItemModel;

import java.util.List;

public class AccessGoodsItemAdapter extends BaseRecyleAdapter<AccessGoodsItemModel> {

    public AccessGoodsItemAdapter(Context context, List<AccessGoodsItemModel> list) {
        super(context, R.layout.recycle_access_goods_item, list);
    }

    @Override
    protected void convert(BaseViewHolder holder, AccessGoodsItemModel item) {
        holder.setText(R.id.tv_goods_day,item.getGoodsName());
        holder.setText(R.id.tv_goods_number,"x"+item.getGoodsNumber());
        holder.setText(R.id.tv_goods_number,"x"+item.getGoodsNumber());
    }
}
