package com.lyzb.jbx.adapter.me.company;

import android.content.Context;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.lyzb.jbx.R;
import com.lyzb.jbx.model.me.CompanyMembersModel;

import java.util.List;

public class CompanyMembersAdapter extends BaseRecyleAdapter<CompanyMembersModel.DataBean.ListBean> {

    private int accountType = 1;//s 能否移除成员权限(1.否 2.是)

    public CompanyMembersAdapter(Context context, List<CompanyMembersModel.DataBean.ListBean> list) {
        super(context, R.layout.item_company_members, list);
    }

    @Override
    protected void convert(BaseViewHolder holder, CompanyMembersModel.DataBean.ListBean item) {

        holder.setVisible(R.id.img_item_com_merber_arrow, (getAccountType() == 2 && item.getDataType() != 0) ? true : false);
        holder.setVisible(R.id.tv_item_com_members_status, (getAccountType() == 2 && item.getDataType() != 0 && item.getAccountSt() != 1) ? true : false);

        holder.setVisible(R.id.tv_item_com_merber_type, item.getRole() == 1 ? true : false);//创建者显示

        holder.setVisible(R.id.tv_item_com_merber_remove, (getAccountType() == 2 && item.getRole() != 1 && item.getDataType() == 0) ? true : false);
        holder.setRadiusImageUrl(R.id.img_item_com_merber_head, item.getHeadimg(), 4);
        holder.setText(R.id.tv_item_com_merber_name, item.getGsName());
        holder.setText(R.id.tv_item_com_merber_com, item.getPosition());

        holder.addItemClickListener(R.id.img_item_com_merber_head);
        holder.addItemClickListener(R.id.tv_item_com_merber_remove);

    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }
}
