package com.lyzb.jbx.mvp.view.me;

/**
 * 创建圈子
 * Created by shidengzhong on 2019/3/5.
 */

public interface IAddCircleView {

    void getImgList(String imgUrl);

    void onCreateSucces();

    void onUpdate();

    void onError (String msg);
}
