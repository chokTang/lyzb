package com.lyzb.jbx.dialog

import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import com.like.longshaolib.dialog.BaseDialogFragment
import com.like.utilslib.screen.DensityUtil
import com.like.utilslib.screen.ScreenUtil
import com.lyzb.jbx.R
import com.lyzb.jbx.adapter.dialog.TabShowHideAdapter
import com.lyzb.jbx.model.me.TabShowHideBean
import com.szy.yishopcustomer.Util.DividerGridItemDecoration
import kotlinx.android.synthetic.main.dialog_tab_show_hide.*

/**
 * Created by :TYK
 * Date: 2019/5/10  10:00
 * Desc: 个人名片中的Tab显示隐藏dialog
 */
class TabShowHideDialog : BaseDialogFragment(), View.OnClickListener {

    var adater: TabShowHideAdapter? = null
    var list: MutableList<TabShowHideBean> = arrayListOf()
    var type = false

    companion object {
        fun newInstance(list: MutableList<TabShowHideBean>, type: Boolean): TabShowHideDialog {
            val dialog = TabShowHideDialog()
            dialog.list = list
            dialog.type = type
            return dialog
        }
    }

    override fun getResId(): Any {
        return R.layout.dialog_tab_show_hide
    }

    override fun initView() {
        adater = TabShowHideAdapter()
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rv_tab_show_hide.layoutManager = linearLayoutManager
        rv_tab_show_hide.adapter = adater
        rv_tab_show_hide.addItemDecoration(DividerGridItemDecoration(context, R.drawable.listdivider_white_10))
        adater?.setNewData(list)

        //团购企业账号管理员显示提示语与否
        if (type) {
            tv_hint.visibility = View.VISIBLE
        } else {
            tv_hint.visibility = View.GONE
        }

        tv_cancel.setOnClickListener(this)
        tv_confirm.setOnClickListener(this)
    }

    override fun initData() {
    }

    override fun getViewWidth(): Int {
        return (ScreenUtil.getScreenWidth() - DensityUtil.dpTopx(20f))
    }

    override fun getViewHeight(): Int {
        return LinearLayout.LayoutParams.WRAP_CONTENT
    }

    override fun getViewGravity(): Int {
        return Gravity.CENTER
    }

    override fun getAnimationType(): Int {
        return FORM_RIGHT_TO_RIGHT
    }

    /**
     * 检查是否有空字符串的 有的话就不能点确定
     */
    fun checkHaveNull(list: MutableList<TabShowHideBean>): Boolean {
        var isAllHide = false
        val selectlist: MutableList<TabShowHideBean>? = arrayListOf()
        for (i in 0 until list.size) {
            if (TextUtils.isEmpty(list[i].getFunName())) {
                selectlist?.add(list[i])
            }
        }
        isAllHide = selectlist!!.size > 0
        return isAllHide
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_cancel -> {
                onDailogDismiss()
            }

            R.id.tv_confirm -> {
                if (checkHaveNull(adater!!.data)) {
                    showToast("栏目名称不能为空")
                } else {
                    clickListener?.click(v, adater?.data)
                    onDailogDismiss()
                }


            }
        }
    }


    interface ClickListener {
        fun click(v: View?, list: MutableList<TabShowHideBean>?)
    }

    var clickListener: ClickListener? = null
    /**
     * 初始化回调
     */
    operator fun invoke(clickListener: ClickListener?): TabShowHideDialog {
        this.clickListener = clickListener
        return this
    }

}