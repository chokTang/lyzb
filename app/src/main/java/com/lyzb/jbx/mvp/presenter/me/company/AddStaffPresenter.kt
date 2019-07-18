package com.lyzb.jbx.mvp.presenter.me.company

import com.like.longshaolib.base.inter.IRequestListener
import com.like.utilslib.json.GSONUtil
import com.like.utilslib.json.JSONUtil
import com.lyzb.jbx.model.me.ResultModel
import com.lyzb.jbx.model.me.company.AccountPrefixModel
import com.lyzb.jbx.model.me.company.StaffInfoModel
import com.lyzb.jbx.model.params.AddStaffBody
import com.lyzb.jbx.model.params.AuditMembersBody
import com.lyzb.jbx.mvp.APPresenter
import com.lyzb.jbx.mvp.view.me.IAddStaffView
import io.reactivex.Observable

/**
 *@author wyx
 *@version
 *@role
 *@time 2019 2019/6/14 14:45
 */
class AddStaffPresenter : APPresenter<IAddStaffView>() {
    /**
     * 获取帐号前缀
     */
    fun getAccountPrefix(organId: String) {
        onRequestData(object : IRequestListener<String> {
            override fun onCreateObservable(): Observable<*> {
                return meApi.getAccountPrefix(getHeadersMap(GET, "/lbs/gs/org/getAccountPrefix"), organId)
            }

            override fun onSuccess(t: String?) {
                val model = GSONUtil.getEntity(t, AccountPrefixModel::class.java)
                if (model.code != 200) {
                    showFragmentToast(model.msg)
                    return
                }
                view.getAccountPrefixSuccess(model.data)
            }

            override fun onFail(message: String?) {
                showFragmentToast(message)
            }

        })
    }

    /**
     * 获取用户信息
     */
    fun getStaffDetail(userId: String, orgId: String) {
        onRequestData(object : IRequestListener<String> {
            override fun onCreateObservable(): Observable<*> {
                val map = HashMap<String, String>()
                map["userId"] = userId
                map["orgId"] = orgId
                return meApi.getStaffDetail(getHeadersMap(GET, "/lbs/gs/org/getOrgMemberInfo"), map)
            }

            override fun onSuccess(t: String?) {
                val model = GSONUtil.getEntity(t, StaffInfoModel::class.java) ?: return
                if (model.code != 200 || model.data == null) {
                    showFragmentToast(model.msg)
                } else {
                    view.getStaffInfoSuccess(model.data)
                }
            }

            override fun onFail(message: String?) {
                showFragmentToast(message)
            }
        })
    }

    /**
     * 获取用户信息-申请时
     */
    fun getUserInfo(userId: String) {
        onRequestData(object : IRequestListener<String> {
            override fun onCreateObservable(): Observable<*> {
                return meApi.getUserDetail(getHeadersMap(GET, "/lbs/gs/org/getStaffDetail"), userId)
            }

            override fun onSuccess(t: String?) {
                val model = GSONUtil.getEntity(t, StaffInfoModel::class.java) ?: return
                if (model.code != 200 || model.data == null) {
                    showFragmentToast(model.msg)
                } else {
                    view.getStaffInfoSuccess(model.data)
                }
            }

            override fun onFail(message: String?) {
                showFragmentToast(message)
            }
        })

    }

    /**
     * 添加、修改、删除成员
     */
    fun addOrDeleteStaff(body: AddStaffBody) {
        onRequestData(object : IRequestListener<String> {
            override fun onCreateObservable(): Observable<*> {
                return meApi.addStaff(getHeadersMap(POST, "/lbs/gs/org/doAddOrUptStaff"), body)
            }

            override fun onSuccess(t: String?) {
                val code = JSONUtil.get(JSONUtil.toJsonObject(t), "code", "")
                if (code != "200") {
                    val msg = JSONUtil.get(JSONUtil.toJsonObject(t), "msg", "")
                    showFragmentToast(msg)
                } else {
                    view.onAddStaffSuccess()
                }
            }

            override fun onFail(message: String?) {
                showFragmentToast(message)
            }

        })
    }

    /**
     * 同意申请
     */
    fun onAudit(model: AuditMembersBody) {
        onRequestData(object : IRequestListener<String> {
            override fun onCreateObservable(): Observable<*> {
                return meApi.doAuditMembers(getHeadersMap(POST, "/lbs/gs/distributor/doAuditMembers"), model)
            }

            override fun onSuccess(o: String) {
                val resultModel = GSONUtil.getEntity(o, ResultModel::class.java)
                if (Integer.parseInt(resultModel!!.status) == 200) {
                    view.onAudit()
                } else {
                    showFragmentToast(resultModel.msg)
                }
            }

            override fun onFail(message: String) {
                showFragmentToast(message)
            }
        })
    }

    /**
     * 生成密码
     */
    fun getPassWord(password: String) {
        onRequestDataHaveCommon(true, object : IRequestListener<String> {
            override fun onCreateObservable(): Observable<*> {
                val map = HashMap<String, String>()
                map["password"] = password
                return phpCommonApi.getPassword(map)
            }

            override fun onSuccess(t: String) {
                view.getPassWordSuccess(t)
            }

            override fun onFail(message: String?) {
                showFragmentToast(message)
            }

        })
    }

    /**
     * 校验手机号
     */
    fun checkPhone(phone: String) {
        onRequestData(object : IRequestListener<String> {
            override fun onCreateObservable(): Observable<*> {
                return meApi.checkPhone(getHeadersMap(GET, "/lbs/gs/org/doCheckMobile"), phone)
            }

            override fun onSuccess(t: String?) {
                val code = JSONUtil.get(JSONUtil.toJsonObject(t), "code", "")
                val msg = JSONUtil.get(JSONUtil.toJsonObject(t), "msg", "")
                if (code == "203" || code == "205") {
                    view.checkPhoneSuccess(code, msg)
                } else {
                    showFragmentToast(msg)
                }
            }

            override fun onFail(message: String?) {
                showFragmentToast(message)
            }
        })
    }

    /**
     * 验证手机
     */
    fun validateMobile(mobile: String, code: String) {
        onRequestDataHaveCommon(true, object : IRequestListener<Any> {
            override fun onCreateObservable(): Observable<*> {
                val params = HashMap<String, Any>()
                params["mobile"] = mobile
                params["sms_captcha"] = code
                return phpCommonApi.validateMobile(params)
            }

            override fun onSuccess(`object`: Any) {
                view.onValidateMobileCodeSuccess()
            }

            override fun onFail(message: String) {
                showFragmentToast(message)
            }
        })
    }

    /**
     * 获取验证码
     */
    fun onSendCode(mobile: String) {
        onRequestDataHaveCommon(false, object : IRequestListener<Any> {
            override fun onCreateObservable(): Observable<*> {
                val params = java.util.HashMap<String, Any>()
                params["mobile"] = mobile
                return APPresenter.phpCommonApi.securitySMS(params)
            }

            override fun onSuccess(`object`: Any) {
                view.onSendMSMSuccess()
            }

            override fun onFail(message: String) {
                showFragmentToast(message)
            }
        })
    }
}