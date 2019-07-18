package com.lyzb.jbx.mvp.presenter.account

import android.os.Message
import com.like.longshaolib.base.inter.IRequestListener
import com.like.utilslib.json.GSONUtil
import com.like.utilslib.other.LogUtil
import com.luck.picture.lib.entity.LocalMedia
import com.lyzb.jbx.model.account.BusinessModel
import com.lyzb.jbx.model.account.QueryByShopName
import com.lyzb.jbx.mvp.APPresenter
import com.lyzb.jbx.mvp.view.account.IPerfectOneView
import com.szy.yishopcustomer.Constant.Key
import com.szy.yishopcustomer.Util.Oss
import com.szy.yishopcustomer.Util.OssAuthenResponse
import com.szy.yishopcustomer.Util.SharedPreferencesUtils
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.File
import okhttp3.MultipartBody


/**
 * Created by :TYK
 * Date: 2019/3/5  16:23
 * Desc: 完善信息页面1 presenter
 */

class PerfectOnePresenter : APPresenter<IPerfectOneView>() {
    var file: File? = null
    /**
     * 获取行业列表
     */
    fun getListBusiness() {
        onRequestData(object : IRequestListener<String> {
            override fun onCreateObservable(): Observable<*> {
                return accountApi.onGetListBusiness(getHeadersMap(GET, "/lbs/gs/user/selectGsProfessionList"))
            }

            override fun onSuccess(t: String?) {
                val businessModel = GSONUtil.getEntityList(t, BusinessModel::class.java)
                view.OnGetListBusiness(businessModel)
            }

            override fun onFail(message: String?) {
                showFragmentToast(message)
            }

        })
    }


    /**
     * 上传文件到阿里云
     */
    fun upAliyun(urlPath: String) {
        file = File(urlPath)
        Oss.getInstance().updaLoadImage(context, SharedPreferencesUtils.getParam(context, Key.USER_INFO_TOKEN.value, "") as String,
                file!!.absolutePath, object : Oss.OssListener {
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
     * 根据商家名称关键字联想相关商家
     */
    fun getListByShopName(keyName: String) {
        onRequestData(false, object : IRequestListener<String> {
            override fun onCreateObservable(): Observable<*> {
                val map = HashMap<String, Any>()
                map["disName"] = keyName
                return accountApi.queryDistributorByName(getHeadersMap(GET, "/lbs/gs/user/queryDistributorByName"), map)
            }

            override fun onSuccess(t: String?) {
                val bean = GSONUtil.getEntity(t, QueryByShopName::class.java)
                if (bean.status=="200"){
                    val list = bean.dataList
                    view.OnGetShopNameList(list)
                }else{
                    view.OnGetShopNameList(null)
                }

            }

            override fun onFail(message: String?) {
                view.OnGetShopNameList(null)
            }
        })
    }

}