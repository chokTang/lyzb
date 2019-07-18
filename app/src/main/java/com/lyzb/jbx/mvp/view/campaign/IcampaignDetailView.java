package com.lyzb.jbx.mvp.view.campaign;

import com.lyzb.jbx.model.campagin.CampaginDetailModel;

public interface IcampaignDetailView {
    void onCampaignDetail(CampaginDetailModel model);

    void onCollectionResultScuess();
    void onDeleteCollectionResultScuess();

    void onCardFollowSuccess();
}
