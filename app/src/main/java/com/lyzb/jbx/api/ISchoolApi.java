package com.lyzb.jbx.api;

import com.lyzb.jbx.model.params.ShopZanBody;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ISchoolApi {

    //获取共商学院--文章分类
    @GET("lbs/article/articleTypes")
    Observable<Response<String>> getSchoolTypeList(@HeaderMap Map<String, String> headers,@Query("catType") int type);

    //共商学院-根据文章分类查询文章列表
    @GET("lbs/article/getArticleListByCat")
    Observable<Response<String>> getSchoolListByType(@HeaderMap Map<String, String> headers, @QueryMap Map<String, Object> params);

    //文章点赞
    @POST("lbs/article/doThumbs")
    Observable<Response<String>> zan(@HeaderMap Map<String, String> headers, @Body ShopZanBody params);

    //获取文章详情
    @GET("lbs/article/getArticleDetail")
    Observable<Response<String>> getArticleDetail(@HeaderMap Map<String, String> headers, @Query("articleId") String articleId);
}
