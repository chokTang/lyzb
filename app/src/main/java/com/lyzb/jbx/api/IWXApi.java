package com.lyzb.jbx.api;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author wyx
 * @role
 * @time 2019 2019/5/24 17:03
 */

public interface IWXApi {
    /**
     * 获取微信openid
     */
    @GET("sns/oauth2/access_token")
    Observable<Object> getOpenId(@Query("appid") String appid,
                                 @Query("secret") String secret,
                                 @Query("code") String code,
                                 @Query("grant_type") String grant_type);
}
