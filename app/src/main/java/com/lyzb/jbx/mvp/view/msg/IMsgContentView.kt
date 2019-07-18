package com.lyzb.jbx.mvp.view.msg

import com.lyzb.jbx.model.msg.MsgBean

/**
 * Created by :TYK
 * Date: 2019/3/4  16:31
 * Desc:
 */
interface IMsgContentView{
    fun getMsgHistory(list: List<MsgBean>)
}