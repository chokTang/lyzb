package com.lyzb.jbx.mvp.view.me;

import com.lyzb.jbx.model.me.CompanyModel;
import com.lyzb.jbx.model.me.SearchResultCompanyModel;

import java.util.List;

/**
 * 智能卡片-动态
 * Created by shidengzhong on 2019/3/5.
 */

public interface ICompanyView {

    void onList(boolean isRefresh, CompanyModel model);

    void onSet(CompanyModel.ListBean listBean);

    void onQueryList(List<SearchResultCompanyModel> resultlist);

    void joinSuccess();

    void joinFail();
}
