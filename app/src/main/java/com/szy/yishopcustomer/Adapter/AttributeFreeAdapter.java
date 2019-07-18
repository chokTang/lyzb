package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;

import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Attribute.AttributeModel;
import com.szy.yishopcustomer.ResponseModel.Attribute.SpecificationModelTwo;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.Attribute.AttributeItemViewHolder;
import com.szy.yishopcustomer.ViewHolder.Attribute.AttributeLineViewHolder;
import com.szy.yishopcustomer.ViewHolder.Attribute.AttributeTitleViewHolder;
import com.szy.yishopcustomer.ViewModel.Attribute.AttributeLineModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 宗仁 on 16/8/20.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class AttributeFreeAdapter extends RecyclerView.Adapter {
    public final int VIEW_TYPE_LINE = 0;
    public final int VIEW_TYPE_ITEM = 1;
    public final int VIEW_TYPE_TITLE = 2;

    public View.OnClickListener onCLickListener;
    public List<Object> data;

    public AttributeFreeAdapter(List<Object> data) {
        this.data = data;
        setHasStableIds(true);
    }

    public AttributeFreeAdapter() {
        this.data = new ArrayList<>();
        setHasStableIds(true);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case VIEW_TYPE_ITEM:
                return new AttributeItemViewHolder(inflater.inflate(R.layout
                        .fragment_attribute_item, parent, false));
            case VIEW_TYPE_TITLE:
                return new AttributeTitleViewHolder(inflater.inflate(R.layout
                        .fragment_attribute_title, parent, false));
            case VIEW_TYPE_LINE:
                return new AttributeLineViewHolder(inflater.inflate(R.layout
                        .fragment_attribute_line, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (getItemViewType(position)) {
            case VIEW_TYPE_TITLE:
                AttributeTitleViewHolder titleViewHolder = (AttributeTitleViewHolder) viewHolder;
                SpecificationModelTwo specificationModel = (SpecificationModelTwo) data.get(position);
                titleViewHolder.textView.setText(specificationModel.attr_name);
                break;
            case VIEW_TYPE_ITEM:
                AttributeItemViewHolder itemViewHolder = (AttributeItemViewHolder) viewHolder;
                AttributeModel attributeModel = (AttributeModel) data.get(position);
                itemViewHolder.textView.setChecked(attributeModel.selected);
                //itemViewHolder.textView.setSelected(attributeModel.selected);
                //itemViewHolder.textView.setEnabled(attributeModel.enabled);
                itemViewHolder.textView.setText(attributeModel.attr_value);
                itemViewHolder.textView.setActivated(true);

                Utils.setViewTypeForTag(itemViewHolder.textView, ViewType.VIEW_TYPE_ITEM);
                Utils.setPositionForTag(itemViewHolder.textView, position);
                itemViewHolder.textView.setOnClickListener(onCLickListener);
                break;
            case VIEW_TYPE_LINE:
                AttributeLineViewHolder attributeLineViewHolder = (AttributeLineViewHolder)
                        viewHolder;
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        Object object = data.get(position);
        if (object instanceof SpecificationModelTwo) {
            return VIEW_TYPE_TITLE;
        } else if (object instanceof AttributeModel) {
            return VIEW_TYPE_ITEM;
        } else if (object instanceof AttributeLineModel) {
            return VIEW_TYPE_LINE;
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
