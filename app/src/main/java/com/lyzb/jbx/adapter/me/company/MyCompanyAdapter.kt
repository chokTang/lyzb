package com.lyzb.jbx.adapter.me.company

import android.util.Log
import android.view.View
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.lyzb.jbx.R
import com.lyzb.jbx.model.me.company.CompanyModel
import com.lyzb.jbx.model.me.company.OrganThreeLvModel
import com.lyzb.jbx.model.me.company.OrganTowLvModel
import com.szy.yishopcustomer.Util.image.LoadImageUtil

/**
 *@author wyx
 *@version
 *@role
 *@time 2019 2019/6/13 16:49
 */
class MyCompanyAdapter : BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    companion object {
        const val TYPE_COMPANY = 0
        const val TYPE_TOWLV_ORGAN = 1
        const val TYPE_THREELV_ORGAN = 2
    }

    constructor(data: MutableList<MultiItemEntity>?) : super(data) {
        addItemType(TYPE_COMPANY, R.layout.item_mycompany)
        addItemType(TYPE_TOWLV_ORGAN, R.layout.item_organ_lv_tow)
        addItemType(TYPE_THREELV_ORGAN, R.layout.item_organ_lv_three)
    }

    override fun convert(helper: BaseViewHolder?, item: MultiItemEntity?) {
        when (helper!!.itemViewType) {
            TYPE_COMPANY -> {
                val companyModel = item as CompanyModel
                var text = companyModel.companyName
                if (companyModel.membersNum > 0) {
                    text += String.format("(%d)", companyModel.membersNum)
                }
                helper.setText(R.id.mycompany_item_name_tv, text)
                helper.setText(R.id.mycompany_item_content_tv, companyModel.industryName)
                LoadImageUtil.loadRoundImage(helper.getView(R.id.mycompany_item_head_iv), companyModel.logoFilePath, 4, R.mipmap.icon_company_defult)
                //在机构详情的时候要隐藏企业信息
                helper.setGone(R.id.mycompany_item_content_ll, !companyModel.isHide)
                helper.setGone(R.id.mycompany_item_top_line, !companyModel.isHide)
                //没有下级机构就隐藏箭头
                helper.setGone(R.id.mycompany_item_arrow_line, !companyModel.isNoBranch)
                helper.setGone(R.id.mycompany_item_notarrow_line, companyModel.isNoBranch)
                if (companyModel.isHide) {
                    helper.setGone(R.id.mycompany_item_notarrow_line, false)
                }
            }
            TYPE_TOWLV_ORGAN -> {
                val organModelLv2 = item as OrganTowLvModel
                var text = organModelLv2.companyName
                if (organModelLv2.membersNum > 0) {
                    text += String.format("(%d)", organModelLv2.membersNum)
                }
                helper.setText(R.id.organ_lvtow_name_tv, text)
                helper.setVisible(R.id.organ_tow_line, !organModelLv2.isLast)
                helper.setVisible(R.id.organ_tow_line3, !organModelLv2.isNoBranch)
            }
            TYPE_THREELV_ORGAN -> {
                val organModelLv3 = item as OrganThreeLvModel
                var text = organModelLv3.companyName
                if (organModelLv3.membersNum > 0) {
                    text += String.format("(%d)", organModelLv3.membersNum)
                }
                helper.setText(R.id.organ_lvthree_name_tv, text)
                helper.setVisible(R.id.organ_lvthree_line, !organModelLv3.isLast)
                helper.setVisible(R.id.organ_lvthree_line2, !organModelLv3.isLast2)
                helper.setVisible(R.id.organ_lvthree_look_lower_level_tv, organModelLv3.isMoreOrgan)
            }
        }
    }

}