package com.lyzb.jbx.mvp.presenter.me.company

import com.like.longshaolib.base.inter.IRequestListener
import com.like.utilslib.json.GSONUtil
import com.lyzb.jbx.model.me.company.ChoiceOrganModel
import com.lyzb.jbx.mvp.APPresenter
import com.lyzb.jbx.mvp.view.me.company.IChoiceOrganView
import io.reactivex.Observable

/**
 *@author wyx
 *@version
 *@role
 *@time 2019 2019/6/17 9:53
 */
class ChoiceOrganPresenter : APPresenter<IChoiceOrganView>() {
    /**
     * 机构类型（1.企业 2.机构）
     */
    fun getOrganList(distributorId: String, dataType: String) {
        onRequestData(object : IRequestListener<String> {
            override fun onCreateObservable(): Observable<*> {
                val map = HashMap<String, Any>()
                map["distributorId"] = distributorId
                map["dataType"] = dataType
                return meApi.getOrganList(getHeadersMap(GET, "/lbs/gs/org/getUptSelectOrgList"), map)
            }

            override fun onSuccess(t: String?) {
                val data = GSONUtil.getEntity(t, ChoiceOrganModel::class.java) ?: return
                if (data.code != "200" || data.data == null || data.data.size < 1) {
                    showFragmentToast(data.msg)
                    return
                }
                view.onSuccess(data.data)
            }

            override fun onFail(message: String?) {
                showFragmentToast(message)
            }
        })
    }

}