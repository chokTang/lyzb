package com.lyzb.jbx.adapter.campaign;

import android.content.Context;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.lyzb.jbx.R;
import com.lyzb.jbx.model.campagin.CampaignModel;

import java.util.List;

/**
 * 首页-活动适配器
 */
public class HomeCampaginListAdapter extends BaseRecyleAdapter<CampaignModel> {

    public HomeCampaginListAdapter(Context context, List<CampaignModel> list) {
        super(context, R.layout.recycle_campaign_home_item, list);
    }

    @Override
    protected void convert(BaseViewHolder holder, CampaignModel item) {
        holder.setText(R.id.tv_campaign_title, item.getTitle());
        holder.setRadiusImageUrl(R.id.img_campaign_poster, item.getActivityLogo(), 4);

        holder.setVisible(R.id.image_first_header, false);
        holder.setVisible(R.id.image_second_header, false);
        holder.setVisible(R.id.image_three_header, false);
        if (item.getActivityParticipantList().size() > 0) {
            holder.setVisible(R.id.image_first_header, true);
            holder.setRoundImageUrl(R.id.image_first_header, item.getActivityParticipantList().get(0).getHeadimg(), 30);
        }
        if (item.getActivityParticipantList().size() > 1) {
            holder.setVisible(R.id.image_second_header, true);
            holder.setRoundImageUrl(R.id.image_second_header, item.getActivityParticipantList().get(1).getHeadimg(), 30);
        }
        if (item.getActivityParticipantList().size() > 2) {
            holder.setVisible(R.id.image_three_header, true);
            holder.setRoundImageUrl(R.id.image_three_header, item.getActivityParticipantList().get(2).getHeadimg(), 30);
        }

        holder.setText(R.id.tv_campaign_number, String.format("%d人参与", item.getPartCount()));
        holder.setText(R.id.tv_campaign_address, item.getAccess() == 1 ? "线上活动" : item.getPlace());
        holder.setText(R.id.tv_campaign_status, item.getActivitySatatusZh());
        holder.setTextColor(R.id.tv_campaign_status, item.getActivitySatatusColor());
    }
}
