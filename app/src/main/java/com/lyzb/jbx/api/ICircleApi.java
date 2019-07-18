package com.lyzb.jbx.api;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.QueryMap;

public interface ICircleApi {

    //获取首页我的和推荐的圈子
    @GET("lbs/gs/home/getRecommendGroup")
    Observable<Response<String>> getHomeMyCircle(@HeaderMap Map<String, String> headers);

    //获取更多的圈子
    @GET("lbs/gsGroup/selectByPageList")
    Observable<Response<String>> getMoreCircle(@HeaderMap Map<String, String> headers, @QueryMap Map<String, Object> params);

    //搜索圈子
    @GET("lbs/gs/home/searchGroup")
    Observable<Response<String>> searchCircle(@HeaderMap Map<String, String> headers, @QueryMap Map<String, Object> params);

    //获取我的圈子动态列表
    @GET("lbs/gs/home/myGroupTopic")
    Observable<Response<String>> getCircleDynamic(@HeaderMap Map<String, String> headers, @QueryMap Map<String, Object> params);
}
