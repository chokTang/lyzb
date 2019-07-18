package com.szy.yishopcustomer.Adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;

import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.IntegrationDetailViewHolder;
import com.szy.yishopcustomer.ViewModel.IntegrationDetailModel;

import java.util.ArrayList;

/**
 * Created by Smart on 2017/7/3.
 */

public class IntegrationDetailAdapter extends HeaderFooterAdapter<IntegrationDetailModel.DataBean.ListBean> {

    public View.OnClickListener onClickListener;

    public IntegrationDetailAdapter() {
        data = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(mHeaderView != null && viewType == TYPE_HEADER) {
            return new ListHolder(mHeaderView);
        }
        if(mFooterView != null && viewType == TYPE_FOOTER){
            return new ListHolder(mFooterView);
        }

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return createViewHolder(inflater, parent);

    }

    private RecyclerView.ViewHolder createViewHolder(LayoutInflater inflater, ViewGroup
            parent) {
        View view = inflater.inflate(R.layout.fragment_integration_detail_item, parent, false);
        return new IntegrationDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if(getItemViewType(position) == TYPE_NORMAL){
            if(viewHolder instanceof IntegrationDetailViewHolder) {
                //这里加载数据的时候要注意，是从position-1开始，因为position==0已经被header占用了
                int tempposition = position;
                if(mHeaderView != null) {
                    tempposition = position-1;
                }
                IntegrationDetailModel.DataBean.ListBean object = data.get(tempposition);
                bindShopViewHolder((IntegrationDetailViewHolder) viewHolder, object,
                        tempposition);
                return;
            }
            return;
        }else if(getItemViewType(position) == TYPE_HEADER){
            return;
        }else{
            return;
        }
    }

    private void bindShopViewHolder(IntegrationDetailViewHolder viewHolder, IntegrationDetailModel.DataBean.ListBean object, int
            position) {

        viewHolder.textView_note.setText(Utils.replaceTHMLLabel(object.note));


        if(!object.changed_points.startsWith("-")) {
            viewHolder.textView_changed_points.setTextColor(Color.parseColor("#38b66e"));
            viewHolder.textView_changed_points.setText("+" + object.changed_points);
        } else {
            viewHolder.textView_changed_points.setTextColor(Color.parseColor("#f23030"));
            viewHolder.textView_changed_points.setText(object.changed_points);
        }
        viewHolder.textView_add_time.setText(Utils.times(object.add_time,"yyyy-MM-dd HH:mm:ss"));
    }

}