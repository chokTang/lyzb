package com.szy.yishopcustomer.union.msg.adapter

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lyzb.jbx.R
import com.lyzb.jbx.model.msg.MsgBean
import com.lyzb.jbx.util.ZZDate
import com.szy.yishopcustomer.Util.GlideUtil

/**
 * Created by :TYK
 * Date: 2019/2/28  16:43
 * Desc:共商联盟消息列表适配器
 */

class UnionMsgAdapter : BaseQuickAdapter<MsgBean, BaseViewHolder>(R.layout.item_msg_union) {


    override fun convert(helper: BaseViewHolder?, item: MsgBean?) {
        Glide.with(helper!!.itemView.context).load(item!!.test).apply(GlideUtil.OptionsDefaultAvata()).thumbnail(0.5f).into(helper.getView(R.id.img_msg_avatar)!!)
        helper.setText(R.id.tv_msg_type,"系统通知")
        helper.setText(R.id.tv_msg_content,"消息内容")
        helper.setText(R.id.tv_msg_time,ZZDate.friendly_time("2019-03-06 10:25:11"))
        helper.getView<ImageView>(R.id.img_banned).visibility = View.VISIBLE
    }



}