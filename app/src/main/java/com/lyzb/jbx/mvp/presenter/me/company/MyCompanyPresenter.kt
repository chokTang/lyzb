package com.lyzb.jbx.mvp.presenter.me.company

import com.like.longshaolib.base.inter.IRequestListener
import com.like.utilslib.json.GSONUtil
import com.lyzb.jbx.model.me.company.CompanyModel
import com.lyzb.jbx.model.me.company.MyCompanyModel
import com.lyzb.jbx.mvp.APPresenter
import com.lyzb.jbx.mvp.view.me.company.IMyCompanyView
import io.reactivex.Observable


/**
 *@author wyx
 *@version
 *@role
 *@time 2019 2019/6/18 9:21
 */
class MyCompanyPresenter : APPresenter<IMyCompanyView>() {

    var pageNum = 1
    val pageSize = 10

    fun getCompanyList(refresh: Boolean) {
        if (refresh) {
            pageNum = 1
        } else {
            pageNum++
        }

        onRequestData(object : IRequestListener<String> {
            override fun onCreateObservable(): Observable<*> {
                val map = HashMap<String, Int>()
                map["pageNum"] = pageNum
                map["pageSize"] = pageSize
                return meApi.getMyCompanyList(getHeadersMap(GET, "/lbs/gs/org/getCompanyOrgList"), map)
            }

            override fun onSuccess(t: String?) {
                val model = GSONUtil.getEntity(t, MyCompanyModel::class.java) ?: return
                if (model.code == "500" || model.data == null) {
                    view.onFail(model.msg)
                    return
                }

                getOrgan(model.data)
                setLine(model.data)
                if (refresh) {
                    view.onRefresh(model)
                } else {
                    view.onLoadMore(model)
                }
            }

            override fun onFail(message: String) {
                view.onFail(message)
            }
        })
    }

    /**
     * 把树形结构装进model
     */
    private fun getOrgan(list: MutableList<CompanyModel>) {
        for (companyModel in list) {
            if (companyModel.children == null) continue
            for (lv2Modle in companyModel.children) {
                companyModel.addSubItem(lv2Modle)
                if (lv2Modle.children == null) continue
                for (lv3Modle in lv2Modle.children) {
                    lv2Modle.addSubItem(lv3Modle)
                    if (lv3Modle.children != null) {
                        lv3Modle.isMoreOrgan = true
                    }
                }
            }
        }
    }

    /**
     * 控制分割线的隐藏
     */
    private fun setLine(list: MutableList<CompanyModel>) {
        for (companyModel in list) {
            //没有二级机构的企业隐藏箭头
            if (companyModel.subItems == null) {
                companyModel.isNoBranch = true
                continue
            }
            val lastLv2Model = companyModel.subItems.last()
            //企业下最后个二级机构的左边竖线隐藏
            lastLv2Model.isLast = true
            //企业下最后个二级机构,下的三级机构全隐藏黑色竖线
            if (lastLv2Model.subItems != null) {
                lastLv2Model.subItems.last().isLast = true
            }
            for (lv2Model in companyModel.subItems) {
                //没有三级机构就隐藏第二根竖线
                if (lv2Model.subItems == null) {
                    lv2Model.isNoBranch = true
                    continue
                }
                //二级机构下,最后个三级机构的,第二条竖线隐藏
                lv2Model.subItems.last().isLast2 = true
            }
        }
    }

}