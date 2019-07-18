package com.lyzb.jbx.mvp.view.me

import com.lyzb.jbx.model.me.company.OrganDetailModel
import com.lyzb.jbx.model.me.company.StaffModel

/**
 *@author wyx
 *@version
 *@role
 *@time 2019 2019/6/14 15:45
 */
interface IOrganDetailView {

    fun onOrganDetail(b: OrganDetailModel)

    fun loadMoreStaff(list: MutableList<StaffModel>)

    fun onFail(code: String)

    fun onCompanyApplyNumber(number: Int)

    fun setDefaultOrganSuccess()

    fun onExitCompanySuccess()
}