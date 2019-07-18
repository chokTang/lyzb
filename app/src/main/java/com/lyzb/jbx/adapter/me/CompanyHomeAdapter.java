package com.lyzb.jbx.adapter.me;

import android.content.Context;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.lyzb.jbx.R;
import com.lyzb.jbx.model.me.CompanyHomeItemModel;
import com.lyzb.jbx.model.me.company.OrganDetailModel;

import java.util.List;

/**
 * 企业主页的适配器
 */
public class CompanyHomeAdapter extends BaseRecyleAdapter<OrganDetailModel.DataBean.AuthListBean> {

    public CompanyHomeAdapter(Context context, List<OrganDetailModel.DataBean.AuthListBean> list) {
        super(context, R.layout.recycle_company_home, list);
    }

    @Override
    protected void convert(BaseViewHolder holder, OrganDetailModel.DataBean.AuthListBean item) {
        holder.setImageUrl(R.id.img_header, item.getClass1());
        holder.setText(R.id.tv_name, item.getDisplay());
        holder.setText(R.id.tv_message_number, String.valueOf(item.getMessageNumber()));
        holder.setVisible(R.id.tv_message_number, item.getMessageNumber() > 0);
    }
}
