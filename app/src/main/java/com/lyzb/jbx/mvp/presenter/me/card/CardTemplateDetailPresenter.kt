package com.lyzb.jbx.mvp.presenter.me.card

import com.like.longshaolib.base.inter.IRequestListener
import com.like.utilslib.json.GSONUtil
import com.lyzb.jbx.model.me.CardTemplateModel
import com.lyzb.jbx.model.me.SaveTemplateBody
import com.lyzb.jbx.mvp.APPresenter
import com.lyzb.jbx.mvp.view.me.ICardTemplateDetailView
import io.reactivex.Observable

/**
 * Created by :TYK
 * Date: 2019/5/6  14:30
 * Desc:
 */
class CardTemplateDetailPresenter : APPresenter<ICardTemplateDetailView>() {

    /**
     * 拿到名片模板列表
     */
    fun getTemplateList() {
        onRequestData(false, object : IRequestListener<String> {
            override fun onCreateObservable(): Observable<*> {
                val map = HashMap<String, Any>()
                return meApi.getTemplateList(getHeadersMap(GET, "/lbs/gs/user/selectGsTemplateByUser"), map)
            }

            override fun onSuccess(t: String?) {
                val list = GSONUtil.getEntityList(t, CardTemplateModel::class.java)
                view.getTemplateList(list)
            }

            override fun onFail(message: String?) {
            }

        })
    }

    /**
     * 启用名片模板
     */
    fun saveCardTemlate(body: SaveTemplateBody) {
        onRequestData(false, object : IRequestListener<String> {
            override fun onCreateObservable(): Observable<*> {
                return meApi.saveCardTemplate(getHeadersMap(POST, "/lbs/gs/goods/startUsingGsTemplate"), body)
            }

            override fun onSuccess(t: String?) {
                val list = GSONUtil.getEntityList(t, CardTemplateModel::class.java)
                for (i in 0 until list.size){
                    if (list[i].isSelectState){
                        view.saveTemplate(list[i])
                    }
                }
            }

            override fun onFail(message: String?) {
            }

        })
    }


}