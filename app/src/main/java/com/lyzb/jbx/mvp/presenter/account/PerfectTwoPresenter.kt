package com.lyzb.jbx.mvp.presenter.account

import com.like.longshaolib.base.inter.IRequestListener
import com.like.utilslib.json.GSONUtil
import com.like.utilslib.other.LogUtil
import com.lyzb.jbx.model.account.BusinessModel
import com.lyzb.jbx.model.account.RequestPerfectBean
import com.lyzb.jbx.model.account.UpPerfectMessageBean
import com.lyzb.jbx.mvp.APPresenter
import com.lyzb.jbx.mvp.view.account.IPerfectTwoView
import io.reactivex.Observable

/**
 * Created by :TYK
 * Date: 2019/3/5  16:23
 * Desc: 完善信息页面2presenter
 */

class PerfectTwoPresenter: APPresenter<IPerfectTwoView>(){


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

            }

        })
    }

    /**
     * 上传相关信息
     */
    fun uploadMsg(requestPerfectBean: RequestPerfectBean) {

        onRequestData( false,object : IRequestListener<String> {

            override fun onCreateObservable(): Observable<*> {
                return accountApi.onUpdateMessage(getHeadersMap(POST, "/lbs/gs/user/doPerfectInfo"), requestPerfectBean)
            }

            override fun onSuccess(t: String?) {
                LogUtil.loge("完善信息参数为"+t)
                val bean = GSONUtil.getEntity(t, UpPerfectMessageBean::class.java)
                if ("200"==bean.status){
                    view.OnResult(bean)
                }else{
                    showFragmentToast(bean.msg)
                }
            }

            override fun onFail(message: String?) {
                showFragmentToast(message)
            }

        })
    }


}