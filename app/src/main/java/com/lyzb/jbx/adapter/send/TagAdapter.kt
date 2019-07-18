package com.lyzb.jbx.adapter.send

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lyzb.jbx.R
import com.lyzb.jbx.model.send.TagModel

/**
 * Created by :TYK
 * Date: 2019/4/18  9:30
 * Desc:  发送图文时候添加标签
 */

class TagAdapter: BaseQuickAdapter<TagModel, BaseViewHolder>(R.layout.layout_send_tag) {
    override fun convert(helper: BaseViewHolder?, item: TagModel?) {
        item.run {
            helper?.setText(R.id.tv_title, this?.name)
        }
    }
}