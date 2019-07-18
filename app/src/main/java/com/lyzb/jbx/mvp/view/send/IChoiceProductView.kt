package com.lyzb.jbx.mvp.view.send

import com.lyzb.jbx.model.send.GoodsModel

/**
 * Created by :TYK
 * Date: 2019/5/8  9:53
 * Desc:
 */
interface IChoiceProductView {

    fun defaultSuccess(isRefresh: Boolean, list: MutableList<GoodsModel>)

    fun defaultFail(isRefresh: Boolean)
}