package com.lyzb.jbx.adapter.me;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener;
import com.lyzb.jbx.R;
import com.lyzb.jbx.inter.IRecycleAnyClickListener;
import com.lyzb.jbx.model.me.FuctionItemModel;

import java.util.List;

public class MeFunctionAdapter extends BaseRecyleAdapter<FuctionItemModel> {

    private IRecycleAnyClickListener clickListener;

    public MeFunctionAdapter(Context context, List<FuctionItemModel> list) {
        super(context, R.layout.recycle_me_item, list);
    }

    @Override
    protected void convert(BaseViewHolder holder, FuctionItemModel item) {
        Hodler itemHolder = (Hodler) holder;
        itemHolder.setText(R.id.tv_item_name, item.getDisplay());
        itemHolder.itemAdapter.update(item.getChildResource());
    }

    @Override
    protected BaseViewHolder onChildCreateViewHolder(int viewType, ViewGroup parent) {
        return new Hodler(_context, getItemView(R.layout.recycle_me_item, parent));
    }

    public class Hodler extends BaseViewHolder {

        RecyclerView recycle_me_item;
        MeFunctionItemAdapter itemAdapter;

        public Hodler(Context context, View itemView) {
            super(context, itemView);
            recycle_me_item = itemView.findViewById(R.id.recycle_me_item);
            recycle_me_item.setNestedScrollingEnabled(false);
            itemAdapter = new MeFunctionItemAdapter(_context, null);
            itemAdapter.setGridLayoutManager(recycle_me_item, 4);
            recycle_me_item.setAdapter(itemAdapter);

            recycle_me_item.addOnItemTouchListener(new OnRecycleItemClickListener() {
                @Override
                public void onItemClick(BaseRecyleAdapter adapter, View view, int position) {
                    if (clickListener != null) {
                        clickListener.onItemClick(view, position, itemAdapter.getPositionModel(position));
                    }
                }
            });
        }
    }

    public void setClickListener(IRecycleAnyClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
