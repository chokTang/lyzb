package com.lyzb.jbx.mvp.presenter.dynamic

import com.like.longshaolib.base.inter.IRequestListener
import com.like.utilslib.json.GSONUtil
import com.like.utilslib.other.LogUtil
import com.lyzb.jbx.model.dynamic.AddLikeOrFollowBody
import com.lyzb.jbx.model.dynamic.AddLikeOrFollowModel
import com.lyzb.jbx.model.dynamic.DynamicCommentModel
import com.lyzb.jbx.mvp.APPresenter
import com.lyzb.jbx.mvp.view.dynamic.ICommentView
import io.reactivex.Observable

/**
 * Created by :TYK
 * Date: 2019/3/14  20:18
 * Desc:
 */

class CommentPresenter: APPresenter<ICommentView>(){

    var  startPage = 1
    /**
     * 获取评论列表
     */
    fun getCommentList(isRefresh:Boolean,topicId:String){

        onRequestData(false,object :IRequestListener<String>{
            override fun onCreateObservable(): Observable<*> {
                val map = HashMap<String,Any>()
                map["pageSize"] = 10
                if (isRefresh){
                    startPage = 1
                }else{
                    startPage ++
                }
                map["pageNum"] = startPage
                map["topicId"] = topicId
                return dynamicApi.getDynamicDetailCommentList(getHeadersMap(GET,"/lbs/comment/pageInfo"),map)
            }

            override fun onSuccess(t: String?) {
                LogUtil.loge("动态详情评论列表回调数据$t")
                val bean = GSONUtil.getEntity(t, DynamicCommentModel::class.java)
                if (null!=bean){
                    view.onSuccess(isRefresh,bean)
                }else{
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
     * status    1：浏览，2：点赞 ，3：收藏 4评论点赞
     * topicId   主键ID
     */
    fun addLikeOrFollow(status:Int,topicId:String,position:Int){
        onRequestData(object :IRequestListener<String>{
            override fun onCreateObservable(): Observable<*> {
                val body = AddLikeOrFollowBody()
                body.topicId = topicId
                body.type = status.toString()
                return dynamicApi.onAddLikeOrFollow(getHeadersMap(POST,"/lbs/gs/topic/delGsTopicFavour"),body)
            }

            override fun onSuccess(t: String?) {
                LogUtil.loge("动态点赞和收藏回调信息$t")
                val bean = GSONUtil.getEntity(t, AddLikeOrFollowModel::class.java)
                if (null!=bean){
                    view.addLikeOrFollow(status,bean,position)
                }else{
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
    fun cancelLikeOrFollow(status:Int,topicId:String,position:Int){
        onRequestData(object :IRequestListener<String>{
            override fun onCreateObservable(): Observable<*> {
                val body = AddLikeOrFollowBody()
                body.topicId = topicId
                body.type = status.toString()
                return dynamicApi.onCancelLikeOrFollow(getHeadersMap(POST,"/lbs/gs/topic/delGsTopicFavour"),body)
            }

            override fun onSuccess(t: String?) {
                LogUtil.loge("取消动态点赞和收藏回调信息$t")
                val bean = GSONUtil.getEntity(t, AddLikeOrFollowModel::class.java)
                if (null!=bean){
                    view.cancleLikeOrFollow(status,bean,position)
                }else{
                    showFragmentToast("数据解析错误")
                }
            }

            override fun onFail(message: String?) {
                showFragmentToast(message)
            }

        })
    }


}