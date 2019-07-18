package com.lyzb.jbx.dialog

import android.content.DialogInterface
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import com.like.longshaolib.dialog.BaseDialogFragment
import com.lyzb.jbx.R
import kotlinx.android.synthetic.main.dialog_send_home.*

/**
 * Created by :TYK
 * Date: 2019/3/5  11:14
 * Desc:
 */
class SendDialog : BaseDialogFragment(), View.OnClickListener {
    var lp: WindowManager.LayoutParams? = null
    override fun onClick(v: View?) {
        dismiss()
        clickListener!!.click(v)
    }

    override fun getResId(): Any {
        return R.layout.dialog_send_home
    }

    override fun initView() {
        lp = activity!!.window.attributes
        lp!!.alpha = 0.4f
        activity!!.window.attributes = lp

    }

    override fun initData() {

        tv_send_tw.setOnClickListener(this)
        tv_send_ps.setOnClickListener(this)
        tv_send_video.setOnClickListener(this)
        tv_send_active.setOnClickListener(this)
        mView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                dismiss()
            }
        })
    }

    override fun getViewWidth(): Int {
        return -1
    }

    override fun getViewHeight(): Int {
        return -2
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

    operator fun invoke(clickListener: ClickListener?) {
        this.clickListener = clickListener
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        lp!!.alpha = 1f
        activity!!.window.attributes = lp
    }


}