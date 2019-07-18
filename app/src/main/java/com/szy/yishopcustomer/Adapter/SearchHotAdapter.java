package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;

import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Checkout.CheckoutDividerModel;
import com.szy.yishopcustomer.ResponseModel.Search.KeyWordModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.SearchEmptyViewHolder;
import com.szy.yishopcustomer.ViewHolder.SearchHotViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 李威 on 17/2/13.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class SearchHotAdapter extends RecyclerView.Adapter {
    public static final int VIEW_TYPE_ITEM = 0;
    public static final int VIEW_TYPE_EMPTY = 1;

    public View.OnClickListener onCLickListener;
    public List<Object> data;

    public SearchHotAdapter(List<Object> data) {
        this.data = data;
        setHasStableIds(true);
    }

    public SearchHotAdapter() {
        this.data = new ArrayList<>();
        setHasStableIds(true);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case VIEW_TYPE_ITEM:
                return new SearchHotViewHolder(inflater.inflate(R.layout.item_search_hot, parent,
                        false));
            case VIEW_TYPE_EMPTY:
                return new SearchEmptyViewHolder(inflater.inflate(R.layout.item_search_empty,
                        parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (getItemViewType(position)) {
            case VIEW_TYPE_ITEM:
                SearchHotViewHolder itemViewHolder = (SearchHotViewHolder) viewHolder;
                KeyWordModel keyWordModel = (KeyWordModel) data.get(position);
                itemViewHolder.mTextView.setText(keyWordModel.keyword);

                Utils.setViewTypeForTag(itemViewHolder.mTextView, ViewType.VIEW_TYPE_HOT_SEARCH);
                Utils.setPositionForTag(itemViewHolder.mTextView, position);
                itemViewHolder.mTextView.setOnClickListener(onCLickListener);

                break;
            case VIEW_TYPE_EMPTY:
                SearchEmptyViewHolder searchEmptyViewHolder = (SearchEmptyViewHolder) viewHolder;
                searchEmptyViewHolder.mTextView.setText("暂无热门搜索");
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        Object object = data.get(position);
        if (object instanceof KeyWordModel) {
            return VIEW_TYPE_ITEM;
        } else if (object instanceof CheckoutDividerModel) {
            return VIEW_TYPE_EMPTY;
        }
        return -1;
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
