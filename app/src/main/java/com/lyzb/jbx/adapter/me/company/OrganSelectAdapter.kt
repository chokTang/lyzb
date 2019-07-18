package com.lyzb.jbx.adapter.me.company

import android.content.Context
import com.like.longshaolib.adapter.BaseRecyleAdapter
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder
import com.lyzb.jbx.R
import com.lyzb.jbx.model.me.company.ChildrenBean

/**
 *@author wyx
 *@version
 *@role
 *@time 2019 2019/6/17 15:17
 */
class OrganSelectAdapter(context: Context, list: MutableList<ChildrenBean>?) : BaseRecyleAdapter<ChildrenBean>(context, R.layout.item_organ_select, list) {

    override fun convert(holder: BaseViewHolder?, item: ChildrenBean?) {
        holder!!.setText(R.id.select_organ_item_name_tv, item!!.companyName)
        holder.addItemClickListener(R.id.select_organ_item_delete_iv)
    }
}