package com.lyzb.jbx.mvp.presenter.me.company

import com.like.longshaolib.base.inter.IRequestListener
import com.like.utilslib.json.GSONUtil
import com.lyzb.jbx.model.account.BusinessModel
import com.lyzb.jbx.model.me.ComdDetailModel
import com.lyzb.jbx.model.me.CreateCompanyBody
import com.lyzb.jbx.mvp.APPresenter
import com.lyzb.jbx.mvp.view.me.ICompanyBaseInfoView
import com.szy.yishopcustomer.Constant.Key
import com.szy.yishopcustomer.Util.Oss
import com.szy.yishopcustomer.Util.SharedPreferencesUtils
import io.reactivex.Observable
import java.io.File

/**
 * Created by :TYK
 * Date: 2019/4/20  11:17
 * Desc: 企业基本信息
 */

class CompanyBaseInfoPresenter: APPresenter<ICompanyBaseInfoView>(){
    /***
     * 获取 行业信息 data
     */
    fun getInducList() {
        onRequestData(false, object : IRequestListener<String> {
            override fun onCreateObservable(): Observable<*> {
                return APPresenter.meApi.getIndustryList(getHeadersMap(APPresenter.GET, "/lbs/gs/user/selectGsProfessionList"))
            }

            override fun onSuccess(s: String) {
                val modelList = GSONUtil.getEntityList(s, BusinessModel::class.java)  //行业信息list
                view.getTypeList(modelList)
            }

            override fun onFail(message: String) {
                showFragmentToast(message)
            }
        })
    }

    /**
     * 上传文件到阿里云
     */
    fun upAliyun(urlPath: String) {
        val file = File(urlPath)
        Oss.getInstance().updaLoadImage(context, SharedPreferencesUtils.getParam(context, Key.USER_INFO_TOKEN.value, "") as String,
                file.absolutePath, object : Oss.OssListener {
            override fun onProgress(progress: Int) {

            }

            override fun onSuccess(url: String) {
                view.OnUploadResult(url)
            }

            override fun onFailure() {

            }
        })

    }

    /**
     * 保存企业信息
     */
    fun saveInfo(body: CreateCompanyBody){
        onRequestData(true,object:IRequestListener<String>{
            override fun onCreateObservable(): Observable<*> {
                return meApi.createCompany(getHeadersMap(POST,"/lbs/gs/distributor/addOrUptCompany"),body)
            }

            override fun onSuccess(t: String?) {
                view.saveInfoSuccess()
            }

            override fun onFail(message: String?) {
                showFragmentToast(message)
            }

        })
    }


    /**
     * 查询企业详情
     */
    fun getCompantInfo(companyId: String) {
        onRequestData(object : IRequestListener<String> {

            override fun onCreateObservable(): Observable<*> {
                return meApi.getComdInfo(getHeadersMap(APPresenter.GET, "/lbs/gs/distributor/getCompanyDetail"), companyId)
            }

            override fun onSuccess(o: String) {
                val model = GSONUtil.getEntity(o, ComdDetailModel::class.java)
                if (model != null) {
                    view.queryCompanyDetail(model)
                } else {
                    showFragmentToast("获取公司信息错误")
                }
            }

            override fun onFail(message: String) {
                showFragmentToast(message)
            }
        })
    }
}