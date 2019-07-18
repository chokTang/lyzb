package com.like.longshaolib.pay;

/**
 * Created by Administrator on 2018/3/30.
 */

public interface IWXPayResultListener {
    void onPaySucess(String message);

    void onPayFail(String message);
}
