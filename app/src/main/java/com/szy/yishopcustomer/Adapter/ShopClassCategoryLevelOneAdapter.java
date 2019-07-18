package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;

import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.TabViewHolder;
import com.szy.yishopcustomer.ViewModel.ShopClassModel;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by liwei on 17/5/20.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class ShopClassCategoryLevelOneAdapter extends RecyclerView.Adapter<TabViewHolder> {
    public View.OnClickListener onClickListener;
    public List<ShopClassModel> mData;

    public ShopClassCategoryLevelOneAdapter(List<ShopClassModel> data) {
        this.mData = data;
    }

    @Override
    public TabViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_left_tab,
                parent, false);
        return new TabViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TabViewHolder viewHolder, int position) {
        ShopClassModel item = mData.get(position);
        viewHolder.titleTextView.setText(item.getCls_name());
        if (item.isClick()) {
            viewHolder.titleTextView.setSelected(true);
        } else {
            viewHolder.titleTextView.setSelected(false);
        }
        if(item.getCls_list_2().size()!=0){
            Utils.setViewTypeForTag(viewHolder.titleTextView, ViewType.VIEW_TYPE_CATEGORY);
            Utils.setPositionForTag(viewHolder.titleTextView, position);
            viewHolder.titleTextView.setOnClickListener(onClickListener);
        }else{
            Utils.setViewTypeForTag(viewHolder.titleTextView, ViewType.VIEW_TYPE_SHOP_STREET);
            Utils.setPositionForTag(viewHolder.titleTextView, position);
            viewHolder.titleTextView.setOnClickListener(onClickListener);
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(ArrayList<ShopClassModel> data) {
        this.mData = data;
    }
}
