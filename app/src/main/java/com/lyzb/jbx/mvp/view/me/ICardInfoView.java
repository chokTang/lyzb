package com.lyzb.jbx.mvp.view.me;

/**
 * 我的-基本信息 编辑
 * Created by shidengzhong on 2019/3/5.
 */

public interface ICardInfoView {

    void toCard(String data);

    void getHeadImg(String url);

    void getWxImg(String url);

    void getLogoImg(String url);

    void getCardBgImg(String url);
}
