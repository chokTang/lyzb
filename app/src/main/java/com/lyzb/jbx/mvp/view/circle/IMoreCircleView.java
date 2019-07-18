package com.lyzb.jbx.mvp.view.circle;

import com.lyzb.jbx.model.circle.CircleModel;

import java.util.List;

public interface IMoreCircleView {
    void onMoreCircle(boolean isrefresh, List<CircleModel> list);

    void onApply (String id);
}
