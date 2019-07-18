package com.lyzb.jbx.adapter.account

import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.like.utilslib.image.LoadImageUtil
import com.lyzb.jbx.R
import com.lyzb.jbx.model.me.SearchResultCompanyModel

/**
 * Created by :TYK
 * Date: 2019/4/10  11:45
 * Desc: 搜索企业结果适配器
 */

class SearchCompanyResultAdapter : BaseQuickAdapter<SearchResultCompanyModel, BaseViewHolder>(R.layout.item_search_company_result) {
    override fun convert(helper: BaseViewHolder?, item: SearchResultCompanyModel?) {
        val imgview = helper?.getView<ImageView>(R.id.img_company)
        item?.run {
            helper?.setText(R.id.tv_name, companyName)
            helper?.setText(R.id.tv_create_man, "创建人:$creatorName")
            helper?.setText(R.id.tv_member, "$industryName·$creatorName")
            LoadImageUtil.loadRoundImage(imgview, distributorLogo, 4,R.mipmap.icon_company_defult)
            //申请状态（ 1：申请中； 2：已加入； 其他：未申请 ）
            when (applyState) {
                1 -> {
                    helper?.getView<TextView>(R.id.tv_jion)?.isSelected = false
                    helper?.getView<TextView>(R.id.tv_jion)?.setTextColor(helper.itemView.context.resources.getColor(R.color.fontcColor3))
                    helper?.getView<TextView>(R.id.tv_jion)?.text = "申请中"
                }
                2 -> {
                    helper?.getView<TextView>(R.id.tv_jion)?.isSelected = false
                    helper?.getView<TextView>(R.id.tv_jion)?.setTextColor(helper.itemView.context.resources.getColor(R.color.fontcColor3))
                    helper?.getView<TextView>(R.id.tv_jion)?.text = "已加入"
                }
                else -> {
                    helper?.getView<TextView>(R.id.tv_jion)?.isSelected = true
                    helper?.getView<TextView>(R.id.tv_jion)?.setTextColor(helper.itemView.context.resources.getColor(R.color.white))
                    helper?.getView<TextView>(R.id.tv_jion)?.text = "加入"
                }
            }
        }

        helper?.addOnClickListener(R.id.tv_jion)
    }
}