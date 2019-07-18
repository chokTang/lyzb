package com.lyzb.jbx.mvp.view.me

import com.lyzb.jbx.model.account.BusinessModel
import com.lyzb.jbx.model.me.company.OrganInfoModel

/**
 *@author wyx
 *@version
 *@role
 *@time 2019 2019/6/14 14:14
 */
interface IAddOrganView {

    fun onOrganData(data: OrganInfoModel)

    fun onAddOrganSuccess()

    fun canDeleteOrganSuccess(canDelSt: Boolean)

    fun getInducListSuccess(list: MutableList<BusinessModel>)
}