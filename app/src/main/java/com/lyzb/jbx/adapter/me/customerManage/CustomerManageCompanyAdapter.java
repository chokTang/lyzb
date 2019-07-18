package com.lyzb.jbx.adapter.me.customerManage;

import android.content.Context;
import android.text.TextUtils;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.lyzb.jbx.R;
import com.lyzb.jbx.model.me.customerManage.CustomerManageCompanyModel;

import java.util.List;

/**
 * @author wyx
 * @role
 * @time 2019 2019/5/10 10:18
 */

public class CustomerManageCompanyAdapter extends BaseRecyleAdapter<CustomerManageCompanyModel.DataBean.ListBean> {
    public CustomerManageCompanyAdapter(Context context, List<CustomerManageCompanyModel.DataBean.ListBean> list) {
        super(context, R.layout.item_customer_manage_company, list);
    }

    @Override
    protected void convert(BaseViewHolder holder, CustomerManageCompanyModel.DataBean.ListBean item) {
        //头像
        holder.setRadiusImageUrl(R.id.customer_manage_company_head_iv, item.getHeadImg(), 4);
        //客户数量
        holder.setText(R.id.customer_manage_company_number_tv, item.getTotalCus());
        //名称，分使用者名和无使用者名
        String name;
        if (TextUtils.isEmpty(item.getUserName())) {
            name = item.getAccountName();
        } else {
            name = item.getAccountName() + item.getUserName();
        }
        holder.setText(R.id.customer_manage_company_name_tv, name);
    }
}
