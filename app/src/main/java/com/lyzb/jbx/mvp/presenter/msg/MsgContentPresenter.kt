package com.lyzb.jbx.mvp.presenter.msg

import com.like.longshaolib.net.helper.RequestSubscriber
import com.like.longshaolib.net.inter.SubscriberOnNextListener
import com.lyzb.jbx.model.msg.MsgBean
import com.lyzb.jbx.mvp.APPresenter
import com.lyzb.jbx.mvp.view.msg.IMsgContentView
import com.szy.yishopcustomer.Util.json.GsonUtils

/**
 * Created by :TYK
 * Date: 2019/3/4  16:31
 * Desc:
 */
class MsgContentPresenter : APPresenter<IMsgContentView>() {


    /**
     * 获取历史消息列表数据
     */
    fun getMsgHistoryList() {
        val requestSubscriber = RequestSubscriber<String>(context)
        requestSubscriber.bindCallbace(object : SubscriberOnNextListener<String> {
            override fun onNext(t: String?) {
                if (isViewAttached) {
                    view.getMsgHistory(GsonUtils.toList(t.toString(), MsgBean::class.java)!!)
                }
            }

            override fun onError(e: Throwable?) {

            }

        })
    }


}