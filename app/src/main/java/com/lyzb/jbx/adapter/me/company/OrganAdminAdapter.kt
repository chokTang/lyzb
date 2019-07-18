package com.lyzb.jbx.adapter.me.company

import android.content.Context
import android.view.View
import android.widget.CheckBox
import com.like.longshaolib.adapter.BaseRecyleAdapter
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder
import com.lyzb.jbx.R
import com.lyzb.jbx.fragment.me.company.OrganAdminFragment
import com.lyzb.jbx.model.me.company.OrganNenberModel

/**
 *@author wyx
 *@version
 *@role 企业管理员
 *@time 2019 2019/6/15 10:04
 */
class OrganAdminAdapter(context: Context?, list: MutableList<OrganNenberModel.DataBean>?) : BaseRecyleAdapter<OrganNenberModel.DataBean>(context, R.layout.item_staff, list) {
    var type = 0
    override fun convert(holder: BaseViewHolder, item: OrganNenberModel.DataBean) {

        holder.setVisible(R.id.staff_item_edit_iv, false)
        holder.setVisible(R.id.staff_item_admin_tv, false)
        holder.setVisible(R.id.staff_item_roletype_tv, true)
        holder.setText(R.id.staff_item_name_tv, item.gsName)
        holder.setText(R.id.staff_item_position_tv, item.position)
        holder.setRadiusImageUrl(R.id.staff_item_head_iv, item.headimg, 4)
        var roleTypeName = ""
        when (item.roleType) {
            OrganAdminFragment.ROLE_TYPE_CREATOR -> roleTypeName = "创建者"
            OrganAdminFragment.ROLE_TYPE_ADMIN -> roleTypeName = "管理员"
            OrganAdminFragment.ROLE_TYPE_PERSON_IN_CHARGE -> roleTypeName = "负责人"
        }
        holder.setText(R.id.staff_item_roletype_tv, roleTypeName)
        val cbx = holder.cdFindViewById<CheckBox>(R.id.staff_item_cbx)
        if (type == 0) {
            //默认状态不显示
            cbx.visibility = View.GONE
        } else {
            when (item.roleType) {
                //添加、删除时创建者和负责人不展示选中
                OrganAdminFragment.ROLE_TYPE_CREATOR, OrganAdminFragment.ROLE_TYPE_PERSON_IN_CHARGE -> {
                    cbx.visibility = View.INVISIBLE
                }
                OrganAdminFragment.ROLE_TYPE_ADMIN -> {
                    if (type == 1) {
                        //删除时展示
                        cbx.visibility = View.VISIBLE
                    } else {
                        //添加时管理员仅占位
                        cbx.visibility = View.INVISIBLE
                    }
                }
                else -> cbx.visibility = View.VISIBLE
            }
        }
        cbx.isChecked = item.isSelection
    }

}