package com.lyzb.jbx.mvp.view.me

import com.lyzb.jbx.model.me.company.AccountPrefixModel
import com.lyzb.jbx.model.me.company.StaffInfoModel

/**
 *@author wyx
 *@version
 *@role
 *@time 2019 2019/6/14 14:45
 */
interface IAddStaffView {
    fun getAccountPrefixSuccess(model: AccountPrefixModel.DataBean)

    fun getStaffInfoSuccess(model: StaffInfoModel.DataBean)

    fun getPassWordSuccess(password: String)

    fun onAudit()

    fun checkPhoneSuccess(code: String, content: String)

    fun onSendMSMSuccess()

    fun onValidateMobileCodeSuccess()

    fun onAddStaffSuccess()
}