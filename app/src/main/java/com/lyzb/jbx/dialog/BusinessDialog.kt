package com.lyzb.jbx.dialog

import android.content.DialogInterface
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.like.longshaolib.dialog.BaseDialogFragment
import com.like.utilslib.screen.ScreenUtil
import com.lyzb.jbx.R
import com.lyzb.jbx.adapter.account.ChoiceBusinessAdapter
import com.lyzb.jbx.model.account.BusinessModel
import com.szy.yishopcustomer.Util.LogUtils
import kotlinx.android.synthetic.main.dialog_choice_business.*

/**
 * Created by :TYK
 * Date: 2019/3/5  11:14
 * Desc: 选择行业对话框
 */
class BusinessDialog : BaseDialogFragment() {

    var businessList: MutableList<BusinessModel> = arrayListOf()
    var selectlist: MutableList<BusinessModel> = arrayListOf()
    var adapter: ChoiceBusinessAdapter? = null
    var lp: WindowManager.LayoutParams? = null
    var max: Int = 10

    companion object {
        fun newIntance(): BusinessDialog {
            var dialog = BusinessDialog()
            return dialog
        }
    }

    override fun getResId(): Any {
        return R.layout.dialog_choice_business
    }

    /**
     * 设置最大选择数
     */
    fun setMaxSelectd(max: Int): BusinessDialog {
        this.max = max
        return this
    }

    /**
     * 初始化数据
     */
    fun setList(businessList: MutableList<BusinessModel>): BusinessDialog {
        this.businessList = businessList
        return this
    }

    /**
     * 初始化回调
     */
    operator fun invoke(clickListener: ClickListener?): BusinessDialog {
        this.clickListener = clickListener
        return this
    }

    override fun initView() {
        lp = activity!!.window.attributes
        lp!!.alpha = 0.5f
        activity!!.window.attributes = lp
        tv_finish.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (selectlist.size > 0) {
                    clickListener!!.click(v, selectlist)
                    onDailogDismiss()
                } else {
                    Toast.makeText(context, "必须选择一个行业", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun initData() {
        adapter = ChoiceBusinessAdapter()
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rv_choice_business.layoutManager = linearLayoutManager
        rv_choice_business.adapter = adapter
        adapter!!.setNewData(businessList)
        tv_title.text = "行业(" + 0 + "/" + this.max + ")"
        mView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                dismiss()
            }
        })

        selectlist.clear()
        adapter!!.setOnItemClickListener { adapter, view, position ->
            val model = adapter.data[position] as BusinessModel
            if (selectlist.size < this.max) {
                model.isChecked = !model.isChecked
                if (model.isChecked) {//选中
                    selectlist.add(model)
                } else {//未选中
                    selectlist.remove(model)
                }
            } else {//大于等于10  这里只有等于10 的时候可以取消
                if (model.isChecked) {
                    model.isChecked = !model.isChecked
                    selectlist.remove(model)
                } else {
                    Toast.makeText(context, "行业选择最多" + this.max + "个", Toast.LENGTH_SHORT).show()
                }
            }
            tv_title.text = "行业(" + selectlist.size + "/" + this.max + ")"
            LogUtils.e("当前选中的行业" + selectlist.size)
            adapter.notifyItemChanged(position)
        }


    }

    override fun getViewWidth(): Int {
        return ScreenUtil.getScreenWidth() * 4 / 5
    }

    override fun getViewHeight(): Int {
        return -1
    }

    override fun getViewGravity(): Int {
        return Gravity.RIGHT
    }

    override fun getAnimationType(): Int {
        return FORM_RIGHT_TO_RIGHT
    }

    interface ClickListener {
        fun click(v: View?, list: MutableList<BusinessModel>?)
    }

    var clickListener: ClickListener? = null


    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        lp = activity!!.window.attributes
        lp!!.alpha = 1f
        activity!!.window.attributes = lp
    }
}