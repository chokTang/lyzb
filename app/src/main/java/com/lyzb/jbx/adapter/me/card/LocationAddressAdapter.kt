package com.lyzb.jbx.adapter.me.card

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lyzb.jbx.R
import com.lyzb.jbx.model.LocationAddressInfo

/**
 * Created by :TYK
 * Date: 2019/6/19  14:00
 * Desc: 搜索地址适配器
 */
class LocationAddressAdapter: BaseQuickAdapter<LocationAddressInfo, BaseViewHolder>(R.layout.item_address) {
    override fun convert(helper: BaseViewHolder?, item: LocationAddressInfo?) {
        item?.run {
            helper!!.setText(R.id.tv_address_title,title)
            helper.setText(R.id.tv_address_content,text)
            helper.getView<View>(R.id.view_yellow_circle).isSelected = isSelect
        }
    }
}