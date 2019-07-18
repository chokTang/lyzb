package com.lyzb.jbx.mvp.view.me;

import com.lyzb.jbx.model.me.PubReplyModel;

import java.util.List;

/**
 * 我的发表-回复
 * Created by shidengzhong on 2019/3/5.
 */

public interface IPubReplyView {

    void onRelyListResult(boolean isRefresh, List<PubReplyModel> list);

    void onFinshOrLoadMore(boolean isRefresh);

    void onDeleteResult();
}
