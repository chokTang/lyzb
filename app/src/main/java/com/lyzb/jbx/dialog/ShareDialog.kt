package com.lyzb.jbx.dialog

import android.content.DialogInterface
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import com.like.longshaolib.dialog.BaseDialogFragment
import com.lyzb.jbx.R
import kotlinx.android.synthetic.main.dialog_share.*

/**
 * Created by :TYK
 * Date: 2019/5/25  10:43
 * Desc:  分享dialog
 */
class ShareDialog : BaseDialogFragment(), View.OnClickListener {

    companion object {
        fun newInstance(): ShareDialog {
            val dialog = ShareDialog()
            return dialog
        }
    }

    override fun getResId(): Any {
        return R.layout.dialog_share
    }

    override fun initView() {
        tv_share_wx.setOnClickListener(this)
        tv_share_haibao.setOnClickListener(this)
        tv_cancel.setOnClickListener(this)
    }

    override fun initData() {
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
        return FORM_RIGHT_TO_RIGHT
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tv_share_wx,R.id.tv_share_haibao->{
                clickListener?.click(v)
                dismiss()
            }
            R.id.tv_cancel->{
                clickListener?.click(v)
                dismiss()
            }
        }
    }


    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        clickListener?.click(null)
    }


    interface ClickListener {
        fun click(v: View?)
    }

    var clickListener: ClickListener? = null

    operator fun invoke(clickListener: ClickListener?):ShareDialog {
        this.clickListener = clickListener
        return this
    }
}