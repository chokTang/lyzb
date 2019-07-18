package com.lyzb.jbx.api;

import com.lyzb.jbx.model.params.SendRequestBody;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by :TYK
 * Date: 2019/3/12  14:47
 * Desc:
 */
public interface ISendApi {

    /**
     * 发送图文或则视频
     **/
    @POST("lbs/gs/topic/doPublish")
    Observable<Response<String>> onSendTextOrVideo(@HeaderMap Map<String, String> headers, @Body SendRequestBody params);

    /**
     * 发送图文或则视频  商品
     **/
    @GET("lbs/gs/topic/getHistoryGoods")
    Observable<Response<String>> getHistoryProduct(@HeaderMap Map<String, String> headers, @QueryMap Map<String,String> map);


}
