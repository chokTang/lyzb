package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;

import com.szy.common.Util.ImageLoader;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Distrib.DistribTeamModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.Distrib.DistribTeamViewHolder;

import java.util.ArrayList;

/**
 * Created by liwei on 2017/7/20.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class DistribTeamAdapter extends HeaderFooterAdapter<DistribTeamModel.DataBean.ListModel> {

    public View.OnClickListener onClickListener;

    public DistribTeamAdapter() {
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
        View view = inflater.inflate(R.layout.fragment_distrib_team_item, parent, false);
        return new DistribTeamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        if(getItemViewType(position) == TYPE_NORMAL){
            if(viewHolder instanceof DistribTeamViewHolder) {

                DistribTeamModel.DataBean.ListModel object = data.get(position);
                bindTeamViewHolder((DistribTeamViewHolder) viewHolder,object,
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

    private void bindTeamViewHolder(DistribTeamViewHolder viewHolder, DistribTeamModel.DataBean.ListModel object, int
            position) {
        viewHolder.user_name.setText("会员："+object.user_name);
        ImageLoader.displayImage(Utils.urlOfImage(object.headimg),viewHolder.headImg);
        viewHolder.total_user_count.setText("团队人数："+object.total_user_count);
        viewHolder.reg_time_format.setText("加入时间："+object.reg_time_format);
        viewHolder.dis_total_money.setText("销售额:"+Utils.formatMoney(viewHolder.dis_total_money.getContext(), object.dis_total_money));
    }
}