package com.lyzb.jbx.adapter.me.access;

import android.content.Context;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.lyzb.jbx.R;
import com.lyzb.jbx.model.me.access.AccessNewAccountModel;

import java.util.List;

/**
 * 引流用户列表适配器
 */
public class AccessAccountAdapter extends BaseRecyleAdapter<AccessNewAccountModel> {

    public AccessAccountAdapter(Context context, List<AccessNewAccountModel> list) {
        super(context, R.layout.recycle_access_account, list);
    }

    @Override
    protected void convert(BaseViewHolder holder, AccessNewAccountModel item) {
        holder.setRadiusImageUrl(R.id.img_member_header, item.getHeadimg(), 4);
        holder.setText(R.id.tv_member_name, item.getUserName());
        holder.setText(R.id.tv_member_value, String.format("通过%s分享注册", item.getShareName()));
        holder.setText(R.id.tv_register_time, String.format("注册时间：%s", item.getRegTime()));

        holder.addItemClickListener(R.id.img_member_header);
    }
}
