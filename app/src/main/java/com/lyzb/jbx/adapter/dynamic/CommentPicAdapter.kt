package com.lyzb.jbx.adapter.dynamic

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.like.utilslib.image.LoadImageUtil
import com.lyzb.jbx.R
import com.lyzb.jbx.model.dynamic.DynamicCommentModel

/**
 * Created by :TYK
 * Date: 2019/3/21  21:48
 * Desc: 动态详情中的评论中的图片适配器
 */
class CommentPicAdapter: BaseQuickAdapter<DynamicCommentModel.ListBean.FileVoList, BaseViewHolder>(R.layout.item_comment_pic) {
    override fun convert(helper: BaseViewHolder?, item: DynamicCommentModel.ListBean.FileVoList?) {
        LoadImageUtil.loadImage(helper!!.getView(R.id.img_dynamic_grid),item!!.file)
    }
}