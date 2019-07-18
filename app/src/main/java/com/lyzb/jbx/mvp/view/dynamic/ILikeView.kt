package com.lyzb.jbx.mvp.view.dynamic

import com.lyzb.jbx.model.dynamic.DynamicLikeModel

/**
 * Created by :TYK
 * Date: 2019/3/14  20:18
 * Desc:
 */

interface ILikeView{
    fun onSuccess(isRefresh:Boolean,bean: DynamicLikeModel)
}