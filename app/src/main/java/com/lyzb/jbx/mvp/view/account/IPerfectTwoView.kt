package com.lyzb.jbx.mvp.view.account

import com.lyzb.jbx.model.account.BusinessModel
import com.lyzb.jbx.model.account.UpPerfectMessageBean

/**
 * Created by :TYK
 * Date: 2019/3/5  16:24
 * Desc: 完善信息2view
 */

interface IPerfectTwoView{
    fun OnResult(bean: UpPerfectMessageBean)
    fun OnGetListBusiness(bean: MutableList<BusinessModel>)
}