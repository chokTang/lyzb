package com.lyzb.jbx.mvp.view.home.first;

import com.lyzb.jbx.model.common.RecommentBannerModel;
import com.lyzb.jbx.model.follow.InterestMemberModel;
import com.lyzb.jbx.mvp.view.dynamic.IBaseDynamicView;

import java.util.List;

public interface IHomeRecommendView extends IBaseDynamicView {

    void onInterestMember(List<InterestMemberModel> list);

    void onBannerList(List<RecommentBannerModel> list);
}
