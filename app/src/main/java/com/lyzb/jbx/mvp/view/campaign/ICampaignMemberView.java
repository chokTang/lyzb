package com.lyzb.jbx.mvp.view.campaign;

import com.lyzb.jbx.adapter.campaign.CampaignDetailMoreMemeberAdapter;
import com.lyzb.jbx.model.campagin.CampaginUserListModel;

import java.util.List;

public interface ICampaignMemberView {
    void onCampaignMember(boolean isfresh, int totalNumber, List<CampaginUserListModel> list);
}
