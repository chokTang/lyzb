package com.lyzb.jbx.mvp.view.me;

import com.lyzb.jbx.model.me.CardMallModel;
import com.szy.yishopcustomer.ResponseModel.SameCity.Home.list;

import java.util.List;

/**
 * 我的发表-回复
 * Created by shidengzhong on 2019/3/5.
 */

public interface ICdMallView {

    void onGoodList(boolean isRefresh, CardMallModel model);

    void onFinshOrLoadMore(boolean isRefresh);

    void onDelete(int position);
}
