package com.lyzb.jbx.api;

import com.lyzb.jbx.model.dynamic.AddLikeOrFollowBody;
import com.lyzb.jbx.model.dynamic.RequestBodyComment;
import com.lyzb.jbx.model.dynamic.RequstBodyFocus;
import com.lyzb.jbx.model.params.IdBody;
import com.lyzb.jbx.model.params.TopicIdBody;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface IDynamicApi {

    //关注下的动态列表
    @GET("lbs/gs/home/mySubscribersList")
    Observable<Response<String>> getFollowDynamicList(@HeaderMap Map<String, String> heards, @QueryMap Map<String, Object> params);

    //关注下的动态详情
    @GET("lbs/gsGroup/topicById")
    Observable<Response<String>> getDynamicDetail(@HeaderMap Map<String, String> heards, @QueryMap Map<String, Object> params);

    @GET("lbs/gsGroup/topicInToGroupDetail")
    Observable<Response<String>> getDynamicDetailTwo(@HeaderMap Map<String, String> heards, @QueryMap Map<String, Object> params);

    //关注下的动态详情-评论列表
    @GET("lbs/comment/pageInfo")
    Observable<Response<String>> getDynamicDetailCommentList(@HeaderMap Map<String, String> heards, @QueryMap Map<String, Object> params);

    //关注下的动态详情-点赞列表
    @GET("lbs/comment/give")
    Observable<Response<String>> getDynamicDetailLikeList(@HeaderMap Map<String, String> heards, @QueryMap Map<String, Object> params);

    //动态详情下-浏览列表
    @GET("lbs/gsGroup/browseList")
    Observable<Response<String>> getDynamicDetailScanList(@HeaderMap Map<String, String> heards, @QueryMap Map<String, Object> params);

    //搜索页面-用户动态
    @GET("lbs/gs/home/searchGsUser")
    Observable<Response<String>> getSearchAccountDynamic(@HeaderMap Map<String, String> heards, @QueryMap Map<String, Object> params);

    //搜索页面-动态列表
    @GET("lbs/gs/home/searchTopic")
    Observable<Response<String>> getSearchDynamic(@HeaderMap Map<String, String> heards, @QueryMap Map<String, Object> params);

    //取消点赞 收藏
    @POST("lbs/gs/topic/delGsTopicFavour")
    Observable<Response<String>> onCancelLikeOrFollow(@HeaderMap Map<String, String> headers, @Body AddLikeOrFollowBody body);

    //添加点赞 收藏
    @POST("lbs/gs/topic/saveGsTopicFavour")
    Observable<Response<String>> onAddLikeOrFollow(@HeaderMap Map<String, String> headers, @Body AddLikeOrFollowBody body);

    //名片(关注/取消关注)
    @POST("lbs/gs/user/saveUsersRelation")
    Observable<Response<String>> onFocusUser(@HeaderMap Map<String, String> headers, @Body RequstBodyFocus body);

    //评论  回复
    @POST("lbs/gs/topic/saveGsTopicComment")
    Observable<Response<String>> addCommentOrReply(@HeaderMap Map<String, String> headers, @Body RequestBodyComment body);

    //评论  回复
    @POST("lbs/gs/topic/saveGsTopicShare")
    Observable<Response<String>> shareDynamic(@HeaderMap Map<String, String> headers, @Body TopicIdBody body);

    //删除 回复
    @POST("lbs/gs/topic/delGsTopicComment")
    Observable<Response<String>> deleteDynamicComment(@HeaderMap Map<String, String> headers, @Body IdBody body);
}
