package com.like.longshaolib.net.helper;

import com.like.longshaolib.net.model.HttpResult;
import com.like.utilslib.other.LogUtil;

import io.reactivex.functions.Function;

/**
 * 数据拦截器
 * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
 *
 * @param <T> Subscriber需要的数据类型，也就是返回中data数据类型
 *            Created by longshao on 2017/3/20.
 */

public class HttpResultFunc<T> implements Function<HttpResult<T>, T> {

    @Override
    public T apply(HttpResult<T> tHttpResultFunc) {
        if (tHttpResultFunc.getCode() != 0) {
//            throw new HttpResultApiException(tHttpResultFunc.getStatus());
            throw new HttpResultApiException(tHttpResultFunc.getMessage());
        }
        return tHttpResultFunc.getData()==null? (T) "" :tHttpResultFunc.getData();
    }
}
