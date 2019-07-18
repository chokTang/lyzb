package com.lyzb.jbx.mvp.view.me;

import com.lyzb.jbx.model.me.CircleModel;

import java.util.List;

/**
 * 我的圈子
 * Created by shidengzhong on 2019/3/5.
 */

public interface IMyCircleView {

    void onList(boolean isRefresh,List<CircleModel.ListBean> list);

    void onFinshOrLoadMore(boolean isRefresh);

    void onCreate();

    void onUnCreate();
}
