package com.lyzb.jbx.dialog

import android.content.DialogInterface
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import com.like.longshaolib.dialog.BaseDialogFragment
import com.lyzb.jbx.R
import kotlinx.android.synthetic.main.dialog_map_select.*

/**
 * Created by :TYK
 * Date: 2019/7/1  15:01
 * Desc:  地图选择 dialog
 */
class MapSelectDialog : BaseDialogFragment(), View.OnClickListener {

    var mapList: MutableList<String> = arrayListOf()

    companion object {
        const val BAIDU = "baidu"
        const val GAODE = "gaode"
        const val TENCENT = "tencent"
        fun newIncetance(): MapSelectDialog {
           val dialog = MapSelectDialog()
            return dialog
        }
    }

    fun setList(list: MutableList<String>): MapSelectDialog {
        mapList = list
        return this
    }

    override fun getResId(): Any {
        return R.layout.dialog_map_select
    }

    override fun initView() {
        tv_baidu.visibility = View.GONE
        tv_gaode.visibility = View.GONE
        tv_tencent.visibility = View.GONE
        for (i in 0 until mapList.size) {
            when (mapList[i]) {
                BAIDU -> {
                    tv_baidu.visibility = View.VISIBLE
                }
                GAODE -> {
                    tv_gaode.visibility = View.VISIBLE
                }
                TENCENT -> {
                    tv_tencent.visibility = View.VISIBLE
                }
            }
        }
        tv_baidu.setOnClickListener(this)
        tv_gaode.setOnClickListener(this)
        tv_tencent.setOnClickListener(this)
    }

    override fun initData() {
    }


    override fun onClick(v: View?) {
        clickListener!!.click(v)
        dismiss()
    }

    override fun getViewWidth(): Int {
        return -1
    }

    override fun getViewHeight(): Int {
        return LinearLayout.LayoutParams.WRAP_CONTENT
    }

    override fun getViewGravity(): Int {
        return Gravity.BOTTOM
    }

    override fun getAnimationType(): Int {
        return FORM_BOTTOM_TO_BOTTOM
    }


    interface ClickListener {
        fun click(v: View?)
    }

    var clickListener: ClickListener? = null

    fun setNewClickListener(clickListener: ClickListener?): MapSelectDialog {
        this.clickListener = clickListener
        return this
    }

//
//    var dissmisList: DialogInterface.OnDismissListener? = null
//
//    fun setOnDismissListener(listener: DialogInterface.OnDismissListener) {
//        dissmisList = listener
//    }


    override fun onDismiss(dialog: DialogInterface?) {
//        dissmisList?.onDismiss(dialog)
        if (this.dialog!=null){
            this.dialog.hide()
        }else{
            dismiss()
        }
    }
}