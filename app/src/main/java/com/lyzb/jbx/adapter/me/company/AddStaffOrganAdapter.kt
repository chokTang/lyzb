package com.lyzb.jbx.adapter.me.company

import android.content.Context
import android.widget.RadioButton
import com.like.longshaolib.adapter.BaseRecyleAdapter
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder
import com.lyzb.jbx.R
import com.lyzb.jbx.model.me.company.ChildrenBean

class AddStaffOrganAdapter(context: Context?, list: MutableList<ChildrenBean>?) : BaseRecyleAdapter<ChildrenBean>(context, R.layout.item_addstaff_organ, list) {

    override fun convert(holder: BaseViewHolder?, item: ChildrenBean?) {
        holder!!.setText(R.id.addstaff_organ_item_organname_tv, item!!.companyName)
        if (item.role == "2") {
            val staff: RadioButton = holder.cdFindViewById(R.id.addstaff_organ_item_staff_rbtn)
            staff.isChecked = true
        } else {
            val admin: RadioButton = holder.cdFindViewById(R.id.addstaff_organ_item_admin_rbtn)
            admin.isChecked = true
        }
        holder.addItemClickListener(R.id.addstaff_organ_item_staff_rbtn)
        holder.addItemClickListener(R.id.addstaff_organ_item_admin_rbtn)
        item.setOrgId(item.id)
    }
}