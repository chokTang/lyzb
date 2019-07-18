package com.lyzb.jbx.mvp.view.me;

import com.lyzb.jbx.model.account.BusinessModel;

import java.util.List;

/**
 * 我的-其他信息 编辑
 * Created by shidengzhong on 2019/3/5.
 */

public interface ICdOtherInfoView {

    void onIndustryList(List<BusinessModel> list);

    void toCardInfo(String data);
    void saveFail();

}
