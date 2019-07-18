package com.lyzb.jbx.mvp.view.me

import com.lyzb.jbx.model.me.AccessModel
import com.lyzb.jbx.model.me.FansModel
import com.lyzb.jbx.mvp.view.dynamic.IBaseDynamicView

/**
 * Created by :TYK
 * Date: 2019/4/18  13:58
 * Desc: 个人名片浏览记录
 */
interface ICardAccessRecordView : IBaseDynamicView {
    fun getRecordList(isRefesh:Boolean,total:Int,list: MutableList<AccessModel.ListBean>)
}