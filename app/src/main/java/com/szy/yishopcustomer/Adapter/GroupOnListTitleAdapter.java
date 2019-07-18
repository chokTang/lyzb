package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;

import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.TabViewHolder;
import com.szy.yishopcustomer.ViewModel.GroupOnLIstTabModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuzhifeng on 2016/7/19 0019.
 */
public class GroupOnListTitleAdapter extends RecyclerView.Adapter<TabViewHolder> {
    public View.OnClickListener onClickListener;
    public List<GroupOnLIstTabModel> mData;

    public GroupOnListTitleAdapter() {
        this.mData = new ArrayList<GroupOnLIstTabModel>();
    }

    @Override
    public TabViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group_on_list_title,
                parent, false);
        return new TabViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TabViewHolder viewHolder, int position) {
        GroupOnLIstTabModel item = mData.get(position);
        viewHolder.titleTextView.setText(item.name);
        viewHolder.titleTextView.setSelected(item.selected);
        Utils.setViewTypeForTag(viewHolder.titleTextView, ViewType.VIEW_TYPE_CATEGORY);
        Utils.setPositionForTag(viewHolder.titleTextView, position);
        viewHolder.titleTextView.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(ArrayList<GroupOnLIstTabModel> data) {
        this.mData = data;
    }
}
