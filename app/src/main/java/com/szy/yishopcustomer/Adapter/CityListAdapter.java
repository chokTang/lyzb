package com.szy.yishopcustomer.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szy.yishopcustomer.Constant.Key;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.SameCity.CityList.SortModel;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.SharedPreferencesUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wyx
 * @role 城市列表 adapter
 * @time 2018 14:55
 */

public class CityListAdapter extends RecyclerView.Adapter {

    public View.OnClickListener onClickListener;
    private Context mContext;
    private List<SortModel> mSortModels;

    private final static int ITME_HISTORE = 0x254;
    private final static int ITME_CITY = 0x255;

    private String city_name;
    private String city_code;
    private String city_province;
    private String standerCode;

    public CityListAdapter(Context context, List<SortModel> models) {
        this.mContext = context;
        this.mSortModels = models;

        city_province = (String) SharedPreferencesUtils.getParam(mContext, Key.KEY_CITY_PROVINCE.getValue(), "");
        city_name = (String) SharedPreferencesUtils.getParam(mContext, Key.KEY_CITY_LATELY.getValue(), "");
        city_code = (String) SharedPreferencesUtils.getParam(mContext, Key.KEY_CITY_LATELY_CODE.getValue(), "");
        standerCode = (String) SharedPreferencesUtils.getParam(mContext, Key.KEY_CITY_STANDER_CODE.getValue(), "");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITME_HISTORE) {
            return creatHistroyHolder(parent);
        }
        return creatCityHolder(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == ITME_HISTORE) {
            //绑定历史数据
            bintHistoryItem((HistoryHolder) holder,position);
        } else {
            bindItemCity((CityHolder) holder, position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ITME_HISTORE;
        }
        return ITME_CITY;
    }

    @Override
    public int getItemCount() {
        return mSortModels.size() + 1;
    }

    public RecyclerView.ViewHolder creatCityHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city_list_layout_view, parent, false);
        CityHolder holder = new CityHolder(view);
        return holder;
    }

    public RecyclerView.ViewHolder creatHistroyHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.same_recycle_history_layout, parent, false);
        HistoryHolder holder = new HistoryHolder(view);
        return holder;
    }

    public class HistoryHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.linea_late_ly)
        LinearLayout mLayout_late;
        @BindView(R.id.tv_item_city_lately_title)
        TextView tv_item_city_lately_title;
        @BindView(R.id.tv_item_city_lately_name)
        TextView tv_item_city_lately_name;

        public HistoryHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public class CityHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_item_tag)
        TextView mTextView_Tag;
        @BindView(R.id.tv_item_city_name)
        TextView mTextView_Name;

        public CityHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void bintHistoryItem(HistoryHolder historyHolder, final int position) {


        if (TextUtils.isEmpty(city_name)) {
            historyHolder.tv_item_city_lately_name.setVisibility(View.GONE);
        } else {
            historyHolder.tv_item_city_lately_name.setVisibility(View.VISIBLE);
            historyHolder.tv_item_city_lately_name.setText(city_name);
            historyHolder.tv_item_city_lately_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        App.getInstance().city_code = city_code;
                        App.getInstance().city_name = city_name;
                        App.getInstance().city_province = city_province;
                        App.getInstance().home_area_code =standerCode;

                        listener.onHisItemListener();
                    }
                }
            });
        }

    }

    public void bindItemCity(final CityHolder holder, final int position) {
        int section = getSectionForPosition(position);
        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section) || position == 1) {
            holder.mTextView_Tag.setVisibility(View.VISIBLE);
            holder.mTextView_Tag.setText(mSortModels.get(position).getLetters());
        } else {
            holder.mTextView_Tag.setVisibility(View.GONE);
        }
        holder.mTextView_Name.setText(mSortModels.get(position).getName());
        holder.mTextView_Name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {

                    SharedPreferencesUtils.setParam(mContext, Key.KEY_CITY_PROVINCE.getValue(), mSortModels.get(position).getProvince_name());
                    SharedPreferencesUtils.setParam(mContext, Key.KEY_CITY_LATELY.getValue(), mSortModels.get(position).getName());
                    SharedPreferencesUtils.setParam(mContext, Key.KEY_CITY_LATELY_CODE.getValue(), mSortModels.get(position).getCode());
                    if (!TextUtils.isEmpty(mSortModels.get(position).getStandardCode())){
                        SharedPreferencesUtils.setParam(mContext, Key.KEY_CITY_STANDER_CODE.getValue(), mSortModels.get(position).getStandardCode());
                    }

                    App.getInstance().city_code = mSortModels.get(position).getCode();
                    App.getInstance().city_name = mSortModels.get(position).getName();
                    App.getInstance().city_province = mSortModels.get(position).getProvince_name();
                    App.getInstance().home_area_code = mSortModels.get(position).getStandardCode();

                    listener.onItemListener();
                }
            }
        });
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的char ascii值
     */
    public int getSectionForPosition(int position) {
        return mSortModels.get(position).getLetters().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getItemCount() - 1; i++) {
            String sortStr = mSortModels.get(i).getLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }

    public interface ItemListener {

        void onItemListener();

        void onHisItemListener();
    }

    private ItemListener listener;

    public void setListener(ItemListener listener) {
        this.listener = listener;
    }

}
