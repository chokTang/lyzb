package com.szy.yishopcustomer.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.EditText;

import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Attribute.AttributeModel;
import com.szy.yishopcustomer.ResponseModel.Attribute.SpecificationModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.Attribute.AttributeItemLastViewHolder;
import com.szy.yishopcustomer.ViewHolder.Attribute.AttributeItemViewHolder;
import com.szy.yishopcustomer.ViewHolder.Attribute.AttributeLineViewHolder;
import com.szy.yishopcustomer.ViewHolder.Attribute.AttributeTitleViewHolder;
import com.szy.yishopcustomer.ViewModel.Attribute.AttributeLineModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Smart on 17/9/8.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class AttributeWholesaleAdapter extends RecyclerView.Adapter {
    public final int VIEW_TYPE_LINE = 0;
    public final int VIEW_TYPE_ITEM = 1;
    public final int VIEW_TYPE_TITLE = 2;
    public final int VIEW_TYPE_ITEM_LAST = 3;


    public View.OnClickListener onClickListener;
    public List<Object> data;

    public AttributeWholesaleAdapter(List<Object> data) {
        this.data = data;
        setHasStableIds(true);
    }

    public AttributeWholesaleAdapter() {
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
            case VIEW_TYPE_ITEM_LAST:
                return new AttributeItemLastViewHolder(inflater.inflate(R.layout
                        .fragment_attribute_item_last, parent, false));
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
                SpecificationModel specificationModel = (SpecificationModel) data.get(position);
                titleViewHolder.textView.setText(specificationModel.attr_name);
                break;
            case VIEW_TYPE_ITEM:
                AttributeItemViewHolder itemViewHolder = (AttributeItemViewHolder) viewHolder;
                AttributeModel attributeModel = (AttributeModel) data.get(position);
                itemViewHolder.textView.setChecked(attributeModel.selected);
                //itemViewHolder.textView.setSelected(attributeModel.selected);
                //itemViewHolder.textView.setEnabled(attributeModel.enabled);
                itemViewHolder.textView.setText(attributeModel.attr_value);
                Utils.setViewTypeForTag(itemViewHolder.textView, ViewType.VIEW_TYPE_ITEM);
                Utils.setPositionForTag(itemViewHolder.textView, position);
                itemViewHolder.textView.setOnClickListener(onClickListener);
                break;
            case VIEW_TYPE_LINE:
                AttributeLineViewHolder attributeLineViewHolder = (AttributeLineViewHolder)
                        viewHolder;
                break;
            case VIEW_TYPE_ITEM_LAST:
                final AttributeItemLastViewHolder attributeItemLastViewHolder = (AttributeItemLastViewHolder) viewHolder;

                final Context mContext = attributeItemLastViewHolder.itemView.getContext();

                final AttributeModel attributeModel1 = (AttributeModel) data.get(position);

                attributeItemLastViewHolder.textView_sku_name.setText(attributeModel1.attr_value);
                attributeItemLastViewHolder.textView_sku_name.setText(attributeModel1.attr_value);

                Utils.setViewTypeForTag(attributeItemLastViewHolder.item_cart_goods_minus_button, ViewType.VIEW_TYPE_MINUS);
                Utils.setPositionForTag(attributeItemLastViewHolder.item_cart_goods_minus_button, position);
                attributeItemLastViewHolder.item_cart_goods_minus_button.setOnClickListener(onClickListener);

                Utils.setViewTypeForTag(attributeItemLastViewHolder.item_cart_goods_add_button, ViewType.VIEW_TYPE_PLUS);
                Utils.setPositionForTag(attributeItemLastViewHolder.item_cart_goods_add_button, position);
                attributeItemLastViewHolder.item_cart_goods_add_button.setOnClickListener(onClickListener);

                Utils.setViewTypeForTag(attributeItemLastViewHolder.item_cart_goods_number, ViewType.VIEW_TYPE_EDIT);
                Utils.setPositionForTag(attributeItemLastViewHolder.item_cart_goods_number, position);
                attributeItemLastViewHolder.item_cart_goods_number.setOnClickListener(onClickListener);


                attributeItemLastViewHolder.item_cart_goods_number.setText(attributeModel1.last_goodNum + "");
                attributeItemLastViewHolder.item_cart_goods_number.setFilters(Utils.filterForGoodsNumber(mContext, attributeModel1.last_stock,"库存还剩$1"));

                attributeItemLastViewHolder.item_cart_goods_number.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if(!hasFocus){
                            EditText t = (EditText)v;
                            String num = t.getText().toString();

                            if (TextUtils.isEmpty(num)) {
                                attributeModel1.last_goodNum = 0;
                            } else {
                                attributeModel1.last_goodNum = Integer.parseInt(num);
                                attributeItemLastViewHolder.item_cart_goods_number.callOnClick();
                            }
                        }
                    }
                });

//                attributeItemLastViewHolder.item_cart_goods_number.addTextChangedListener(new TextWatcher() {
//                    @Override
//                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                    }
//
//                    @Override
//                    public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                    }
//
//                    @Override
//                    public void afterTextChanged(final Editable s) {
//                        String str = s.toString();
//                        try {
//                            if (TextUtils.isEmpty(str)) {
//                                attributeModel1.last_goodNum = 0;
//                            } else {
//                                attributeModel1.last_goodNum = Integer.parseInt(str);
//                            }
//                        } catch (Exception e) {
//                            attributeModel1.last_goodNum = 0;
//                        }
//
//                        attributeItemLastViewHolder.item_cart_goods_number.callOnClick();
//                    }
//
//
//                });

                attributeItemLastViewHolder.textView_sku_stock.setText(attributeModel1.last_stock + attributeModel1.last_company + "可售");
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        Object object = data.get(position);
        if (object instanceof SpecificationModel) {
            return VIEW_TYPE_TITLE;
        } else if (object instanceof AttributeModel) {
            AttributeModel attributeModel = (AttributeModel) object;
            if (attributeModel.isLast) {
                return VIEW_TYPE_ITEM_LAST;
            } else {
                return VIEW_TYPE_ITEM;
            }
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
