package com.like.longshaolib.net.helper;

import android.text.TextUtils;

import com.like.utilslib.json.JSONUtil;
import com.like.utilslib.other.LogUtil;

import org.json.JSONObject;

import io.reactivex.functions.Function;
import retrofit2.Response;

/**
 * 数据拦截器
 * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
 *
 * @param <String> Subscriber需要的数据类型，也就是返回中data数据类型
 *                 Created by longshao on 2017/3/20.
 */

public class HttpResultStringFunc<String> implements Function<Response<String>, String> {

    @Override
    public String apply(Response<String> stringResponse) throws Exception {
        if (stringResponse.code() != 200) {
            JSONObject object = JSONUtil.toJsonObject(stringResponse.errorBody().string());
            Object errorMessage =JSONUtil.get(object,"message","");
            if (TextUtils.isEmpty(errorMessage.toString())){
                throw new HttpResultApiException(stringResponse.message());
            }else {
                throw new HttpResultApiException(errorMessage.toString());
            }
        }
        return stringResponse.body();
    }
}
