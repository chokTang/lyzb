package com.lyzb.jbx.api;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.QueryMap;

/**
 * @author wyx
 * @role 统计模块api
 * @time 2019 2019/4/22 10:11
 */

public interface IStatisticsApi {
    /**
     * 数据统计-分享数据
     *
     * @param headers
     * @param body
     * @return
     */
    @GET("lbs/gs/user/selectMyShareNumVo")
    Observable<Response<String>> getAnalysisShare(@HeaderMap Map<String, String> headers, @QueryMap Map<String, String> body);

    /**
     * 数据统计-名片商城交易(企业)
     *
     * @param headers
     * @param body
     * @return
     */
    @GET("lbs/gs/disStatistics/getStatisticsOrderData")
    Observable<Response<String>> getAnalysisTransactionCompany(@HeaderMap Map<String, String> headers, @QueryMap Map<String, Object> body);

    /**
     * 数据统计-名片商城交易(个人)
     *
     * @param headers
     * @param body
     * @return
     */
    @GET("lbs/gs/home/getOrdersByCard")
    Observable<Response<String>> getAnalysisTransactionUser(@HeaderMap Map<String, String> headers, @QueryMap Map<String, Object> body);


    /**
     * 数据统计-访问次数(企业)
     *
     * @param headerParams
     * @param body
     * @return
     */
    @GET("lbs/gs/user/selectCurrentView")
    Observable<Response<String>> getAnalysisVisitCompany(@HeaderMap Map<String, String> headerParams, @QueryMap Map<String, Object> body);

    /**
     * 数据统计-访问次数(个人)
     *
     * @param headers
     * @param body
     * @return
     */
    @GET("lbs/gs/user/getUserTrack")
    Observable<Response<String>> getAnalysisVisitUser(@HeaderMap Map<String, String> headers, @QueryMap Map<String, String> body);

    /**
     * 数据统计-引流新用户(企业)
     *
     * @param headers
     * @param body
     * @return
     */
    @GET("lbs/gs/disStatistics/getStatisticsNewUserData")
    Observable<Response<String>> getAnalysisNewUserCompany(@HeaderMap Map<String, String> headers, @QueryMap Map<String, Object> body);

    /**
     * 数据统计-引流新用户(个人)
     *
     * @param headers
     * @param body
     * @return
     */
    @GET("lbs/gsUserBelong/selectByPageInfo")
    Observable<Response<String>> getAnalysisNewUserUser(@HeaderMap Map<String, String> headers, @QueryMap Map<String, Object> body);

    /**
     * 数据统计-首页
     *
     * @param headers
     * @param body
     * @return
     */
    @GET("lbs/gs/disStatistics/getAllStatisticsData")
    Observable<Response<String>> getStatistics(@HeaderMap Map<String, String> headers, @QueryMap Map<String, String> body);

    /**
     * 数据统计-各种排行榜
     *
     * @param headers
     * @param body
     * @return
     */
    @GET("lbs/gs/disStatistics/getStatisticsRankData")
    Observable<Response<String>> getAnalysisRanking(@HeaderMap Map<String, String> headers, @QueryMap Map<String, Object> body);


}
