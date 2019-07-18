package com.lyzb.jbx.mvp.view.dynamic

import com.lyzb.jbx.model.dynamic.AddLikeOrFollowModel
import com.lyzb.jbx.model.dynamic.DynamicCommentModel

/**
 * Created by :TYK
 * Date: 2019/3/14  20:19
 * Desc:
 */

interface ICommentView {
    fun onSuccess(isRefresh:Boolean,bean: DynamicCommentModel)
    fun addLikeOrFollow(status:Int,bean: AddLikeOrFollowModel,position:Int)
    fun cancleLikeOrFollow(status:Int,bean: AddLikeOrFollowModel,position:Int)
}