package com.lyzb.jbx.mvp.view.me;

/**
 * @author wyx
 * @role
 * @time 2019 2019/5/14 14:14
 */

public interface IShareWordView {

    void onShareWord(String bCard, String dynamic);

    void onSetChareWordSuccess();

    void onFail(String msg);
}
