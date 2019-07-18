package com.lyzb.jbx.mvp.view.me

import com.lyzb.jbx.model.account.BusinessModel
import com.lyzb.jbx.model.me.ComdDetailModel

/**
 * Created by :TYK
 * Date: 2019/4/20  11:18
 * Desc:
 */
interface ICompanyBaseInfoView{
    fun getTypeList(list: MutableList<BusinessModel>)

    fun OnUploadResult(imgUrl: String?)

    fun saveInfoSuccess()

    fun queryCompanyDetail(model: ComdDetailModel)
}