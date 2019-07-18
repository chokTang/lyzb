package com.lyzb.jbx.mvp.presenter.dynamic

import com.like.longshaolib.base.inter.IRequestListener
import com.like.utilslib.json.GSONUtil
import com.like.utilslib.other.LogUtil
import com.lyzb.jbx.model.dynamic.DynamicLikeModel
import com.lyzb.jbx.mvp.APPresenter
import com.lyzb.jbx.mvp.view.dynamic.ILikeView
import io.reactivex.Observable

/**
 * Created by :TYK
 * Date: 2019/3/14  20:17
 * Desc:
 */

class LikePresenter : APPresenter<ILikeView>() {
    var startPage = 1
    /**
     * 获取点赞列表
     */
    fun getLikeList(isRefresh: Boolean, topicId: String) {
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
                return dynamicApi.getDynamicDetailLikeList(getHeadersMap(GET, "/lbs/comment/give"), map)
            }

            override fun onSuccess(t: String?) {
                LogUtil.loge("动态详情点赞列表回调数据$t")
                val bean = GSONUtil.getEntity(t, DynamicLikeModel::class.java)

                if (null != bean) {
                    view.onSuccess(isRefresh, bean)
                } else {
                    showFragmentToast("数据解析错误")
                }
            }

            override fun onFail(message: String?) {
                showFragmentToast(message)
            }

        })
    }

}