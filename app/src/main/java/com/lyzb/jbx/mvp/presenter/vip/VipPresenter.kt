package com.lyzb.jbx.mvp.presenter.vip

import com.like.longshaolib.net.helper.RequestSubscriber
import com.like.longshaolib.net.inter.SubscriberOnNextListener
import com.lyzb.jbx.model.vip.VipBean
import com.lyzb.jbx.mvp.APPresenter
import com.lyzb.jbx.mvp.view.vip.IVipView
import com.szy.yishopcustomer.Util.json.GsonUtils

/**
 * Created by :TYK
 * Date: 2019/3/4  16:42
 * Desc:
 */
class VipPresenter: APPresenter<IVipView>(){


    /**
     * 获取vip相关数据
     */
    fun getVipDataList() {
        val requestSubscriber = RequestSubscriber<String>(context)
        requestSubscriber.bindCallbace(object : SubscriberOnNextListener<String> {
            override fun onNext(t: String?) {
                if (isViewAttached) {
                    view.getVipData(GsonUtils.toList(t.toString(), VipBean::class.java)!!)
                }
            }

            override fun onError(e: Throwable?) {

            }

        })
    }


}