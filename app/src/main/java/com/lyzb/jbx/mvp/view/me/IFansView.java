package com.lyzb.jbx.mvp.view.me;

import com.lyzb.jbx.model.me.FansModel;
import com.lyzb.jbx.mvp.view.dynamic.IBaseDynamicView;

import java.util.List;

/**
 * 我的发表-回复
 * Created by shidengzhong on 2019/3/5.
 */

public interface IFansView extends IBaseDynamicView {

    void onFansList(boolean isRefresh, int total, List<FansModel> list);
}
