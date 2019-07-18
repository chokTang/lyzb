package com.lyzb.jbx.adapter.account

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lyzb.jbx.R
import com.lyzb.jbx.model.account.QueryByShopName

/**
 * Created by :TYK
 * Date: 2019/3/19  20:56
 * Desc: popupwindow  中的 recycleview适配器
 */

class PopupwindowAdapter: BaseQuickAdapter<QueryByShopName.DataListBean, BaseViewHolder>(R.layout.item_pop_shop_name) {
    override fun convert(helper: BaseViewHolder?, item: QueryByShopName.DataListBean?) {
        helper!!.setText(R.id.tv_shop_name,item!!.companyName)
    }
}