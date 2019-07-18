package com.lyzb.jbx.adapter.me.company

import android.content.Context
import android.support.v4.content.ContextCompat
import android.widget.TextView
import com.like.longshaolib.adapter.BaseRecyleAdapter
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder
import com.lyzb.jbx.R
import com.lyzb.jbx.model.me.company.ChildrenBean

/**
 *@author wyx
 *@version
 *@role
 *@time 2019 2019/6/17 10:35
 */
class OrganSuperiorAdapter(context: Context?, list: MutableList<ChildrenBean>?) : BaseRecyleAdapter<ChildrenBean>(context, R.layout.item_organ_superior, list) {

    override fun convert(holder: BaseViewHolder?, item: ChildrenBean?) {
        val nameTv = holder!!.cdFindViewById<TextView>(R.id.organ_superior_item_name_tv)
        val hide = holder.adapterPosition >= list.size - 1
        holder.setVisible(R.id.organ_superior_item_iv, !hide)
        nameTv.text = item!!.companyName
        if (item.isSelection_Top) {
            nameTv.setTextColor(ContextCompat.getColor(_context, R.color.fontcColor1))
            nameTv.textSize = 16f
        } else {
            nameTv.setTextColor(ContextCompat.getColor(_context, R.color.fontcColor2))
            nameTv.textSize = 14f
        }
    }
}