package com.lyzb.jbx.mvp.view.me.company

import com.lyzb.jbx.model.me.company.CompanyModel
import com.lyzb.jbx.model.me.company.MyCompanyModel

/**
 *@author wyx
 *@version
 *@role
 *@time 2019 2019/6/18 9:22
 */
interface IMyCompanyView {

    fun onRefresh(bean: MyCompanyModel)

    fun onLoadMore(bean: MyCompanyModel)

    fun onFail(msg: String)
}