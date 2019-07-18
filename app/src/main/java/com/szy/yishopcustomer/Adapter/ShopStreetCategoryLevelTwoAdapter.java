package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;

import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.TabViewHolder;
import com.szy.yishopcustomer.ViewModel.CategoryModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuzhifeng on 2017/2/10
 */
public class ShopStreetCategoryLevelTwoAdapter extends RecyclerView.Adapter<TabViewHolder> {
    public View.OnClickListener onClickListener;
    public List<CategoryModel> mData;

    public ShopStreetCategoryLevelTwoAdapter(List<CategoryModel> data) {
        this.mData = data;
    }

    @Override
    public TabViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_shop_street_left_tab_two, parent, false);
        return new TabViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TabViewHolder viewHolder, int position) {
        CategoryModel item = mData.get(position);
        viewHolder.titleTextView.setText(item.getCatName());

        if (item.isClick()) {
            viewHolder.titleTextView.setSelected(true);
        } else {
            viewHolder.titleTextView.setSelected(false);
        }

        Utils.setViewTypeForTag(viewHolder.titleTextView, ViewType.VIEW_TYPE_CATEGORY_TWO);
        Utils.setPositionForTag(viewHolder.titleTextView, position);
        viewHolder.titleTextView.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(ArrayList<CategoryModel> data) {
        this.mData = data;
    }
}
