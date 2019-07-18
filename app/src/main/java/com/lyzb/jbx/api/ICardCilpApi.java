package com.lyzb.jbx.api;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.QueryMap;

//名片夹
public interface ICardCilpApi {

    //获取名片夹列表
    @GET("lbs/gs/user/selectMyFolder")
    Observable<Response<String>> getCardClipList(@HeaderMap Map<String, String> heards, @QueryMap Map<String, Object> params);
}
