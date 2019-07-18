package com.lyzb.jbx.adapter.campaign;

import android.content.Context;
import android.view.ViewGroup;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.lyzb.jbx.R;
import com.lyzb.jbx.model.campagin.CampaginDetailUserModel;

import java.util.List;

public class CampaignDetailMemeberAdapter extends BaseRecyleAdapter<CampaginDetailUserModel> {

    private boolean isShowVip = false;
    private ViewGroup.LayoutParams params;

    public CampaignDetailMemeberAdapter(Context context, int with, List<CampaginDetailUserModel> list) {
        super(context, R.layout.recycle_campaign_detail_user, list);
        params = new ViewGroup.LayoutParams(with, with);
    }

    public CampaignDetailMemeberAdapter(Context context, int with, int height, List<CampaginDetailUserModel> list) {
        super(context, R.layout.recycle_campaign_detail_user, list);
        params = new ViewGroup.LayoutParams(with, height);
    }

    @Override
    protected void convert(BaseViewHolder holder, CampaginDetailUserModel item) {
        holder.setRoundImageUrl(R.id.img_member_header, item.getHeadimg(), 100);
        holder.setText(R.id.tv_member_name, item.getUserName());

        if (isShowVip) {
            holder.setVisible(R.id.img_vip, item.getUserActionVos().size() > 0);
        } else {
            holder.setVisible(R.id.img_vip, false);
        }
        holder.get_view().setLayoutParams(params);
    }

    public void setShowVip(boolean showVip) {
        isShowVip = showVip;
    }
}
