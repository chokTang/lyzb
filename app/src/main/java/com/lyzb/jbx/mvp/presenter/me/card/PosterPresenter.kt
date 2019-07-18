package com.lyzb.jbx.mvp.presenter.me.card

import com.like.longshaolib.base.inter.IRequestListener
import com.like.utilslib.json.GSONUtil
import com.lyzb.jbx.model.me.MiniQrUrlModel
import com.lyzb.jbx.model.me.TabShowHideBean
import com.lyzb.jbx.mvp.APPresenter
import com.lyzb.jbx.mvp.view.me.IPosterView
import io.reactivex.Observable
import java.util.HashMap

/**
 * Created by :TYK
 * Date: 2019/5/25  13:55
 * Desc:
 */
class PosterPresenter: APPresenter<IPosterView>(){


    fun getCardTabList(cardUserId: String, shareUserId: String) {

        onRequestData(true, object : IRequestListener<String> {
            override fun onCreateObservable(): Observable<*> {
                val map = HashMap<String, Any>()
                map["scene"] = "a=$cardUserId&b=$shareUserId"
                map["page"] = "pages/card/card"
                return APPresenter.meApi.getMiniqrQrUrl(getHeadersMap(APPresenter.GET, "/lbs/wxminiapp/user/getminiqrQrUrl"), map)
            }

            override fun onSuccess(s: String) {
                val model =  GSONUtil.getEntity(s, MiniQrUrlModel::class.java)
                view.getMiniqrQrUrlSucess(model)
            }

            override fun onFail(message: String) {
                showFragmentToast(message)
            }
        })
    }


}