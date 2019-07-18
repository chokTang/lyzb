package com.lyzb.jbx.mvp.view.me

import com.lyzb.jbx.model.account.BusinessModel

/**
 * Created by :TYK
 * Date: 2019/4/19  10:01
 * Desc:  创建企业view
 */
interface ICreateCompanyView {

    fun getTypeList(list: MutableList<BusinessModel>)

    fun OnUploadResult(imgUrl: String?)

    fun createCompanySuccess()
}