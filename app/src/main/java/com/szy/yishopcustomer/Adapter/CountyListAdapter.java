package com.szy.yishopcustomer.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.TextView;

import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.SameCity.CityList.CountyModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wyx
 * @role 区县列表数据
 * @time 2018 14:22
 */

public class CountyListAdapter extends RecyclerView.Adapter {

    private Context mContext;
    public List<CountyModel> mCountyModels;

    public CountyListAdapter(Context context) {
        this.mContext = context;
        this.mCountyModels = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return creatCountyHolder(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        CountyModel countyModel = mCountyModels.get(position);
        bindItemMneu((ItemHoler) holder, countyModel);
    }

    @Override
    public int getItemCount() {
        return mCountyModels.size();
    }

    public RecyclerView.ViewHolder creatCountyHolder(ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_county, parent, false);
        ItemHoler holder = new ItemHoler(view);
        return holder;
    }

    public class ItemHoler extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_item_county_name)
        TextView mTextView_Name;

        public ItemHoler(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public void bindItemMneu(ItemHoler holder, final CountyModel model) {

        holder.mTextView_Name.setText(model.regionName);

        holder.mTextView_Name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mListener != null) {
                    mListener.onItemClick(model);
                }
            }
        });
    }

    public interface ItemListener {

        void onItemClick(CountyModel model);
    }

    private ItemListener mListener;

    public void setItemListener(ItemListener listener) {
        this.mListener = listener;
    }
}
