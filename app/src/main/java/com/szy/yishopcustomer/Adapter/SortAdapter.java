package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;

import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.SortModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.SortItemViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 宗仁 on 16/8/22.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class SortAdapter extends RecyclerView.Adapter<SortItemViewHolder> {
    public List<SortModel> data;
    public View.OnClickListener onClickListener;
    public boolean removeCompositeSort = false;

    public SortAdapter() {
        data = new ArrayList<>();
    }

    @Override
    public SortItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_goods_list_sort_item, parent, false);
        return new SortItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SortItemViewHolder holder, int position) {
        holder.textView.setText(data.get(position).name);

        boolean is = false;
        if ("1".equals(data.get(position).selected)) {
            is = true;
        }
        holder.textView.setSelected(is);
        holder.imageView.setSelected(is);
        if (is) {
            if (data.get(position).order.equals("DESC")) {
                holder.imageView.setSelected(false);
            } else {
                holder.imageView.setSelected(true);
            }
            holder.imageView.setVisibility(View.VISIBLE);
        } else {
            holder.imageView.setVisibility(View.INVISIBLE);
        }

        Utils.setPositionForTag(holder.itemView, position + (removeCompositeSort ? 1 : 0));
        Utils.setViewTypeForTag(holder.itemView, ViewType.VIEW_TYPE_SORT_ITEM);
        holder.itemView.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<SortModel> list) {
        data = list;
    }
}
