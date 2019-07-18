package com.lyzb.jbx.adapter.me.company;

import android.content.Context;
import android.text.TextUtils;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.lyzb.jbx.R;
import com.lyzb.jbx.model.me.CompanyAccountsModel;

import java.util.List;

public class CompanyAccountsAdapter extends BaseRecyleAdapter<CompanyAccountsModel.DataBean.ListBean> {
    public CompanyAccountsAdapter(Context context, List<CompanyAccountsModel.DataBean.ListBean> list) {
        super(context, R.layout.item_company_accounts, list);
    }

    @Override
    protected void convert(BaseViewHolder holder, CompanyAccountsModel.DataBean.ListBean item) {

        holder.setVisible(R.id.tv_item_com_accounts_type, "1".equals(item.getRole()) ? true : false);//创建者显示
        holder.setVisible(R.id.tv_item_com_accounts_status, (!"1".equals(item.getRole()) && item.getAccountSt() != 1) ? true : false);

        holder.setRadiusImageUrl(R.id.img_item_com_accounts_head, item.getHeadimg(), 4);
        String name = TextUtils.isEmpty(item.getAccountName()) ? "" : item.getAccountName();
        name = name + (TextUtils.isEmpty(item.getGsName()) ? "" : item.getGsName());
        holder.setText(R.id.tv_item_com_accounts_name, name);
        holder.setText(R.id.tv_item_com_accounts_com, item.getPosition());
        holder.addItemClickListener(R.id.img_item_com_accounts_head);
    }
}
