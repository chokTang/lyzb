package com.lyzb.jbx.api;

import com.lyzb.jbx.model.params.CampaignColloectBody;
import com.lyzb.jbx.model.params.SignCampaginBody;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * 活动模块接口
 */
public interface ICampaginApi {

    //获取首页活动列表
    @GET("lbs/activity/selectByPageList")
    Observable<Response<String>> getHomeCampaginList(@HeaderMap Map<String, String> headerParams, @QueryMap Map<String, Object> params);

    //获取活动详情
    @GET("lbs/activity/selectById")
    Observable<Response<String>> getCampaginDetail(@HeaderMap Map<String, String> headerParams, @Query("id") String id);

    //获取活动的成员列表
    @GET("lbs/participant/selectByPageList")
    Observable<Response<String>> getCampaginMember(@HeaderMap Map<String, String> headerParams, @QueryMap Map<String, Object> params);

    //收藏活动
    @POST("lbs/participant/collectAddOrUpdate")
    Observable<Response<String>> collectionCampaign(@HeaderMap Map<String, String> headerParams, @Body CampaignColloectBody body);

    //取消收藏活动
    @POST("lbs/participant/collectDelete")
    Observable<Response<String>> cancleCollectionCampaign(@HeaderMap Map<String, String> headerParams, @Body CampaignColloectBody body);

    //分享活动
    @FormUrlEncoded
    @POST("lbs/participant/shareAddOrUpdate")
    Observable<Response<String>> shareCampaign(@HeaderMap Map<String, String> headerParams, @Field("activityId") String activityId);

    //报名活动
    @POST("lbs/participant/addOrUpdate")
    Observable<Response<String>> signCampaign(@HeaderMap Map<String, String> headerParams, @Body SignCampaginBody params);
}
