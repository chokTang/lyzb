package com.lyzb.jbx.api;

import com.lyzb.jbx.model.params.ImUserInfoBody;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
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
import retrofit2.http.Url;

/**
 * 公共的serviceAPi接口
 *
 * @author longshao 2018年10月8日 14:18:01
 * // http://t.weather.sojson.com/api/weather/city/101030100 测试接口
 */
public interface ICommonApi {


    @GET()
    Observable<String> getUrlData(@Url String mUrl);

    /**
     * 下载文件
     *
     * @param mDownUrl
     * @return
     */
    @Streaming
    @GET()
    Observable<ResponseBody> downLoadFile(@Url String mDownUrl);

    //搜索关键字 搜索页面数据
    @GET("esapi/lbs/search/suggest")
    Observable<Response<String>> getSearchByKey(@HeaderMap Map<String, String> headers, @Query("msg") String msg);

    //搜索页面，获取搜索上的商品列表
    @GET("esapi/lbs/search")
    Observable<Response<String>> getSearchGoodsList(@HeaderMap Map<String, String> headers, @QueryMap Map<String, Object> params);

    //获取推荐下面的动态列表
    @GET("esapi/lbs/user/getTopic")
    Observable<Response<String>> getRecomendDynamicList(@QueryMap Map<String, Object> params);

    //视屏动态列表
    @GET("esapi/lbs/user/getVideoTopic")
    Observable<Response<String>> getVideoTopic(@HeaderMap Map<String, String> heards, @QueryMap Map<String, Object> params);

    //获取.net地区的接口
    @GET("lbs/gs/distributor/getOptChildren")
    Observable<Response<String>> getRecomendDynamicList(@HeaderMap Map<String, String> headers, @Query("regionId") String regionId);

    //获取IM下的 用户个人信息查询-个人用户信息查询（环信注册修改集成）
    @POST("lbs/queryUserNameAndPassword")
    Observable<Response<String>> getImUserInfo(@HeaderMap Map<String, String> headers, @Body() ImUserInfoBody body);

    //获取呱呱上的热文推广商品
    @POST("http://m.jbxgo.com/goods/skutg")  //正式服
//    @POST("http://m.jibaoh.com/goods/skutg")  //测试服
    @FormUrlEncoded
    Observable<Response<String>> getGuaGuaHotShop(@FieldMap Map<String, String> params);
}
