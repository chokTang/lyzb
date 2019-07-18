package com.lyzb.jbx.api;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.QueryMap;

public interface IFollowApi {

    //获取我的粉丝列表
    @GET("lbs/gs/home/getSimpleRelation")
    Observable<Response<String>> getMyFollowList(@HeaderMap Map<String, String> params);

    //获取可能认识人的列表
    @GET("lbs/gs/home/maybeKnow")
    Observable<Response<String>> getAdoutMeList(@HeaderMap Map<String, String> headers, @QueryMap Map<String, Object> params);

    //获取推荐下--可能感兴趣的人
    @GET("lbs/gs/home/maybeInterest")
    Observable<Response<String>> getMeInterest(@HeaderMap Map<String, String> headers, @QueryMap Map<String, Object> params);
}
