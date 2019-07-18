package com.lyzb.jbx.adapter.home.first;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.like.longshaolib.adapter.rvhelper.DividerItemDecoration;
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.circle.DynamicCircleListAdapter;
import com.lyzb.jbx.adapter.circle.HomeHeaderCircleAdapter;
import com.lyzb.jbx.inter.IRecycleAnyClickListener;
import com.lyzb.jbx.model.circle.CircleModel;
import com.lyzb.jbx.model.dynamic.DynamicModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页-圈子-适配器
 */
public class CircleAdapter extends DynamicCircleListAdapter {

    private final static int TYPE_HEADER = 0x624;
    private List<CircleModel> mCircleList = new ArrayList<>();
    private IRecycleAnyClickListener anyClickListener;

    public CircleAdapter(Context context, List<DynamicModel> list) {
        super(context, list);
        _list.add(0, new DynamicModel());//起站位作用
    }

    @Override
    protected void convert(BaseViewHolder holder, DynamicModel item) {
        if (holder.getItemViewType() == TYPE_HEADER) {//头部圈子
            CircleHeaderHolder headerHolder = (CircleHeaderHolder) holder;
            headerHolder.circleAdapter.update(mCircleList);
        }
        super.convert(holder, item);
    }

    @Override
    protected BaseViewHolder onChildCreateViewHolder(int viewType, ViewGroup parent) {
        if (viewType == TYPE_HEADER) {
            return new CircleHeaderHolder(_context, getItemView(R.layout.recycle_circle_header, parent));
        }
        return super.onChildCreateViewHolder(viewType, parent);
    }

    @Override
    protected int getChildItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        return super.getChildItemViewType(position);
    }

    public void noticeHeader(List<CircleModel> list) {
        mCircleList.clear();
        mCircleList.add(0, new CircleModel());//添加【更多圈子的位置】
        mCircleList.addAll(list);

        notifyItemChanged(0);
    }

    @Override
    public void update(List<DynamicModel> items) {
        _list.clear();
        _list.add(0, new DynamicModel());
        _list.addAll(items);
        notifyDataSetChanged();
    }

    class CircleHeaderHolder extends BaseViewHolder {

        RecyclerView recycle_home_circle_item;
        HomeHeaderCircleAdapter circleAdapter;

        public CircleHeaderHolder(Context context, View itemView) {
            super(context, itemView);
            recycle_home_circle_item = itemView.findViewById(R.id.recycle_home_circle_item);
            circleAdapter = new HomeHeaderCircleAdapter(context, null);
            circleAdapter.setLayoutManager(recycle_home_circle_item, LinearLayoutManager.HORIZONTAL);
            recycle_home_circle_item.addItemDecoration(new DividerItemDecoration(DividerItemDecoration.HORIZONTAL_LIST, R.drawable.listdivider_white_10));
            recycle_home_circle_item.setAdapter(circleAdapter);

            recycle_home_circle_item.addOnItemTouchListener(new OnRecycleItemClickListener() {
                @Override
                public void onItemClick(BaseRecyleAdapter adapter, View view, int position) {
                    if (anyClickListener != null) {
                        anyClickListener.onItemClick(view, position, adapter.getPositionModel(position));
                    }
                }
            });
        }
    }

    public void setAnyClickListener(IRecycleAnyClickListener anyClickListener) {
        this.anyClickListener = anyClickListener;
    }
}
