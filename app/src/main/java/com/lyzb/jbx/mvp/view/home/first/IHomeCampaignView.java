package com.lyzb.jbx.mvp.view.home.first;

import com.lyzb.jbx.model.campagin.CampaignModel;

import java.util.List;

public interface IHomeCampaignView {
    void onGetCampaignResult(boolean isRefrsh, List<CampaignModel> list);
}
