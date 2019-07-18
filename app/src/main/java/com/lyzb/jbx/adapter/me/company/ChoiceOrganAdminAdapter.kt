package com.lyzb.jbx.adapter.me.company

import android.content.Context
import android.view.View
import android.widget.CheckBox
import com.like.longshaolib.adapter.BaseRecyleAdapter
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder
import com.lyzb.jbx.R
import com.lyzb.jbx.model.me.company.OrganNenberModel

class ChoiceOrganAdminAdapter(context: Context?, list: MutableList<OrganNenberModel.DataBean>?) : BaseRecyleAdapter<OrganNenberModel.DataBean>(context, R.layout.item_staff, list) {

    override fun convert(holder: BaseViewHolder, item: OrganNenberModel.DataBean) {
        holder.setVisible(R.id.staff_item_edit_iv, false)
        holder.setVisible(R.id.staff_item_admin_tv, false)
        holder.setVisible(R.id.staff_item_roletype_tv, false)
        holder.setText(R.id.staff_item_name_tv, item.gsName)
        holder.setText(R.id.staff_item_position_tv, item.position)
        holder.setRadiusImageUrl(R.id.staff_item_head_iv, item.headimg, 4)
        val cbx = holder.cdFindViewById<CheckBox>(R.id.staff_item_cbx)
        cbx.visibility = View.VISIBLE
        cbx.isChecked = item.isSelection
    }
}