package com.szy.yishopcustomer.union.msg.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.util.MultiTypeDelegate
import com.lyzb.jbx.R

/**
 * Created by :TYK
 * Date: 2019/2/28  16:43
 * Desc:共商联盟
 */

class UnionOnlineServiceAdapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_online_service_union_left) {
    companion object {
        const val MSG_LEFT = 1  //头像在左边的消息
        const val MSG_RIGHT = 2  //头像在右边的消息
    }
    init {
        multiTypeDelegate = object : MultiTypeDelegate<String>() {
            override fun getItemType(t: String?): Int {
                return MSG_LEFT
            }
        }
        multiTypeDelegate.registerItemType(MSG_LEFT, R.layout.item_online_service_union_left)
                .registerItemType(MSG_RIGHT, R.layout.item_online_service_union_right)
    }
    override fun convert(helper: BaseViewHolder?, item: String?) {
        when(helper!!.itemViewType){
            MSG_LEFT->{//左边头像消息

            }
            MSG_RIGHT->{//右边头像消息

            }
        }
    }
}