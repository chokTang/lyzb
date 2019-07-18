package com.lyzb.jbx.mvp.presenter.me.company

import com.like.longshaolib.base.inter.IRequestListener
import com.like.utilslib.json.GSONUtil
import com.like.utilslib.json.JSONUtil
import com.lyzb.jbx.model.account.BusinessModel
import com.lyzb.jbx.model.me.company.OrganInfoResponse
import com.lyzb.jbx.model.params.AddOrganBody
import com.lyzb.jbx.mvp.APPresenter
import com.lyzb.jbx.mvp.view.me.IAddOrganView
import io.reactivex.Observable
import org.json.JSONObject

/**
 *@author wyx
 *@version
 *@role
 *@time 2019 2019/6/14 14:14
 */
class AddOrganPresenter : APPresenter<IAddOrganView>() {

    fun getOrganInfo(companyId: String) {

        onRequestData(object : IRequestListener<String> {
            override fun onCreateObservable(): Observable<*> {
                return meApi.getOrganInfo(getHeadersMap(GET, "/lbs/gs/org/getCompanyOrgInfo"), companyId)
            }

            override fun onSuccess(t: String?) {
                val data: OrganInfoResponse? = GSONUtil.getEntity(t, OrganInfoResponse::class.java)
                        ?: return
                if (data!!.code != "200") {
                    showFragmentToast(data.msg)
                    return
                }
                view.onOrganData(data.data)

            }

            override fun onFail(message: String?) {
                showFragmentToast(message)
            }
        })
    }

    /**
     *添加、修改、删除机构
     */
    fun addOrgan(body: AddOrganBody) {
        onRequestData(object : IRequestListener<String> {
            override fun onCreateObservable(): Observable<*> {
                return meApi.addOrgan(getHeadersMap(POST, "/lbs/gs/org/addOrUptOrg"), body)
            }

            override fun onSuccess(t: String?) {
                val code = JSONUtil.get(JSONUtil.toJsonObject(t), "code", "")
                if (code != "200") {
                    val msg = JSONUtil.get(JSONUtil.toJsonObject(t), "msg", "")
                    showFragmentToast(msg)
                    return
                }
                view.onAddOrganSuccess()
            }

            override fun onFail(message: String?) {
                showFragmentToast(message)
            }
        })
    }

    /**
     * 查询能否删除机构
     */
    fun canDeleteOrgan(orgid: String) {
        onRequestData(object : IRequestListener<String> {
            override fun onCreateObservable(): Observable<*> {
                return meApi.canDeleteOrgan(getHeadersMap(GET, "/lbs/gs/org/getCanDelOrg"), orgid)
            }

            override fun onSuccess(t: String?) {
                val jsonObject = JSONUtil.toJsonObject(t)
                val code = JSONUtil.get(jsonObject, "code", "0")
                if (code == "200" || code == "203") {
                    val canDelSt = JSONUtil.get(jsonObject, "canDelSt", false)
                    view.canDeleteOrganSuccess(canDelSt)
                } else {
                    showFragmentToast(JSONUtil.get(jsonObject, "msg", ""))
                }
            }

            override fun onFail(message: String?) {
                showFragmentToast(message)
            }
        })
    }

    /***
     * 获取 企业类型 data
     *
     */
    fun getInducList() {
        onRequestData(false, object : IRequestListener<String> {
            override fun onCreateObservable(): Observable<*> {
                return meApi.getIndustryList(getHeadersMap(GET, "/lbs/gs/user/selectGsProfessionList"))
            }

            override fun onSuccess(s: String) {
                val modelList = GSONUtil.getEntityList(s, BusinessModel::class.java)  //行业信息list
                view.getInducListSuccess(modelList)
            }

            override fun onFail(message: String) {
                showFragmentToast(message)
            }
        })
    }
}