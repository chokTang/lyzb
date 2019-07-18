package com.lyzb.jbx.adapter.campaign;

import android.content.Context;
import android.view.ViewGroup;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.lyzb.jbx.R;
import com.lyzb.jbx.model.campagin.CampaginUserListModel;

import java.util.List;

public class CampaignDetailMoreMemeberAdapter extends BaseRecyleAdapter<CampaginUserListModel> {

    private ViewGroup.LayoutParams params;

    public CampaignDetailMoreMemeberAdapter(Context context, int with, List<CampaginUserListModel> list) {
        super(context, R.layout.recycle_campaign_more_user, list);
        params = new ViewGroup.LayoutParams(with, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected void convert(BaseViewHolder holder, CampaginUserListModel item) {
        holder.setRoundImageUrl(R.id.img_member_header, item.getHeadimg(), 100);
        holder.setText(R.id.tv_member_name, item.getUserName());
        holder.setText(R.id.tv_member_company, item.getUserHeadName());

        holder.setVisible(R.id.img_vip, item.getUserActionVos().size() > 0);
        holder.get_view().setLayoutParams(params);
    }
}
