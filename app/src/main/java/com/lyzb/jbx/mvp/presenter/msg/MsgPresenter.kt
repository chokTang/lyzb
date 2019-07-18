package com.lyzb.jbx.mvp.presenter.msg

import com.like.longshaolib.net.helper.RequestSubscriber
import com.like.longshaolib.net.inter.SubscriberOnNextListener
import com.lyzb.jbx.model.msg.MsgBean
import com.lyzb.jbx.mvp.APPresenter
import com.lyzb.jbx.mvp.view.msg.IMsgListView
import com.szy.yishopcustomer.Util.json.GsonUtils

/**
 * Created by :TYK
 * Date: 2019/3/4  16:03
 * Desc:
 */
class MsgPresenter : APPresenter<IMsgListView>() {
    
    /**
     * 获取消息列表数据
     */
    fun getMsgList() {
        val requestSubscriber = RequestSubscriber<String>(context)
        requestSubscriber.bindCallbace(object : SubscriberOnNextListener<String> {
            override fun onNext(t: String?) {
                if (isViewAttached) {
                    view.getMsgList(GsonUtils.toList(t.toString(),MsgBean::class.java)!!)
                }
            }

            override fun onError(e: Throwable?) {

            }

        })
    }

}