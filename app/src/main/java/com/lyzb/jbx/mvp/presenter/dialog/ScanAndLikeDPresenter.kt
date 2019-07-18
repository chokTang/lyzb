package com.lyzb.jbx.mvp.presenter.dialog

import android.widget.Toast
import com.like.longshaolib.app.LongshaoAPP
import com.like.longshaolib.base.inter.IRequestListener
import com.like.utilslib.json.GSONUtil
import com.like.utilslib.json.JSONUtil
import com.lyzb.jbx.model.dynamic.DynamicLikeModel
import com.lyzb.jbx.model.dynamic.DynamicScanModel
import com.lyzb.jbx.mvp.APPresenter
import com.lyzb.jbx.mvp.view.dialog.IScanAndLikeDView
import io.reactivex.Observable

class ScanAndLikeDPresenter : APPresenter<IScanAndLikeDView>() {

    var startPage = 1
    var sacnPage = 1
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
                val bean = GSONUtil.getEntity(t, DynamicLikeModel::class.java)
                if (null != bean) {
                    view.onSuccess(isRefresh, bean)
                } else {
                    Toast.makeText(LongshaoAPP.getApplicationContext(), "数据解析错误", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFail(message: String?) {
                Toast.makeText(LongshaoAPP.getApplicationContext(), message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    //获取浏览的数据
    fun getScanList(isRefresh: Boolean, id: String) {
        onRequestData(false, object : IRequestListener<String> {
            override fun onCreateObservable(): Observable<*> {
                val map = HashMap<String, Any>()
                map["pageSize"] = 10
                if (isRefresh) {
                    sacnPage = 1
                } else {
                    sacnPage++
                }
                map["id"] = id
                map["pageNum"] = sacnPage
                return dynamicApi.getDynamicDetailScanList(getHeadersMap(GET, "/lbs/gsGroup/browseList"), map)
            }

            override fun onSuccess(t: String?) {
                val resultObject =JSONUtil.toJsonObject(t)
                val listArray =JSONUtil.getJsonArray(resultObject,"list")
                val total=JSONUtil.get(resultObject,"total",0)
                val bean = GSONUtil.getEntityList(listArray.toString(), DynamicScanModel::class.java)
                view.onScanResultList(isRefresh,total,bean)
            }

            override fun onFail(message: String?) {
                Toast.makeText(LongshaoAPP.getApplicationContext(), message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}