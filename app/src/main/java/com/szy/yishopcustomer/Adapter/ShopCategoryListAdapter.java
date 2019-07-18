package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;

import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.CategoryModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.ShopGoodsList.ShopCategoryViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 宗仁 on 2017/1/10.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class ShopCategoryListAdapter extends RecyclerView.Adapter<ShopCategoryViewHolder> {

    private static final int VIEW_TYPE_PARENT = 0;
    private static final int VIEW_TYPE_CHILD = 1;

    public List<CategoryModel> data;

    public View.OnClickListener onClickListener;

    public ShopCategoryListAdapter() {
        this.data = new ArrayList<>();
        setHasStableIds(true);
    }

    @Override
    public ShopCategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = R.layout.fragment_shop_category_list_child_item;
        switch (viewType) {
            case VIEW_TYPE_CHILD:
                layout = R.layout.fragment_shop_category_list_child_item;
                break;
            case VIEW_TYPE_PARENT:
                layout = R.layout.fragment_shop_category_list_parent_item;
                break;
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ShopCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShopCategoryViewHolder holder, int position) {
        CategoryModel model = data.get(position);
        //注释掉箭头，请勿删除
        /*if (model.type > 0) {
            if (model.cat_name.equals("全部")) {
                holder.numberTextView.setVisibility(View.GONE);
            } else {
                holder.numberTextView.setVisibility(View.VISIBLE);
            }
        } else {
            holder.numberTextView.setVisibility(View.GONE);
        }*/
        holder.itemView.setSelected(model.selected);
        holder.titleTextView.setSelected(model.selected);
        holder.titleTextView.setText(model.cat_name);
        Utils.setPositionForTag(holder.itemView, position);
        Utils.setViewTypeForTag(holder.itemView, ViewType.VIEW_TYPE_CATEGORY);
        holder.itemView.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemViewType(int position) {
        CategoryModel categoryModel = data.get(position);
        if (categoryModel.cat_id.contentEquals("0") || categoryModel.isParent || (categoryModel
                .chr_list != null && categoryModel.chr_list.size() > 0)) {
            return VIEW_TYPE_PARENT;
        }

        return VIEW_TYPE_CHILD;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
