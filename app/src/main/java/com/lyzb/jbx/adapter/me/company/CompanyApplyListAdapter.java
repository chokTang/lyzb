package com.lyzb.jbx.adapter.me.company;

import android.content.Context;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.lyzb.jbx.R;
import com.lyzb.jbx.model.me.CompanyApplyListModel;

import java.util.List;

public class CompanyApplyListAdapter extends BaseRecyleAdapter<CompanyApplyListModel.DataBean.ListBean> {
    public CompanyApplyListAdapter(Context context, List<CompanyApplyListModel.DataBean.ListBean> list) {
        super(context, R.layout.item_company_applylist, list);
    }

    @Override
    protected void convert(BaseViewHolder holder, CompanyApplyListModel.DataBean.ListBean item) {

        holder.setVisible(R.id.tv_item_company_states, false);
        holder.setVisible(R.id.tv_item_company_agreed, false);
        holder.setVisible(R.id.tv_item_company_cancle, false);
        holder.setRadiusImageUrl(R.id.img_item_company_appl_head, item.getHeadimg(), 4);
        holder.setText(R.id.tv_item_company_appl_name, item.getGsName());
        holder.setText(R.id.tv_item_company_appl_com, item.getCurrentDepartment());
        holder.setText(R.id.tv_item_company_time, item.getApplyTime());

        if (item.getAuditState() == 1) {
            holder.setVisible(R.id.tv_item_company_agreed, true);
            holder.setVisible(R.id.tv_item_company_cancle, true);
        } else {
            holder.setVisible(R.id.tv_item_company_states, true);
            holder.setText(R.id.tv_item_company_states, item.getAuditState() == 2 ? "已同意" : "已驳回");
        }
        holder.addItemClickListener(R.id.tv_item_company_agreed);
        holder.addItemClickListener(R.id.tv_item_company_cancle);
    }
}
