package com.lyzb.jbx.api;

import com.like.longshaolib.net.model.HttpResult;
import com.lyzb.jbx.model.launcher.AdvertModel;
import com.lyzb.jbx.model.me.CardMallModel;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Php的接口地址
 */
public interface IPhpCommonApi {

    //获取登陆时，是否配置广告
    @GET("index/information/image-info")
    Observable<HttpResult<AdvertModel>> getAdvertInfo(@Query("sys_type") int sys_type);

    /****
     * 名片-商城 商品列表
     * @param userId
     * @param type
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @GET("goods-java/api-goods-list")
    Observable<HttpResult<CardMallModel>> getCardGoodList(
            @Query("userId") String userId, @Query("type") String type, @Query("cur_page") int pageNumber, @Query("page_size") int pageSize);

    @GET("goods-java/api-goods-list")
    Observable<HttpResult<CardMallModel>> getCompanyGoodList(
            @Query("distributor_id") String userId, @Query("type") String type, @Query("cur_page") int pageNumber, @Query("page_size") int pageSize);

    @FormUrlEncoded
    @POST("goods-java/api-add-goods")
    Observable<HttpResult<Object>> addGoods(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("goods-java/api-delete")
    Observable<HttpResult<Object>> deleteGoods(@FieldMap Map<String, Object> params);

    @Headers("User-Agent: szyapp/android")
    @POST("site/logout")
    Observable<HttpResult<Object>> loginOut();

    //我的企业-团购企业-企业账号重置密码
    @Headers("User-Agent: szyapp/android")
    @FormUrlEncoded
    @POST("site/quick-edit-password")
    Observable<HttpResult<Object>> onQuickModifyPassword(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("user/security/sms-captcha-gs")
    Observable<HttpResult<Object>> securitySMS(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("user/security/validate-mobile-gs")
    Observable<HttpResult<Object>> validateMobile(@FieldMap Map<String, Object> params);

    /**
     * 绑定微信
     */
    @Headers("User-Agent: szyapp/android")
    @FormUrlEncoded
    @POST("user/account-manage/wx-user-bind")
    Observable<HttpResult<Object>> binDingWX(@FieldMap Map<String, Object> params);

    /**
     * 检查微信是否绑定
     */
    @Headers("User-Agent: szyapp/android")
    @FormUrlEncoded
    @POST("user/account-manage/wx-user-check")
    Observable<HttpResult<Object>> checkBinDing(@FieldMap Map<String, Object> params);

    /**
     * 解除微信绑定
     */
    @Headers("User-Agent: szyapp/android")
    @FormUrlEncoded
    @POST("user/account-manage/wx-user-del")
    Observable<HttpResult<Object>> removeBinDing(@FieldMap Map<String, Object> params);

    /**
     * 生成密码
     */
    @Headers("User-Agent: szyapp/android")
    @FormUrlEncoded
    @POST("user/security/build-password")
    Observable<HttpResult<Object>> getPassword(@FieldMap Map<String, String> params);
}
