package com.lyzb.jbx.mvp.view.dynamic

import com.lyzb.jbx.model.dynamic.*

/**
 * Created by :TYK
 * Date: 2019/3/14  16:26
 * Desc:
 */

interface IDynamicDetailView{

    fun onRequestSuccess(bean: DynamicDetailModel)
    fun addLikeOrFollow(status:Int,bean: AddLikeOrFollowModel)
    fun cancleLikeOrFollow(status:Int,bean: AddLikeOrFollowModel)
    fun resultFocus(bean: DynamicFocusModel)
    fun addCommentOrReply(position: Int,bean: AddCommentOrReplyModel)

    fun onSuccess(isRefresh:Boolean,bean: DynamicCommentModel)
    fun onFail(isRefresh:Boolean)
    fun addLikeOrFollow(status:Int,bean: AddLikeOrFollowModel,position:Int)
    fun cancleLikeOrFollow(status:Int,bean: AddLikeOrFollowModel,position:Int)
    fun onDeleteResult(position: Int)
}