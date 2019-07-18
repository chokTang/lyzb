package com.szy.yishopcustomer.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.TextView;

import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.SameCity.CityList.SortModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wyx
 * @role 城市列表搜索结果 adapter
 * @time 2018 14:55
 */

public class CityResultListAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<SortModel> mSortModels;


    public CityResultListAdapter(Context context, List<SortModel> models) {
        this.mContext = context;
        this.mSortModels = models;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return creatCityHolder(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        bindItemCity((CityHolder) holder, position);
    }


    @Override
    public int getItemCount() {
        return mSortModels.size();
    }

    public RecyclerView.ViewHolder creatCityHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city_list_layout_view, parent, false);
        CityHolder holder = new CityHolder(view);
        return holder;
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

    public void bindItemCity(final CityHolder holder, final int position) {
        holder.mTextView_Tag.setVisibility(View.GONE);
        holder.mTextView_Name.setText(mSortModels.get(position).getName());

        holder.mTextView_Name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (listener != null) {

                    SortModel sortModel = mSortModels.get(position);
                    listener.onItemListener(sortModel, position);
                }
            }
        });
    }

    public interface ItemListener {

        void onItemListener(SortModel model, int position);
    }

    private ItemListener listener;

    public void setListener(ItemListener listener) {
        this.listener = listener;
    }

}
