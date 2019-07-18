package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;

import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.SearchHotViewHolder;
import com.szy.yishopcustomer.ViewModel.GroupOnLIstTabModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuzhifeng on 2016/7/19 0019.
 */
public class GroupOnListAttrAdapter extends RecyclerView.Adapter<SearchHotViewHolder> {
    public View.OnClickListener onClickListener;
    public List<GroupOnLIstTabModel> mData;

    public GroupOnListAttrAdapter() {
        this.mData = new ArrayList<GroupOnLIstTabModel>();
    }

    @Override
    public SearchHotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tab,
                parent, false);
        return new SearchHotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchHotViewHolder viewHolder, int position) {
        GroupOnLIstTabModel item = mData.get(position);
        if(item.singleLine) {
            viewHolder.mTextView.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.mTextView.setVisibility(View.VISIBLE);
            viewHolder.mTextView.setText(item.name);
            viewHolder.mTextView.setSelected(item.selected);
            Utils.setViewTypeForTag(viewHolder.mTextView, ViewType.VIEW_TYPE_CATEGORY);
            Utils.setPositionForTag(viewHolder.mTextView, position);
            viewHolder.mTextView.setOnClickListener(onClickListener);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(ArrayList<GroupOnLIstTabModel> data) {
        this.mData = data;
    }
}
