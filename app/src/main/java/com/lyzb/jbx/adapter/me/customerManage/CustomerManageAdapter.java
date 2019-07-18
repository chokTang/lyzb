package com.lyzb.jbx.adapter.me.customerManage;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.lyzb.jbx.R;
import com.lyzb.jbx.model.me.customerManage.CustomerInfoModel;
import com.szy.yishopcustomer.ResponseModel.SameCity.Home.list;

import java.util.List;

/**
 * @author wyx
 * @role 客户管理
 * @time 2019 2019/5/6 10:22
 */

public class CustomerManageAdapter extends BaseRecyleAdapter<CustomerInfoModel> {
    private boolean isCompany;

    public CustomerManageAdapter(Context context, boolean isCompany, List<CustomerInfoModel> list) {
        super(context, R.layout.item_customer_manage, list);
        this.isCompany = isCompany;
    }

    @Override
    protected void convert(BaseViewHolder holder, CustomerInfoModel item) {
        if (isCompany) {
            //企业管理员看的时候不展示添加跟进
            holder.setVisible(R.id.customer_manage_addtrack_tv, false);
        } else {
            holder.setVisible(R.id.customer_manage_addtrack_tv, true);
        }
        //头像
        holder.setRadiusImageUrl(R.id.customer_manage_head_iv, item.getHeadimg(), 4);
        //名称
        holder.setText(R.id.customer_manage_name_tv, item.getRemarkName());
        //职位
        holder.setText(R.id.customer_manage_company_tv, item.getCompany());
        //看了多少次-名片、商品、动态
        String content = _context.getString(R.string.customer_content, item.getBrowseNum(), item.getGoodsNum(), item.getTopicNum());
        holder.setText(R.id.customer_manage_content_tv, content);
        //跟进次数
        holder.setText(R.id.customer_manage_track_number_tv, String.format("跟进%d次", item.getCustomersFollowNum()));
        //访问、跟进时间
        if (TextUtils.isEmpty(item.getCreateTime())) {
            holder.setText(R.id.customer_manage_time_tv, "暂未跟进");
        } else {
            holder.setText(R.id.customer_manage_time_tv, "最近跟进：" + item.getCreateTime());
        }
        ImageView isVip = holder.cdFindViewById(R.id.customer_manage_isvip_iv);
        //是否为vip
        if (item.getUserActionVos() == null || item.getUserActionVos().size() < 1) {
            isVip.setVisibility(View.INVISIBLE);
        } else {
            isVip.setVisibility(View.VISIBLE);
        }

        holder.addItemClickListener(R.id.customer_manage_addtrack_tv);
        holder.addItemClickListener(R.id.customer_manage_head_iv);
    }
}