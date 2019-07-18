package com.lyzb.jbx.mvp.view.school;

import com.lyzb.jbx.model.school.SchoolModel;
import com.lyzb.jbx.model.school.SchoolTypeModel;

import java.util.List;

public interface IBusinessSchoolView {
    void onSchoolResult(boolean isRefresh, List<SchoolModel> list);

    void onGetArticleTypeList(List<SchoolTypeModel> list);

    void onZanReuslt(int position);
}
