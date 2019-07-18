package com.lyzb.jbx.dialog

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import com.like.longshaolib.dialog.BaseDialogFragment
import com.like.utilslib.image.LoadImageUtil
import com.lyzb.jbx.R

/**
 *@author wyx
 *@version
 *@role
 *@time 2019 2019/5/30 15:43
 */

class MeGrowDialog : BaseDialogFragment() {

    var imgUrl: String? = ""
    var gif = false

    companion object {

        const val INTENTKEY_IMGURL = "intentkey_imgurl"
        const val INTENTKEY_GIF = "intentkey_gif"

        fun newIntance(imgUrl: String?, gif: Boolean): MeGrowDialog {
            var dialog = MeGrowDialog()
            var bundel = Bundle()
            bundel.putString(INTENTKEY_IMGURL, imgUrl)
            bundel.putBoolean(INTENTKEY_GIF, gif)
            dialog.arguments = bundel
            return dialog
        }
    }

    override fun initView() {
        val iv: ImageView = findViewById(R.id.megrow_iv)
        if (gif) {
            com.szy.yishopcustomer.Util.image.LoadImageUtil.loadImageGif(iv, imgUrl)
        } else {
            LoadImageUtil.loadImage(iv, imgUrl)
        }
        iv.setOnClickListener {
            dismiss()
        }
    }

    override fun initData() {
    }

    override fun getViewWidth(): Int {
        return WindowManager.LayoutParams.WRAP_CONTENT
    }

    override fun getViewHeight(): Int {
        return WindowManager.LayoutParams.WRAP_CONTENT
    }

    override fun getViewGravity(): Int {
        return Gravity.CENTER
    }

    override fun getAnimationType(): Int {
        return BaseDialogFragment.CNTER
    }

    override fun getResId(): Any {
        return R.layout.layout_megrow
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (null != arguments) {
            imgUrl = arguments!!.getString(INTENTKEY_IMGURL)
            gif = arguments!!.getBoolean(INTENTKEY_GIF)
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}