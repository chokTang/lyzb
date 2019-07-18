package com.lyzb.jbx.mvp.view.circle;

import com.lyzb.jbx.model.circle.CircleModel;
import com.lyzb.jbx.mvp.view.dynamic.IBaseDynamicView;

import java.util.List;

public interface IHomeCircleView extends IBaseDynamicView {
    void onMyCircleList(List<CircleModel> list);
}
