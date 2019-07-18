package com.lyzb.jbx.dialog

import android.content.DialogInterface
import android.support.v7.widget.GridLayoutManager
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import com.like.longshaolib.dialog.BaseDialogFragment
import com.like.utilslib.other.LogUtil
import com.like.utilslib.screen.ScreenUtil
import com.lyzb.jbx.R
import com.lyzb.jbx.adapter.send.TagAdapter
import com.lyzb.jbx.model.send.TagModel
import kotlinx.android.synthetic.main.dialog_all_tag.*
import kotlinx.android.synthetic.main.fragment_send_tw.*
import kotlinx.android.synthetic.main.layout_xx_title.*

/**
 * Created by :TYK
 * Date: 2019/4/18  10:24
 * Desc: 所有标签dialog
 */
class AllTagDialog : BaseDialogFragment(),View.OnClickListener{


    var lp: WindowManager.LayoutParams? = null
    var tagAdapter: com.lyzb.jbx.adapter.send.TagAdapter? = null
    var taglist: MutableList<TagModel> = arrayListOf()

    companion object {
        fun newIntance(): AllTagDialog {
            var dialog = AllTagDialog()
            return dialog
        }
    }

    override fun getResId(): Any {
        return R.layout.dialog_all_tag
    }

    override fun initView() {
        lp = activity!!.window.attributes
        lp!!.alpha = 0.5f
        activity!!.window.attributes = lp

        tv_center_text.text = "添加标签"
        tagAdapter = TagAdapter()
        val gridLayoutManager = GridLayoutManager(context, 4)
        rv_all_tag.layoutManager = gridLayoutManager
        rv_all_tag.adapter = tagAdapter

        tagAdapter!!.setOnItemClickListener { adapter, view, position ->
            this.clickListener?.click((adapter.data.get(position) as TagModel).name)
            dismiss()
        }

        img_back.setOnClickListener(this)
    }

    override fun initData() {
        for (i in 0 until 15) {
            val tagModel = TagModel()
            tagModel.setName("全部标签$i")
            taglist.add(tagModel)
        }
        tagAdapter?.setNewData(taglist)
    }

    override fun getViewWidth(): Int {
        return -1
    }

    override fun getViewHeight(): Int {
        return ScreenUtil.getScreenHeight() * 4 / 5
    }

    override fun getViewGravity(): Int {
        return Gravity.BOTTOM
    }

    override fun getAnimationType(): Int {
        return FORM_LEFT_TO_RIGHT
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        lp = activity!!.window.attributes
        lp!!.alpha = 1f
        activity!!.window.attributes = lp
    }

    interface ClickListener {
        fun click(string: String?)
    }

    var clickListener: ClickListener? = null

    /**
     * 初始化回调
     */
    operator fun invoke(clickListener: ClickListener?): AllTagDialog {
        this.clickListener = clickListener
        return this
    }


    override fun onClick(v: View?) {
        when(v?.id){
            R.id.img_back->{
                dismiss()
            }
        }
    }
}