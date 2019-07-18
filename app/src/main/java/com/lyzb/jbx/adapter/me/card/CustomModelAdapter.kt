package com.lyzb.jbx.adapter.me.card

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lyzb.jbx.R
import com.lyzb.jbx.model.me.CardModel
import com.lyzb.jbx.model.me.CustomModular

/**
 * Created by :TYK
 * Date: 2019/6/14  15:15
 * Desc: 名片中自定义模块适配器
 */

class CustomModelAdapter : BaseQuickAdapter<CustomModular, BaseViewHolder>(R.layout.item_card_custom_model) {

    var adapter: CardTextImgAdapter? = null
    var layoutManager: LinearLayoutManager? = null

    var isCanEdit = false

    override fun convert(helper: BaseViewHolder?, item: CustomModular?) {
        adapter = CardTextImgAdapter()
        layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        helper!!.getView<RecyclerView>(R.id.rv_custom_content).layoutManager = layoutManager
        helper.getView<RecyclerView>(R.id.rv_custom_content).adapter = adapter
        item.run {
            helper.setText(R.id.tv_name, this!!.modularName)
            adapter!!.isCanEdit = isCanEdit
            adapter!!.setNewData(this.paragraphVoList)
            helper.setGone(R.id.img_see, isCanEdit)
            helper.setGone(R.id.ll_can_see, isCanEdit)
            helper.setGone(R.id.ll_delete, isCanEdit)
            helper.setGone(R.id.ll_add_custom, isCanEdit)
            helper.getView<ImageView>(R.id.img_see).isSelected = showState == 1
            if (showState == 1) {
                helper.getView<TextView>(R.id.tv_name).setTextColor(mContext.resources.getColor(R.color.fontcColor1))
                helper.setGone(R.id.ll_edit_custom, isCanEdit)
                helper.getView<LinearLayout>(R.id.ll_edit_custom).isClickable = true
                helper.getView<TextView>(R.id.tv_text).isClickable = true
                helper.getView<TextView>(R.id.tv_img).isClickable = true
                helper.addOnClickListener(R.id.ll_edit_custom)
                helper.addOnClickListener(R.id.tv_text)
                helper.addOnClickListener(R.id.tv_img)
            } else {
                helper.getView<TextView>(R.id.tv_name).setTextColor(mContext.resources.getColor(R.color.fontcColor3))
                helper.getView<LinearLayout>(R.id.ll_edit_custom).visibility = View.GONE
                helper.getView<LinearLayout>(R.id.ll_edit_custom).isClickable = false
                helper.getView<TextView>(R.id.tv_text).isClickable = false
                helper.getView<TextView>(R.id.tv_img).isClickable = false
            }
            if (this.paragraphVoList.size > 0) {
                helper.setGone(R.id.ll_empty, false)
                helper.setGone(R.id.ll_add_custom, isCanEdit)
            } else {
                helper.setGone(R.id.ll_empty, isCanEdit)
                helper.setGone(R.id.ll_add_custom, false)
            }
        }
        adapter!!.onItemChildClickListener = OnItemChildClickListener { adapter, view, position ->
            clickListener!!.click(view, helper.adapterPosition, position)
        }

        helper.addOnClickListener(R.id.ll_add_custom)
        helper.addOnClickListener(R.id.ll_can_see)
        helper.addOnClickListener(R.id.ll_delete)

    }


    interface ClickListener {
        fun click(v: View?, modePosition: Int?, position: Int?)
    }

    var clickListener: ClickListener? = null

    /**
     * 初始化回调
     */
    operator fun invoke(clickListener: ClickListener?) {
        this.clickListener = clickListener
    }
}