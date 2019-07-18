package com.lyzb.jbx.mvp.presenter.me.company

import com.google.gson.Gson
import com.like.longshaolib.base.inter.IRequestListener
import com.like.utilslib.json.GSONUtil
import com.like.utilslib.json.JSONUtil
import com.lyzb.jbx.model.me.company.OrganNenberModel
import com.lyzb.jbx.model.params.ChangeAdminBody
import com.lyzb.jbx.mvp.APPresenter
import com.lyzb.jbx.mvp.view.me.IOrganAdminView
import io.reactivex.Observable

/**
 *@author wyx
 *@version
 *@role 企业管理员
 *@time 2019 2019/6/15 9:58
 */
class OrganAdminPresenter : APPresenter<IOrganAdminView>() {

    fun getOrganMember(orgId: String, queryType: String) {
        onRequestData(object : IRequestListener<String> {
            override fun onCreateObservable(): Observable<*> {
                val map = HashMap<String, Any>()
                map["orgId"] = orgId
                map["queryType"] = queryType
                return meApi.getOrganMember(getHeadersMap(GET, "/lbs/gs/org/getOrgManagersOrMember"), map)
            }

            override fun onSuccess(t: String?) {
                val model = GSONUtil.getEntity(t, OrganNenberModel::class.java) ?: return
                if (model.code != "200") {
                    showFragmentToast(model.msg)
                    return
                }
                view.getMenberListSuccess(model.data)
            }

            override fun onFail(message: String?) {
                showFragmentToast(message)
            }
        })
    }

    /**
     * 添加or删除管理员
     */
    fun changeAdmin(list: MutableList<ChangeAdminBody>) {

        onRequestData(object : IRequestListener<String> {
            override fun onCreateObservable(): Observable<*> {
                val roleList = ChangeAdminBody()
                roleList.roleList = list
                return meApi.changeAdmin(getHeadersMap(POST, "/lbs/gs/org/doOptOrgManager"), roleList)
            }

            override fun onSuccess(t: String?) {
                val code = JSONUtil.get(JSONUtil.toJsonObject(t), "code", "")
                if (code == "200") {
                    view.onChangeAdminSuccess()
                } else {
                    val msg = JSONUtil.get(JSONUtil.toJsonObject(t), "msg", "")
                    showFragmentToast(msg)
                }
            }

            override fun onFail(message: String?) {
                showFragmentToast(message)
            }
        })
    }

}