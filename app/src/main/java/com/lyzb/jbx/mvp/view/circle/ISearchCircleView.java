package com.lyzb.jbx.mvp.view.circle;

import com.lyzb.jbx.model.circle.CircleModel;

import java.util.List;

public interface ISearchCircleView {

    void onSearchCircle(boolean isrefresh, List<CircleModel> list);

    void onFinshRequest(boolean isrefresh);

    void onApply(String groupId);
}
