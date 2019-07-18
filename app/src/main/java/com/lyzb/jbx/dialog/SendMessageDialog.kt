package com.lyzb.jbx.dialog

import android.content.Context
import android.content.DialogInterface
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import com.like.longshaolib.dialog.BaseDialogFragment
import com.lyzb.jbx.R
import kotlinx.android.synthetic.main.dialog_send_message.*

/**
 * Created by :TYK
 * Date: 2019/3/21  13:58
 * Desc:  发送消息对话框
 */

class SendMessageDialog : BaseDialogFragment(), View.OnClickListener {

    var dissmisList: DialogInterface.OnDismissListener? = null
    var imm: InputMethodManager? = null
    override fun getResId(): Any {
        return R.layout.dialog_send_message
    }

    override fun initView() {

        edt_content.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s!!.isNotEmpty()) {
                    tv_send.isClickable = true
                    tv_send.setTextColor(resources.getColor(R.color.blue))
                } else {
                    tv_send.isClickable = false
                    tv_send.setTextColor(resources.getColor(R.color.gray))
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }


        })


        tv_send.setOnClickListener(this)
    }

    override fun initData() {
        mView.post {
            edt_content.requestFocus()
            edt_content.isFocusable = true
            imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm!!.showSoftInput(edt_content, InputMethodManager.SHOW_FORCED)
        }
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

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tv_send -> {//发送
                clickListener!!.onClick(edt_content.text.toString())
            }
        }
    }


    interface ClickListener {
        fun onClick(s: String)
    }

    var clickListener: ClickListener? = null


    operator fun invoke(clickListener: ClickListener) {
        this.clickListener = clickListener
    }

    fun setOnDismissListener(listener: DialogInterface.OnDismissListener) {
        dissmisList = listener
    }


    override fun onDismiss(dialog: DialogInterface?) {
        dissmisList?.onDismiss(dialog)
    }



}