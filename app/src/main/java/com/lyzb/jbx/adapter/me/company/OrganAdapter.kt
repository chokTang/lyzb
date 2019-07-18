package com.lyzb.jbx.adapter.me.company

import android.content.Context
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import com.like.longshaolib.adapter.BaseRecyleAdapter
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder
import com.lyzb.jbx.R
import com.lyzb.jbx.model.me.company.ChildrenBean

/**
 *@author wyx
 *@version
 *@role 选择机构-购机
 *@time 2019 2019/6/17 9:31
 */
class OrganAdapter(context: Context?, list: MutableList<ChildrenBean>?) : BaseRecyleAdapter<ChildrenBean>(context, R.layout.item_organ_choice, list) {

    override fun convert(holder: BaseViewHolder?, item: ChildrenBean?) {
        holder!!.setText(R.id.organ_choice_item_name_tv, item!!.companyName)

        val statusIv: ImageView = holder.cdFindViewById(R.id.organ_choice_item_iv)
        if(item.isSelection){
            statusIv.setImageResource(R.mipmap.ic_organ_selection)
        }else{
            statusIv.setImageResource(R.mipmap.ic_organ_unchecked )
        }
        holder.addItemClickListener(R.id.organ_choice_item_iv)

        val moreIv: ImageView = holder.cdFindViewById(R.id.organ_choice_item_more_iv)
        if (item.isNoBranch) {
            moreIv.visibility = View.GONE
        } else {
            moreIv.visibility = View.VISIBLE
        }
    }
}