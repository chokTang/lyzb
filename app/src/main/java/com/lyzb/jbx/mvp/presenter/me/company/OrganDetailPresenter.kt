package com.lyzb.jbx.mvp.presenter.me.company

import com.like.longshaolib.base.inter.IRequestListener
import com.like.utilslib.json.JSONUtil
import com.lyzb.jbx.model.me.ResultModel
import com.lyzb.jbx.model.me.SetComdModel
import com.lyzb.jbx.model.me.company.CompanyModel
import com.lyzb.jbx.model.me.company.OrganDetailModel
import com.lyzb.jbx.model.me.company.OrganThreeLvModel
import com.lyzb.jbx.model.me.company.OrganTowLvModel
import com.lyzb.jbx.model.params.DeleteCompanyBody
import com.lyzb.jbx.model.params.RemoveMembersBody
import com.lyzb.jbx.mvp.APPresenter
import com.lyzb.jbx.mvp.view.me.IOrganDetailView
import com.szy.yishopcustomer.Util.json.GSONUtil
import io.reactivex.Observable

/**
 *@author wyx
 *@version
 *@role
 *@time 2019 2019/6/14 15:44
 */
class OrganDetailPresenter : APPresenter<IOrganDetailView>() {
    var pageNum = 1
    /**
     * 获取机构详情
     */
    fun getOrganDetail(refresh: Boolean, companyId: String) {
        if (refresh) {
            pageNum = 1
        } else {
            pageNum++
        }
        onRequestData(object : IRequestListener<String> {
            override fun onCreateObservable(): Observable<*> {
                val map = HashMap<String, Any>()
                map["companyId"] = companyId
                map["pageNum"] = pageNum
                map["pageSize"] = 10
                return meApi.getOrganDetail(getHeadersMap(GET, "/lbs/gs/org/getCompanyOrgDetail"), map)
            }

            override fun onSuccess(t: String?) {
                val b = GSONUtil.getEntity(t, OrganDetailModel::class.java)
                if (b.code != "200" || b.data == null) {
                    view.onFail(b.code)
                    showFragmentToast(b.msg)
                    return
                }
                if (!refresh) {
                    if (b.data.membersVoList == null) {
                        view.onFail(b.code)
                    } else {
                        view.loadMoreStaff(b.data.membersVoList)
                    }
                    return
                }
                val companyModel = CompanyModel()
//                companyModel.isHide = true
                companyModel.id = b.data.id
                companyModel.companyName = b.data.companyName
                companyModel.logoFilePath = b.data.logoFilePath
                if (b.data.disType == "2") {
                    companyModel.industryName = String.format("所属企业：%s", b.data.parentOrgName)
                } else {
                    companyModel.industryName = b.data.industryName
                }
                companyModel.children = b.data.orgList

                getOrganLv2(companyModel)
                getOrganLv3(companyModel)
                setLine(companyModel)

                b.data.companyList = mutableListOf()
                b.data.companyList.add(companyModel)
                if (b.data.authList != null && b.data.authList.size > 0 && b.data.authList[0].childResource != null) {
                    screenAuth(b.data.authList[0].childResource)
                }

                view.onOrganDetail(b)
            }

            override fun onFail(message: String?) {
                view.onFail("")
                showFragmentToast(message)
            }
        })
    }

    /**
     * 遍历企业列表及机构列表，把企业下的二级机构放进下级item里
     */
    private fun getOrganLv2(companyModel: CompanyModel) {
        //遍历机构列表，不是二级机构的放进其他机构列表待用
        val otherOrganList: MutableList<OrganTowLvModel> = mutableListOf()
        for (organModel in companyModel.children) {
            //机构的上级id与企业id相同时就是企业下的二级机构了
            if (organModel.parentId == companyModel.id) {
                companyModel.addSubItem(organModel)
            } else if (organModel.parentId != "0") {
                otherOrganList.add(organModel)
            }
        }
        companyModel.children = otherOrganList
    }

    /**
     * 获取三级机构
     */
    private fun getOrganLv3(companyModel: CompanyModel) {
        //企业下没有机构或没有其他机构就不管了
        if (companyModel.subItems == null || companyModel.children == null) return
        //遍历企业下二级机构
        for (organModel in companyModel.subItems) {
            //在二级机构下再遍历剩余机构，剩余机构的上级id与二级机构相同则为三级机构
            for (otherOrganModel in companyModel.children) {
                if (organModel.id == otherOrganModel.parentId) {
                    organModel.addSubItem(buildOrganModelLv3(otherOrganModel))
                }
            }
        }
        //再循环一次，看看三级机构下有无更多机构
        for (organModel in companyModel.subItems) {
            if (organModel.subItems == null) continue
            for (organModelLv3 in organModel.subItems) {
                canMoreOrgan(organModelLv3, companyModel.children)
            }
        }
    }

    /**
     * 判断有无更多机构
     */
    private fun canMoreOrgan(organModelLv3: OrganThreeLvModel, list: MutableList<OrganTowLvModel>) {
        //继续遍历机构列表
        for (organModel in list) {
            if (organModelLv3.id == organModel.parentId) {
                organModelLv3.isMoreOrgan = true
            }
        }
    }

    /**
     * 生成三级机构实体
     */
    private fun buildOrganModelLv3(organModel: OrganTowLvModel): OrganThreeLvModel {
        val model = OrganThreeLvModel()
        model.id = organModel.id
        model.membersNum = organModel.membersNum
        model.parentId = organModel.parentId
        model.companyName = organModel.companyName
        return model
    }


    /**
     * 控制分割线的隐藏
     */
    private fun setLine(companyModel: CompanyModel) {
        //没有二级机构的企业隐藏箭头
        if (companyModel.subItems == null) {
            companyModel.isHide = true
            companyModel.isNoBranch = true
            return
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

    /**
     * 剔除不展示的按钮
     */
    private fun screenAuth(list: MutableList<OrganDetailModel.DataBean.AuthListBean>) {
        for (i in list.size - 1 downTo 0) {
            val bean = list[i]
            if (bean.enabled == "N" || !bean.class3.contains("android"))
                list.removeAt(i)
        }
    }

    /****
     * 获取 企业申请数量
     */
    fun getCompanyApplyNumber(comdId: String) {
        onRequestData(false, object : IRequestListener<String> {

            override fun onCreateObservable(): Observable<*> {
                val params = java.util.HashMap<String, String>()
                params["queryType"] = "count"
                params["companyId"] = comdId
                return meApi.getApplyJoinAuditList(getHeadersMap(GET, "/lbs/gs/distributor/getApplyJoinAuditList"), params)
            }

            override fun onSuccess(o: String) {
                val reslutObject = JSONUtil.toJsonObject(o)
                val number = JSONUtil.get(reslutObject, "applyCount", 0)
                view.onCompanyApplyNumber(number)
            }

            override fun onFail(message: String) {
                showFragmentToast(message)
            }
        })
    }

    /**
     * 设置选中企业
     *
     * @param model
     */
    fun setComd(model: SetComdModel) {
        onRequestData(object : IRequestListener<String> {
            override fun onCreateObservable(): Observable<*> {
                return meApi.setComd(getHeadersMap(POST, "/lbs/gs/distributor/doSelectCompany"), model)
            }

            override fun onSuccess(o: String) {
                val resultModel = com.like.utilslib.json.GSONUtil.getEntity(o, ResultModel::class.java)
                if ("200" == resultModel!!.status) {
                    view.setDefaultOrganSuccess()
                } else {
                    showFragmentToast(resultModel.msg)
                }
            }

            override fun onFail(message: String) {
                showFragmentToast(message)
            }
        })
    }

    /**
     * 退出企业
     */
    fun exitCompany(model: RemoveMembersBody) {
        onRequestData(false, object : IRequestListener<String> {
            override fun onCreateObservable(): Observable<*> {
                return meApi.doRemoveMembers(getHeadersMap(POST, "/lbs/gs/distributor/doRemoveMembers"), model)
            }

            override fun onSuccess(o: String) {
                val resultModel = GSONUtil.getEntity(o, ResultModel::class.java)
                if (Integer.parseInt(resultModel!!.status) == 200) {
                    view.onExitCompanySuccess()
                } else {
                    showFragmentToast(resultModel.msg)
                }
            }

            override fun onFail(message: String) {
                showFragmentToast(message)
            }
        })
    }

    /**
     * 解散企业
     */
    fun deleteCompany(companyId: String) {
        onRequestData(object : IRequestListener<String> {
            override fun onCreateObservable(): Observable<*> {
                return meApi.deleteCompany(getHeadersMap(POST, "/lbs/gs/org/doDismissDis"), DeleteCompanyBody(companyId))
            }

            override fun onSuccess(t: String?) {
                val code = JSONUtil.get(JSONUtil.toJsonObject(t), "code", "")
                val msg = JSONUtil.get(JSONUtil.toJsonObject(t), "msg", "")
                if (code == "200") {
                    view.onExitCompanySuccess()
                } else {
                    showFragmentToast(msg)
                }
            }

            override fun onFail(message: String?) {
                showFragmentToast(message)
            }
        })
    }
}
