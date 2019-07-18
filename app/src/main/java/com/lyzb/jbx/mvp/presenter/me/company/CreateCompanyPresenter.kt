package com.lyzb.jbx.mvp.presenter.me.company

import com.like.longshaolib.base.inter.IRequestListener
import com.like.utilslib.json.GSONUtil
import com.lyzb.jbx.model.account.BusinessModel
import com.lyzb.jbx.model.me.CreateCompanyBody
import com.lyzb.jbx.mvp.APPresenter
import com.lyzb.jbx.mvp.view.me.ICreateCompanyView
import com.szy.yishopcustomer.Constant.Key
import com.szy.yishopcustomer.Util.Oss
import com.szy.yishopcustomer.Util.SharedPreferencesUtils
import io.reactivex.Observable
import java.io.File

/**
 * Created by :TYK
 * Date: 2019/4/19  9:59
 * Desc: 创建企业presenter
 */

class CreateCompanyPresenter : APPresenter<ICreateCompanyView>() {

    /***
     * 获取 行业信息 data
     */
    fun getInducList() {
        onRequestData(true, object : IRequestListener<String> {
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
     * 创建企业
     */
    fun createCompany(body:CreateCompanyBody){
        onRequestData(true,object:IRequestListener<String>{
            override fun onCreateObservable(): Observable<*> {
                return meApi.createCompany(getHeadersMap(POST,"/lbs/gs/distributor/addOrUptCompany"),body)
            }

            override fun onSuccess(t: String?) {
                view.createCompanySuccess()
            }

            override fun onFail(message: String?) {
                showFragmentToast(message)
            }
        })
    }
}