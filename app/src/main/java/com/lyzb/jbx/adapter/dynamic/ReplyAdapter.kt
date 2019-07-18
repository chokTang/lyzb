package com.lyzb.jbx.adapter.dynamic

import android.text.TextUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.like.utilslib.date.DateStyle
import com.like.utilslib.date.DateUtil
import com.like.utilslib.image.LoadImageUtil
import com.lyzb.jbx.R
import com.lyzb.jbx.model.dynamic.DynamicCommentModel
import com.szy.yishopcustomer.Util.App

/**
 * Created by :TYK
 * Date: 2019/3/15  11:18
 * Desc: 动态详情回复适配器
 */

class ReplyAdapter : BaseQuickAdapter<DynamicCommentModel.ListBean.ChiledrenListBean, BaseViewHolder>(R.layout.item_reply_dynamic) {

    override fun convert(helper: BaseViewHolder?, item: DynamicCommentModel.ListBean.ChiledrenListBean?) {
        LoadImageUtil.loadRoundSizeImage(helper!!.getView(R.id.img_avatar), item!!.headimg, 50)
        helper.setText(R.id.tv_name, item.userName)
        helper.setText(R.id.tv_content, item.content)
        helper.setText(R.id.tv_time, DateUtil.StringToString(item.createDate, DateStyle.MM_DD_HH_MM))

        helper.addOnClickListener(R.id.img_item_delete)
        helper.setVisible(R.id.img_item_delete, TextUtils.equals(item!!.userId, App.getInstance().userId))
    }
}