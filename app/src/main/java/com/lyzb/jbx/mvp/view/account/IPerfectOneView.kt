package com.lyzb.jbx.mvp.view.account

import com.lyzb.jbx.model.account.BusinessModel
import com.lyzb.jbx.model.account.QueryByShopName
import com.szy.yishopcustomer.Util.OssAuthenResponse

/**
 * Created by :TYK
 * Date: 2019/3/5  16:24
 * Desc: 完善信息1view
 */

interface IPerfectOneView{
    fun OnUploadResult(imgUrl: String?)
    fun OnGetListBusiness(bean: MutableList<BusinessModel>?)
    fun OnGetShopNameList(list:MutableList<QueryByShopName.DataListBean>?)
}