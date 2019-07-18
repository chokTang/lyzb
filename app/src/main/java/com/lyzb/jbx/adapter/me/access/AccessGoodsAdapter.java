package com.lyzb.jbx.adapter.me.access;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.like.longshaolib.adapter.rvhelper.DividerItemDecoration;
import com.like.utilslib.date.DateStyle;
import com.like.utilslib.date.DateUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.model.me.access.AccessGoodsModel;

import java.util.List;

public class AccessGoodsAdapter extends BaseRecyleAdapter<AccessGoodsModel> {

    public AccessGoodsAdapter(Context context, List<AccessGoodsModel> list) {
        super(context, R.layout.recycle_access_goods, list);
    }

    @Override
    protected void convert(BaseViewHolder holder, AccessGoodsModel item) {
        GoodsHolder goodsHolder = (GoodsHolder) holder;
        goodsHolder.itemAdapter.update(item.getList());

        goodsHolder.setText(R.id.tv_goods_day, DateUtil.StringToString(item.getAddTime(), DateStyle.MM_DD_CN));
        goodsHolder.setText(R.id.tv_total_number, String.format("交易笔数\n%d", item.getOrderCount()));
        goodsHolder.setText(R.id.tv_total_number, String.format("合计金额\n%.2f", item.getOrderAmount()));
    }

    @Override
    protected BaseViewHolder onChildCreateViewHolder(int viewType, ViewGroup parent) {
        return new GoodsHolder(_context, getItemView(R.layout.recycle_access_goods, parent));
    }

    public class GoodsHolder extends BaseViewHolder {
        AccessGoodsItemAdapter itemAdapter;
        RecyclerView recycle_goods_list;

        public GoodsHolder(Context context, View itemView) {
            super(context, itemView);
            recycle_goods_list = itemView.findViewById(R.id.recycle_goods_list);
            itemAdapter = new AccessGoodsItemAdapter(context, null);
            itemAdapter.setLayoutManager(recycle_goods_list);
            recycle_goods_list.addItemDecoration(new DividerItemDecoration(DividerItemDecoration.VERTICAL_LIST));
            recycle_goods_list.setAdapter(itemAdapter);
        }
    }
}
