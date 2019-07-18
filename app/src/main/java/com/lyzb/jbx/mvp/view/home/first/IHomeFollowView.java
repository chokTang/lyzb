package com.lyzb.jbx.mvp.view.home.first;

import com.lyzb.jbx.model.follow.FollowAdoutUserModel;
import com.lyzb.jbx.model.follow.FollowHomeModel;
import com.lyzb.jbx.mvp.view.dynamic.IBaseDynamicView;

import java.util.List;

public interface IHomeFollowView extends IBaseDynamicView {

    void onFollowListReuslt(FollowHomeModel model);

    void onFollowAdoutMeList(List<FollowAdoutUserModel> list);
}
