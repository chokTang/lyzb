package com.lyzb.jbx.mvp.view.me;

import com.lyzb.jbx.model.circle.CircleDetailItemModel;
import com.lyzb.jbx.model.me.CircleDetModel;
import com.lyzb.jbx.model.me.CompanyCircleTabModel;
import com.lyzb.jbx.mvp.view.dynamic.IBaseDynamicView;

import java.util.List;

/**
 * 圈子详情
 * Created by shidengzhong on 2019/3/5.
 */

public interface ICircleDetView {

    void onData(CircleDetModel model);

    void onApply();

    void onCompanyCircleTabData (List<CompanyCircleTabModel> list);

    void onSavaCompanyCircleTabSuccess ();

    void onFail (String msg);
}
