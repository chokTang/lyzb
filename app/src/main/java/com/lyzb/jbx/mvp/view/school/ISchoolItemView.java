package com.lyzb.jbx.mvp.view.school;

import com.lyzb.jbx.model.school.SchoolModel;

import java.util.List;

public interface ISchoolItemView {

    void onGetListByType(boolean isfresh, List<SchoolModel> list);

    void onZanReuslt(int position);
}
