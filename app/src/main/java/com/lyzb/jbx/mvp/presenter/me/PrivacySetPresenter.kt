package com.lyzb.jbx.mvp.presenter.me

import com.like.longshaolib.base.inter.IRequestListener
import com.lyzb.jbx.model.params.UserPrivacyInfoBody
import com.lyzb.jbx.mvp.APPresenter
import com.lyzb.jbx.mvp.view.me.IPrivacySetView
import io.reactivex.Observable

/**
 * Created by :TYK
 * Date: 2019/4/19  14:33
 * Desc: 隐私设置
 */
class PrivacySetPresenter : APPresenter<IPrivacySetView>() {

    /**
     * 设置隐私开关
     */
    fun setPrivace(showInfo: String) {
        onRequestData(false, object : IRequestListener<String> {
            override fun onCreateObservable(): Observable<*> {
                return meApi.setPrivacyswitch(getHeadersMap(POST, "/lbs/gs/user/updateGaUserExtShowInfo"), UserPrivacyInfoBody(showInfo))
            }

            override fun onSuccess(t: String?) {
                view.setPrivacySwith(showInfo)
            }

            override fun onFail(message: String?) {
                showFragmentToast(message)
            }
        })
    }
}