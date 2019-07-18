package com.lyzb.jbx.api;

import com.lyzb.jbx.model.account.RequestPerfectBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;

/**
 * 用户相关API
 * Created by Administrator on 2017/12/30.
 */
public interface IAccountApi {

    //注册
    @FormUrlEncoded
    @POST("user/logreg/register")
    Observable<String> onRegister(@FieldMap Map<String, Object> params);

    //加密登录
    @FormUrlEncoded
    @POST("user/logreg/login")
    Observable<String> onLogin(@FieldMap Map<String, Object> params);

    //用户修改或者找回密码
    @FormUrlEncoded
    @POST("user/logreg/edit_password")
    Observable<String> onModifyPassword(@FieldMap Map<String, Object> params);

    /**
     * 我的- 完善信息- 个人信息完善
     **/
    @POST("lbs/gs/user/doPerfectInfo")
    Observable<Response<String>> onUpdateMessage(@HeaderMap Map<String, String> headers, @Body RequestPerfectBean body);

    /**
     * 我的- 完善信息- 获取行业列表
     **/
    @GET("lbs/gs/user/selectGsProfessionList")
    Observable<Response<String>> onGetListBusiness(@HeaderMap Map<String, String> headers);

    /**
     * 传单个文件
     *
     * @param headers
     * @return
     */
    @Streaming
    @GET("lbs/oss/ossAuthentication")
    Observable<Response<String>> uploadMultipleTypeFile(@HeaderMap Map<String, String> headers, @QueryMap Map<String, RequestBody> params);

    /**
     * 传多个文件
     *
     * @param params
     * @returns
     */
    @Streaming
    @GET("lbs/oss/ossAuthentication")
    Observable<Response<String>> uploadMultipleTypeFiles(@HeaderMap Map<String, String> headers, @QueryMap Map<String, RequestBody> params);

    /**
     * 我的- 完善信息- 获取行业列表
     **/
    @GET("lbs/gsGroup/queryUserByAllGroup")
    Observable<Response<String>> onGetListGroup(@HeaderMap Map<String, String> headers, @QueryMap Map<String, Object> params);

    //获取推荐页面的功能入口+是否完善信息列表
    @GET("lbs/gs/home/getEntrance")
    Observable<Response<String>> getFunctionList(@HeaderMap Map<String, String> headers);

    /**
     * 我的- 完善信息-获取商家名称联想
     **/
    @GET("lbs/gs/user/queryDistributorByName")
    Observable<Response<String>> queryDistributorByName(@HeaderMap Map<String, String> headers, @QueryMap Map<String, Object> params);

    //获取推荐页面下的banner列表
    @GET("lbs/user/advertByPosition")
    Observable<Response<String>> getRecommentBanner(@HeaderMap Map<String, String> headers, @Query("position") String position);

    //获取用户是否完善信息
    @GET("lbs/gs/user/queryIsPerfect")
    Observable<Response<String>> getAccountIsPerfect(@HeaderMap Map<String, String> headers);

    //获取用户发送图文标签
    @GET("lbs/gs/topic/getRecommendTopics")
    Observable<Response<String>> getTagList(@HeaderMap Map<String, String> headers);
}
