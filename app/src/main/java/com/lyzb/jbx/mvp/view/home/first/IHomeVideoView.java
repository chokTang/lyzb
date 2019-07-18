package com.lyzb.jbx.mvp.view.home.first;

import com.lyzb.jbx.model.video.HomeVideoModel;

import java.util.List;

public interface IHomeVideoView {
    void onApplyList(boolean isRefresh, List<HomeVideoModel.ListBean> list);


    //点赞结果
    void onZanResult(int position);

    void onFinshOrLoadMore(boolean isRefrsh);
}
