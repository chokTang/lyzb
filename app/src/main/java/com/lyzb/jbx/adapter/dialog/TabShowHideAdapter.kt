package com.lyzb.jbx.adapter.dialog

import android.text.Editable
import android.text.TextWatcher
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.like.utilslib.other.ToastUtil
import com.lyzb.jbx.R
import com.lyzb.jbx.model.me.TabShowHideBean

/**
 * Created by :TYK
 * Date: 2019/5/10  10:33
 * Desc: 个人名片中tab显示隐藏 dialog适配器
 */

class TabShowHideAdapter : BaseQuickAdapter<TabShowHideBean, BaseViewHolder>(R.layout.item_tab_show_hide_dialog) {

    override fun convert(helper: BaseViewHolder?, item: TabShowHideBean?) {
        item.run {
            helper?.setText(R.id.tv_name, this?.funName)
            helper?.getView<CheckBox>(R.id.check_show)?.isChecked = this!!.status == "0"
            helper?.getView<EditText>(R.id.tv_name)?.isEnabled = this!!.status == "1"
            helper?.getView<CheckBox>(R.id.check_show)?.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    helper.getView<EditText>(R.id.tv_name).setTextColor(helper?.itemView.context.resources.getColor(R.color.gray))
                    if (!checkNotAllhide(data)) {//未全部隐藏
                        helper?.getView<EditText>(R.id.tv_name)?.isEnabled = !isChecked
                        if (isChecked) {//隐藏
                            data[helper.adapterPosition].status = "0"
                        } else {//显示
                            data[helper.adapterPosition].status = "1"
                        }
                    } else {//全部隐藏
                        helper?.getView<CheckBox>(R.id.check_show)?.isChecked = false
                        Toast.makeText(mContext, "至少需保留一个栏目", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    helper.getView<EditText>(R.id.tv_name).setTextColor(helper?.itemView.context.resources.getColor(R.color.fontcColor1))
                    helper?.getView<EditText>(R.id.tv_name)?.isEnabled = !isChecked
                    if (isChecked) {//隐藏
                        data[helper.adapterPosition].status = "0"
                    } else {//显示
                        data[helper.adapterPosition].status = "1"
                    }
                }

            }

            helper?.getView<EditText>(R.id.tv_name)?.addTextChangedListener(object : TextWatcher {

                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s?.length!! > 0) {
                        data[helper.adapterPosition].funName = s.toString().trim()
                    } else {
                        data[helper.adapterPosition].funName = ""
                        Toast.makeText(mContext, "名称不能为空", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }

    /**
     * 检查是否全隐藏 不能全部隐藏
     */
    fun checkNotAllhide(list: MutableList<TabShowHideBean>): Boolean {
        var isAllHide = false
        val selectlist: MutableList<TabShowHideBean>? = arrayListOf()
        for (i in 0 until list.size) {
            if (list[i].status == "0") {
                selectlist?.add(list[i])
            }
        }
        isAllHide = selectlist!!.size == list.size - 1
        return isAllHide
    }

}