package com.szy.yishopcustomer.Adapter.samecity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.TextView;

import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.SameCity.MoreSort.MoreModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wyx
 * @role 分类更多-子item 适配器
 * @time
 */

public class MoreContentAdapter extends RecyclerView.Adapter {

    public List<Object> data;
    public Context mContext;

    public MoreContentAdapter(Context context) {
        this.data = new ArrayList<>();
        this.mContext = context;
    }

    public RecyclerView.ViewHolder createItemView(ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_more_sort_content, parent, false);
        ItemViewHolder holder = new ItemViewHolder(view);
        return holder;
    }


    /***
     * ITEM布局切换
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return createItemView(parent);
    }

    /***
     * ITEM 赋值
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MoreModel.ChildrenBean bean = (MoreModel.ChildrenBean) data.get(position);
        bindItemMore((ItemViewHolder) holder, bean);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_more_content)
        TextView mTextView_Content;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public void bindItemMore(ItemViewHolder holder, final MoreModel.ChildrenBean bean) {
        holder.mTextView_Content.setText(bean.catgName);

        holder.mTextView_Content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mClick != null) {
                    mClick.onItemClick(bean);
                }
            }
        });
    }

    public interface onConItemClick {
        void onItemClick(MoreModel.ChildrenBean bean);
    }

    private onConItemClick mClick;

    public void setClick(onConItemClick click) {
        this.mClick = click;
    }
}
