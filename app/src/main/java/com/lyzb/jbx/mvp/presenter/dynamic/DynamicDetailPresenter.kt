package com.lyzb.jbx.mvp.presenter.dynamic

import com.like.longshaolib.base.inter.IRequestListener
import com.like.utilslib.json.GSONUtil
import com.like.utilslib.json.JSONUtil
import com.like.utilslib.other.LogUtil
import com.lyzb.jbx.model.dynamic.*
import com.lyzb.jbx.model.params.IdBody
import com.lyzb.jbx.model.params.TopicIdBody
import com.lyzb.jbx.mvp.APPresenter
import com.lyzb.jbx.mvp.view.dynamic.IDynamicDetailView
import io.reactivex.Observable

/**
 * Created by :TYK
 * Date: 2019/3/14  16:25
 * Desc: 动态详情
 */

class DynamicDetailPresenter : APPresenter<IDynamicDetailView>() {


    /**
     * 获取动态详情
     */
    fun getDynamicDetail(id: String) {
        onRequestData(object : IRequestListener<String> {
            override fun onCreateObservable(): Observable<*> {
                val map = HashMap<String, Any>()
                map["id"] = id
                return dynamicApi.getDynamicDetail(getHeadersMap(GET, "/lbs/gsGroup/topicById"), map)
            }

            override fun onSuccess(t: String?) {
                val bean = GSONUtil.getEntity(t, DynamicDetailModel::class.java)
                if (null != bean) {
                    view.onRequestSuccess(bean)
                } else {
                    showFragmentToast("数据解析错误")
                }
            }

            override fun onFail(message: String?) {
                showFragmentToast(message)
            }

        })
    }

    var startPage = 1
    /**
     * 获取评论列表
     */
    fun getCommentList(isRefresh: Boolean, topicId: String) {
        onRequestData(false, object : IRequestListener<String> {
            override fun onCreateObservable(): Observable<*> {
                val map = HashMap<String, Any>()
                map["pageSize"] = 10
                if (isRefresh) {
                    startPage = 1
                } else {
                    startPage++
                }
                map["pageNum"] = startPage
                map["topicId"] = topicId
                return dynamicApi.getDynamicDetailCommentList(getHeadersMap(GET, "/lbs/comment/pageInfo"), map)
            }

            override fun onSuccess(t: String?) {
                LogUtil.loge("动态详情评论列表回调数据$t")
                val bean = GSONUtil.getEntity(t, DynamicCommentModel::class.java)
                if (null != bean) {
                    view.onSuccess(isRefresh, bean)
                } else {
                    showFragmentToast("数据解析错误")
                }

            }

            override fun onFail(message: String?) {
                showFragmentToast(message)
                view.onFail(isRefresh)
            }

        })
    }


    /**
     * 动态(取消 点赞 收藏)
     * status    1：浏览，2：点赞 ，3：收藏 4评论点赞
     * topicId   主键ID
     */
    fun addLikeOrFollow(status: Int, topicId: String, position: Int) {
        onRequestData(false, object : IRequestListener<String> {
            override fun onCreateObservable(): Observable<*> {
                val body = AddLikeOrFollowBody()
                body.topicId = topicId
                body.type = status.toString()
                return dynamicApi.onAddLikeOrFollow(getHeadersMap(POST, "/lbs/gs/topic/delGsTopicFavour"), body)
            }

            override fun onSuccess(t: String?) {
                LogUtil.loge("动态点赞和收藏回调信息$t")
                val bean = GSONUtil.getEntity(t, AddLikeOrFollowModel::class.java)
                if (null != bean) {
                    if (position == -1) {//动态点赞
                        view.addLikeOrFollow(status, bean)
                    } else {//评论点赞
                        view.addLikeOrFollow(status, bean, position)
                    }
                } else {
                    showFragmentToast("数据解析错误")
                }
            }

            override fun onFail(message: String?) {
                showFragmentToast(message)
            }

        })
    }

    /**
     * 动态(取消 点赞 收藏)
     * status   1：浏览，2：点赞 ，3：收藏 4评论点赞
     * topicId   主键ID
     */
    fun cancelLikeOrFollow(status: Int, topicId: String, position: Int) {
        onRequestData(false,object : IRequestListener<String> {
            override fun onCreateObservable(): Observable<*> {
                val body = AddLikeOrFollowBody()
                body.topicId = topicId
                body.type = status.toString()
                return dynamicApi.onCancelLikeOrFollow(getHeadersMap(POST, "/lbs/gs/topic/delGsTopicFavour"), body)
            }

            override fun onSuccess(t: String?) {
                LogUtil.loge("取消动态点赞和收藏回调信息$t")
                val bean = GSONUtil.getEntity(t, AddLikeOrFollowModel::class.java)
                if (null != bean) {
                    if (position == -1) {//动态取消点赞收藏
                        view.cancleLikeOrFollow(status, bean)
                    } else {//评论取消点赞收藏
                        view.cancleLikeOrFollow(status, bean, position)
                    }
                } else {
                    showFragmentToast("数据解析错误")
                }
            }

            override fun onFail(message: String?) {
                showFragmentToast(message)
            }

        })
    }


    /**
     * 关注用户
     */
    fun onFocusUser(enabled: Int, toUserId: String) {
        onRequestData(object : IRequestListener<String> {
            override fun onCreateObservable(): Observable<*> {
                val body = RequstBodyFocus()
                body.enabled = enabled
                body.toUserId = toUserId
                return dynamicApi.onFocusUser(getHeadersMap(POST, "/lbs/gs/user/saveUsersRelation"), body)
            }

            override fun onSuccess(t: String?) {
                val bean = GSONUtil.getEntity(t, DynamicFocusModel::class.java)
                if (null != bean) {
                    view.resultFocus(bean)
                } else {
                    showFragmentToast("数据解析错误")
                }
            }

            override fun onFail(message: String?) {
                showFragmentToast(message)
            }
        })
    }

    /**
     * 添加评论  添加回复
     */
    fun addCommentOrReply(position: Int, body: RequestBodyComment) {
        onRequestData(true, object : IRequestListener<String> {
            override fun onCreateObservable(): Observable<*> {
                return dynamicApi.addCommentOrReply(getHeadersMap(POST, "/lbs/gs/topic/saveGsTopicComment"), body)
            }

            override fun onSuccess(t: String?) {
                val bean = GSONUtil.getEntity(t, AddCommentOrReplyModel::class.java)
                if (null != bean) {
                    view.addCommentOrReply(position, bean)
                } else {
                    showFragmentToast("数据解析错误")
                }
            }

            override fun onFail(message: String?) {
                showFragmentToast(message)
            }

        })
    }

    /**
     * 分享动态
     */
    fun shareDynamic(dynamicId: String) {
        onRequestData(object : IRequestListener<String> {
            override fun onCreateObservable(): Observable<*> {
                return dynamicApi.shareDynamic(getHeadersMap(POST, "/lbs/gs/topic/saveGsTopicShare"), TopicIdBody(dynamicId))
            }

            override fun onSuccess(t: String?) {
            }

            override fun onFail(message: String?) {
            }
        })
    }

    /**
     * 删除回复
     */
    fun deleteDynamic(id: String, position: Int) {
        onRequestData(object : IRequestListener<String> {
            override fun onCreateObservable(): Observable<*> {
                return dynamicApi.deleteDynamicComment(getHeadersMap(POST, "/lbs/gs/topic/delGsTopicComment"), IdBody(id))
            }

            override fun onSuccess(t: String?) {
                val resultObject = JSONUtil.toJsonObject(t)
                val result: Int = JSONUtil.get(resultObject, "index", 0)
                if (result == 1) {
                    view.onDeleteResult(position)
                } else {
                    showFragmentToast("删除失败")
                }
            }

            override fun onFail(message: String?) {
                showFragmentToast(message)
            }
        })
    }
}