package com.szy.yishopcustomer.View

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import com.lyzb.jbx.R;


class MenuPop(context: Context?) : PopupWindow(context), View.OnClickListener {
    override fun onClick(p0: View?) {
        onclickListener!!.click(p0!!)
    }

    var popview: View? = null
    val context: Context? = null
    var share: TextView? = null
    var home: TextView? = null

    init {
        popview = LayoutInflater.from(context).inflate(R.layout.pop_shop_detail_menu, null)
        contentView = popview
        //点击空白区域PopupWindow消失，这里必须先设置setBackgroundDrawable，否则点击无反应
        setBackgroundDrawable(ColorDrawable(0x00000000))
        isOutsideTouchable = true

        share = popview!!.findViewById(R.id.tv_menu_share)
        home = popview!!.findViewById(R.id.tv_menu_home)

        share!!.setOnClickListener(this)
        home!!.setOnClickListener(this)
    }


    interface OnclickListener {
        fun click(view: View)
    }

    var onclickListener: OnclickListener? = null


    operator fun invoke(onclickListener: OnclickListener) {
        this.onclickListener = onclickListener
    }
}