package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;

import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.NearShopTabViewHolder;
import com.szy.yishopcustomer.ViewModel.CategoryModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuzhifeng on 2017/2/10
 */
public class ShopStreetIntelligentAdapter extends RecyclerView.Adapter<NearShopTabViewHolder> {
    public View.OnClickListener onClickListener;
    public List<CategoryModel> mData;

    public ShopStreetIntelligentAdapter(List<CategoryModel> data) {
        this.mData = data;
    }

    @Override
    public NearShopTabViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_shop_street_intelligent, parent, false);
        return new NearShopTabViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NearShopTabViewHolder viewHolder, int position) {
        CategoryModel item = mData.get(position);
        viewHolder.titleTextView.setText(item.getCatName());

        if (item.isClick()) {
            viewHolder.titleTextView.setSelected(true);
            viewHolder.titleImageView.setVisibility(View.VISIBLE);

        } else {
            viewHolder.titleTextView.setSelected(false);
            viewHolder.titleImageView.setVisibility(View.INVISIBLE);
        }

        if (item.getCatImage().equals("DESC")) {
            viewHolder.titleImageView.setImageResource(R.mipmap.ic_sort_arrow_down);
        } else {
            viewHolder.titleImageView.setImageResource(R.mipmap.ic_sort_arrow_up);
        }

        Utils.setViewTypeForTag(viewHolder.titleTextView, ViewType
                .VIEW_TYPE_CATEGORY_NEAR_INTENLLINGENT);
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
