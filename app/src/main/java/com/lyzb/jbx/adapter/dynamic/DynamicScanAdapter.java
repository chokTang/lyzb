package com.lyzb.jbx.adapter.dynamic;

import android.content.Context;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.lyzb.jbx.R;
import com.lyzb.jbx.model.dynamic.DynamicScanModel;

import java.util.List;

/**
 * 动态详情下的 浏览列表
 */
public class DynamicScanAdapter extends BaseRecyleAdapter<DynamicScanModel> {

    public DynamicScanAdapter(Context context, List<DynamicScanModel> list) {
        super(context, R.layout.recycle_dynamic_scan, list);
    }

    @Override
    protected void convert(BaseViewHolder holder, DynamicScanModel item) {
        holder.setRadiusImageUrl(R.id.img_avatar, item.getHeadimg(), 4);
        holder.setVisible(R.id.img_vip, item.getUserActionVos().size() > 0);
        holder.setText(R.id.tv_name, item.getUserName());
        holder.setText(R.id.tv_scan, String.format("浏览了%d次", item.getBrowseNum()));
        holder.setText(R.id.tv_shop_name, item.getCompanyInfo());
    }
}
