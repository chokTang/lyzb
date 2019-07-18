package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.EditText;

import com.jaygoo.widget.RangeSeekBar;
import com.szy.common.Adapter.TextWatcherAdapter;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.Filter.FilterDividerViewHolder;
import com.szy.yishopcustomer.ViewHolder.Filter.FilterInputViewHolder;
import com.szy.yishopcustomer.ViewHolder.Filter.FilterItemViewHolder;
import com.szy.yishopcustomer.ViewHolder.Filter.FilterLineViewHolder;
import com.szy.yishopcustomer.ViewHolder.Filter.FilterSeekbarViewHolder;
import com.szy.yishopcustomer.ViewHolder.Filter.FilterTitleViewHolder;
import com.szy.yishopcustomer.ViewModel.Filter.FilterChildModel;
import com.szy.yishopcustomer.ViewModel.Filter.FilterGroupModel;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by 宗仁 on 16/8/20.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class FilterAdapter extends RecyclerView.Adapter implements View.OnClickListener,
        TextWatcherAdapter.TextWatcherListener {

    public static final int VIEW_TYPE_TITLE = 1;
    public static final int VIEW_TYPE_ITEM = 2;
    public static final int VIEW_TYPE_INPUT = 3;
    public static final int VIEW_TYPE_LINE = 4;
    public static final int VIEW_TYPE_DIVIDER = 5;
    public static final int VIEW_TYPE_SEEKBAR = 6;

    private static final String TAG = "FilterAdapter";

    public ArrayList<FilterGroupModel> data;

    public FilterAdapter(ArrayList<FilterGroupModel> data) {
        this.data = data;
    }

    @Override
    public void onClick(View view) {
        ViewType viewType = Utils.getViewTypeOfTag(view);
        int position = Utils.getPositionOfTag(view);
        switch (viewType) {
            case VIEW_TYPE_GROUP:
                onGroupClicked(position);
                break;
            case VIEW_TYPE_ITEM:
                onChildClicked(position);
                break;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_TITLE:
                return new FilterTitleViewHolder(LayoutInflater.from(parent.getContext()).inflate
                        (R.layout.fragment_filter_title, parent, false));
            case VIEW_TYPE_ITEM:
                return new FilterItemViewHolder(LayoutInflater.from(parent.getContext()).inflate
                        (R.layout.fragment_filter_item, parent, false));
            case VIEW_TYPE_INPUT:
                return new FilterInputViewHolder(LayoutInflater.from(parent.getContext()).inflate
                        (R.layout.fragment_filter_input, parent, false));
            case VIEW_TYPE_SEEKBAR:
                return new FilterSeekbarViewHolder(LayoutInflater.from(parent.getContext()).inflate
                        (R.layout.fragment_filter_seekbar, parent, false));
            case VIEW_TYPE_DIVIDER:
                return new FilterDividerViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.fragment_filter_divider, parent, false));
            case VIEW_TYPE_LINE:
                return new FilterLineViewHolder(LayoutInflater.from(parent.getContext()).inflate
                        (R.layout.fragment_filter_line, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case VIEW_TYPE_INPUT:
                bindInputViewHolder((FilterInputViewHolder) holder, (FilterChildModel) getItem
                        (position), position);
                break;
            case VIEW_TYPE_SEEKBAR:
                bindSeekbarViewHolder((FilterSeekbarViewHolder) holder, (FilterChildModel) getItem
                        (position), position);
                break;
            case VIEW_TYPE_ITEM:
                bindItemViewHolder((FilterItemViewHolder) holder, (FilterChildModel) getItem
                        (position), position);
                break;
            case VIEW_TYPE_TITLE:
                bindTitleViewHolder((FilterTitleViewHolder) holder, (FilterGroupModel) getItem
                        (position), position);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        Object object = getItem(position);
        if (object instanceof FilterGroupModel) {
            FilterGroupModel filterGroupModel = (FilterGroupModel) object;
            switch (filterGroupModel.type) {
                case FilterGroupModel.FILTER_TYPE_LINE:
                    return VIEW_TYPE_LINE;
                case FilterGroupModel.FILTER_TYPE_DIVIDER:
                    return VIEW_TYPE_DIVIDER;
                default:
                    return VIEW_TYPE_TITLE;
            }
        } else if (object instanceof FilterChildModel) {
            FilterChildModel filterChildModel = (FilterChildModel) object;
            switch (filterChildModel.type) {
                case FilterChildModel.FILTER_TYPE_PRICE:
                    return VIEW_TYPE_INPUT;
                case FilterChildModel.FILTER_TYPE_PRICE_SEEKBAR:
                    return VIEW_TYPE_SEEKBAR;
                default:
                    return VIEW_TYPE_ITEM;
            }
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        int count = data.size();
        for (FilterGroupModel groupModel : data) {
            if (groupModel.expandEnabled) {
                if (groupModel.expanded) {
                    count += groupModel.children.size();
                } else {
                    count += (groupModel.children.size() > 3 ? 3 : groupModel.children.size());
                }
            } else {
                count += groupModel.children.size();
            }
        }
        Log.i(TAG, "Item count is " + count);
        return count;
    }

    @Override
    public void onTextChanged(EditText view, String text) {
        ViewType viewType = Utils.getViewTypeOfTag(view);
        int position = Utils.getPositionOfTag(view);
        FilterChildModel filterChildModel = (FilterChildModel) getItem(position);
        switch (viewType) {
            case VIEW_TYPE_MINIMUM:
                if (filterChildModel != null) {
                    filterChildModel.minimumValue = text;
                }
                break;
            case VIEW_TYPE_MAXIMUM:
                if (filterChildModel != null) {
                    filterChildModel.maximumValue = text;
                }
                break;
        }
    }

    private void bindInputViewHolder(FilterInputViewHolder holder, FilterChildModel item, int
            position) {
        if (!Utils.isNull(item.minimumValue) && Double.parseDouble(item.minimumValue) > 0) {
            holder.minimumEditText.setText(item.minimumValue);
        } else {
            holder.minimumEditText.setText("");
        }
        if (!Utils.isNull(item.maximumValue) && Double.parseDouble(item.maximumValue) > 0) {
            holder.maximumEditText.setText(item.maximumValue);
        } else {
            holder.maximumEditText.setText("");
        }
        Utils.setViewTypeForTag(holder.minimumEditText, ViewType.VIEW_TYPE_MINIMUM);
        Utils.setPositionForTag(holder.minimumEditText, position);
        holder.minimumEditText.setTextWatcherListener(this);

        Utils.setViewTypeForTag(holder.maximumEditText, ViewType.VIEW_TYPE_MAXIMUM);
        Utils.setPositionForTag(holder.maximumEditText, position);
        holder.maximumEditText.setTextWatcherListener(this);
    }

    private void bindSeekbarViewHolder(final FilterSeekbarViewHolder holder, final FilterChildModel item, final int
            position) {

        double minPrice = Math.floor(Double.parseDouble(item.rangeStart));
        if(minPrice>1){
            BigDecimal bd1 = BigDecimal.valueOf(minPrice);
            BigDecimal bd2 = BigDecimal.valueOf(1);
            minPrice = bd1.subtract(bd2).doubleValue();
        }else {
            minPrice = 0;
        }

        double maxPrice = Math.ceil(Double.parseDouble(item.rangeEnd));
        BigDecimal bd1 = BigDecimal.valueOf(maxPrice);
        BigDecimal bd2 = BigDecimal.valueOf(minPrice);
        BigDecimal bd3 = BigDecimal.valueOf(100);
        double dVablue = bd1.subtract(bd2).doubleValue();
        BigDecimal bd4 = BigDecimal.valueOf(dVablue);
        if(dVablue < 100){
            //maxPrice = maxPrice + (100 - dVablue);
            maxPrice = bd1.add(bd3.subtract(bd4)).doubleValue();
        }

        if(maxPrice > minPrice){
            //设置范围
            holder.rangeSeekBar.setRange(Float.parseFloat(String.valueOf(minPrice)),Float.parseFloat(String.valueOf(maxPrice)));
            //设置初始值
            //点击清空按钮，item.minimumValue和maximumValue都为null
            if(!Utils.isNull(item.minimumValue)&&!Utils.isNull(item.maximumValue)) {
                //if第一次赋值默认为最大值和最小值范围
                //else其余时间赋值为选择的范围
                if(item.minimumValue.equals(item.rangeStart)&&item.maximumValue.equals(item.rangeEnd)){
                    holder.rangeSeekBar.setValue(Float.parseFloat(String.valueOf(minPrice)),Float.parseFloat(String.valueOf(maxPrice)));
                }else {
                    holder.rangeSeekBar.setValue(Float.parseFloat(item.minimumValue), Float.parseFloat(item.maximumValue));
                }

            }else{
                holder.rangeSeekBar.setValue(Float.parseFloat(String.valueOf(minPrice)),Float.parseFloat(String.valueOf(maxPrice)));
            }

        }

        final DecimalFormat df = new DecimalFormat("0");

        if(!Utils.isNull(item.minimumValue)&&!Utils.isNull(item.maximumValue)){
            //if第一次赋值默认为最大值和最小值范围
            //else其余时间赋值为选择的范围
            if(item.minimumValue.equals(item.rangeStart)&&item.maximumValue.equals(item.rangeEnd)){
                holder.progressTextView.setText(df.format(minPrice)+ "-" + df.format(maxPrice));
            }else{
                holder.progressTextView.setText(df.format(Double.parseDouble(item.minimumValue))+ "-" + df.format(Double.parseDouble(item.maximumValue)));
            }
        }else{
            holder.progressTextView.setText(df.format(minPrice)+ "-" + df.format(maxPrice));
        }

        holder.rangeSeekBar.setOnRangeChangedListener(new RangeSeekBar.OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float min, float max, boolean isFromUser) {
                if (isFromUser) {
                    holder.progressTextView.setText(df.format(min) + "-" + df.format(max));
                    holder.rangeSeekBar.setLeftProgressDescription(df.format(min));
                    holder.rangeSeekBar.setRightProgressDescription(df.format(max));

                    FilterChildModel filterChildModel = (FilterChildModel) getItem(position);
                    filterChildModel.maximumValue = df.format(max);
                    filterChildModel.minimumValue = df.format(min);
                }
            }
        });
    }

    private void bindItemViewHolder(FilterItemViewHolder holder, FilterChildModel item, int
            position) {
        holder.textView.setText(item.title);
        holder.textView.setChecked(item.selected);
        Utils.setViewTypeForTag(holder.itemView, ViewType.VIEW_TYPE_ITEM);
        Utils.setPositionForTag(holder.itemView, position);
        holder.itemView.setOnClickListener(this);
    }

    private void bindTitleViewHolder(FilterTitleViewHolder holder, FilterGroupModel item, int
            position) {
        Utils.setPositionForTag(holder.itemView, position);
        Utils.setViewTypeForTag(holder.itemView, ViewType.VIEW_TYPE_GROUP);
        holder.itemView.setOnClickListener(this);
        holder.textView.setText(item.title);
        if (item.expandEnabled) {
            holder.imageView.setVisibility(View.VISIBLE);
            holder.allTextView.setVisibility(View.VISIBLE);
            if (item.expanded) {
                holder.imageView.setImageResource(R.mipmap.bg_arrow_down_gray);
            } else {
                holder.imageView.setImageResource(R.mipmap.bg_arrow_right_gray);
            }
        } else {
            holder.imageView.setVisibility(View.INVISIBLE);
            holder.allTextView.setVisibility(View.INVISIBLE);
        }
    }

    private Object getItem(int position) {
        int cursor = 0;
        for (FilterGroupModel filterGroupModel : data) {
            if (cursor == position) {
                return filterGroupModel;
            }
            if (filterGroupModel.expandEnabled && !filterGroupModel.expanded && filterGroupModel
                    .children.size() > 3) {
                for (int i = 0; i < 3; i++) {
                    cursor++;
                    if (cursor == position) {
                        return filterGroupModel.children.get(i);
                    }
                }
            } else {
                for (Object childObject : filterGroupModel.children) {
                    cursor++;
                    if (cursor == position) {
                        return childObject;
                    }
                }
            }
            cursor++;
        }
        return null;
    }

    private void onChildClicked(int position) {
        FilterChildModel filterChildModel = (FilterChildModel) getItem(position);
        filterChildModel.selected = !filterChildModel.selected;
        notifyDataSetChanged();
    }

    private void onGroupClicked(int position) {
        FilterGroupModel groupModel = (FilterGroupModel) getItem(position);
        if (!groupModel.expandEnabled) {
            return;
        }
        groupModel.expanded = !groupModel.expanded;
        notifyDataSetChanged();
    }
}
