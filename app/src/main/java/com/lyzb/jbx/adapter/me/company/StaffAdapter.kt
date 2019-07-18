package com.lyzb.jbx.adapter.me.company

import android.content.Context
import android.text.TextUtils
import com.like.longshaolib.adapter.BaseRecyleAdapter
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder
import com.lyzb.jbx.R
import com.lyzb.jbx.model.me.company.StaffModel

/**
 *@author wyx
 *@version
 *@role 机构主页下的人员列表
 *@time 2019 2019/6/18 16:00
 */
class StaffAdapter(context: Context?, list: MutableList<StaffModel>?) : BaseRecyleAdapter<StaffModel>(context, R.layout.item_staff, list) {

    override fun convert(holder: BaseViewHolder?, item: StaffModel) {
        holder!!.addItemClickListener(R.id.staff_item_edit_iv)
        holder.setText(R.id.staff_item_name_tv, item.gsName)
        holder.setText(R.id.staff_item_position_tv, item.position)
        holder.setRadiusImageUrl(R.id.staff_item_head_iv, item.headimg, 4)
        holder.setVisible(R.id.staff_item_edit_iv, item.isEdit == "y")
        holder.setText(R.id.staff_item_admin_tv, item.roleName)
        holder.setVisible(R.id.staff_item_admin_tv, !TextUtils.isEmpty(item.roleName))

        holder.addItemClickListener(R.id.staff_item_head_iv)
    }

}