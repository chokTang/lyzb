package com.lyzb.jbx.mvp.presenter.me.card

import com.like.longshaolib.base.inter.IRequestListener
import com.like.utilslib.json.GSONUtil
import com.like.utilslib.other.LogUtil
import com.lyzb.jbx.model.me.AccessModel
import com.lyzb.jbx.mvp.APPresenter
import com.lyzb.jbx.mvp.presenter.dynamic.BaseDynamicPresenter
import com.lyzb.jbx.mvp.view.me.ICardAccessRecordView
import io.reactivex.Observable

/**
 * Created by :TYK
 * Date: 2019/4/18  13:57
 * Desc: 浏览记录 presenter
 */

class CardAccessRecordPresenter : BaseDynamicPresenter<ICardAccessRecordView>() {

    /**
     * 获取浏览记录
     */
    fun getAccessRecordList(isRefresh: Boolean,userId:String) {
        if (isRefresh) {
            pageIndexDynamic = 1
        } else {
            pageIndexDynamic++
        }
        onRequestData(false, object : IRequestListener<String> {
            override fun onCreateObservable(): Observable<*> {
                val map = HashMap<String, Any>()
                map["pageNum"] = pageIndexDynamic
                map["pageSize"] = pageSize
                map["userId"] = userId
                return meApi.getAccessRecordList(getHeadersMap(GET, "/lbs/gs/user/selectMyViewList"), map)
            }

            override fun onSuccess(t: String?) {
                LogUtil.loge("当前的参数"+t)
                val model = GSONUtil.getEntity(t,AccessModel::class.java)
                view.getRecordList(isRefresh,model.total,model.list)
            }

            override fun onFail(message: String?) {
            }

        })
    }

}