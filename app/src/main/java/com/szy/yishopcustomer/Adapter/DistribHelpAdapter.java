package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;

import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Distrib.DistribHelpModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.Distrib.DistribHelpViewHolder;

import java.util.ArrayList;

/**
 * Created by liwei on 2017/7/21.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class DistribHelpAdapter extends HeaderFooterAdapter<DistribHelpModel.DataBean.ListModel> {

    public View.OnClickListener onClickListener;

    public DistribHelpAdapter() {
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
        View view = inflater.inflate(R.layout.fragment_distrib_help_item, parent, false);
        return new DistribHelpViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        if(getItemViewType(position) == TYPE_NORMAL){
            if(viewHolder instanceof DistribHelpViewHolder) {

                DistribHelpModel.DataBean.ListModel object = data.get(position);
                bindHelpViewHolder((DistribHelpViewHolder) viewHolder,object,
                        position);
                return;
            }
            return;
        }else if(getItemViewType(position) == TYPE_HEADER){
            return;
        }else{
            return;
        }

    }

    private void bindHelpViewHolder(DistribHelpViewHolder viewHolder, DistribHelpModel.DataBean.ListModel object, int
            position) {
        viewHolder.title.setText(object.title);

        Utils.setViewTypeForTag(viewHolder.itemView, ViewType.VIEW_TYPE_DETAIL);
        Utils.setPositionForTag(viewHolder.itemView, position);
        viewHolder.itemView.setOnClickListener(onClickListener);

    }
}