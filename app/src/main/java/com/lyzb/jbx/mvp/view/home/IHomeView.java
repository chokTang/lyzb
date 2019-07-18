package com.lyzb.jbx.mvp.view.home;

import com.lyzb.jbx.model.account.FunctionModel;
import com.lyzb.jbx.model.follow.FollowHomeModel;

import java.util.List;

public interface IHomeView {
    void onFunctionResult(List<FunctionModel> list, int hasUserInfo);

    void onFollowListReuslt(FollowHomeModel model);
}
