package com.lyzb.jbx.mvp.view.me;

import com.lyzb.jbx.model.me.FansModel;
import com.lyzb.jbx.mvp.view.dynamic.IBaseDynamicView;

import java.util.List;

/**
 * Created by shidengzhong on 2019/3/5.
 */

public interface IFocusView extends IBaseDynamicView {

    void onFocusList(boolean isRefresh, int totalNumber, List<FansModel> list);
}
