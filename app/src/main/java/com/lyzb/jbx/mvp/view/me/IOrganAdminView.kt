package com.lyzb.jbx.mvp.view.me

import com.lyzb.jbx.model.me.company.OrganNenberModel

/**
 *@author wyx
 *@version
 *@role
 *@time 2019 2019/6/15 9:57
 */
interface IOrganAdminView {

    fun getMenberListSuccess(list: MutableList<OrganNenberModel.DataBean>)

    fun onChangeAdminSuccess()
}