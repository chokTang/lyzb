package com.szy.yishopcustomer.View

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.LogUtils
import com.szy.yishopcustomer.ViewModel.samecity.FoodsSelectTitleBean
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.dialog_select.*
import com.iflytek.cloud.resource.Resource.setTitle
import com.zhy.view.flowlayout.TagFlowLayout


/**
 * Created by :TYK
 * Date: 2019/1/2  15:29
 * Desc: 筛选对话框
 */

class SelectDialog(context: Context?, layoutId: Int) : BaseDialog(context, layoutId), View.OnClickListener {

    var list: MutableList<FoodsSelectTitleBean> = arrayListOf()

    var position1 = -1
    var position2 = -1
    var view1: View? = null
    var view2: View? = null
    var isTakeout = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setWindowMatch()
        setListener()
    }


    fun setListener() {
        tv_reset.setOnClickListener(this)
        tv_finish.setOnClickListener(this)
    }

    fun setData(list: MutableList<FoodsSelectTitleBean>) {
        this.list = list

        //配置几人餐数据
        flowlayout.adapter = object : TagAdapter<FoodsSelectTitleBean>(list) {
            override fun getView(parent: com.zhy.view.flowlayout.FlowLayout?, position: Int, bean: FoodsSelectTitleBean?): View {
                val tv = LayoutInflater.from(parent!!.context).inflate(R.layout.layout_select_tag, flowlayout, false) as TextView
                tv.text = bean!!.name
                return tv
            }
        }
        //配置服务数据
        val serviceString: MutableList<String> = arrayListOf()
        serviceString.add("外卖")
        flowlayout_service.adapter = object : TagAdapter<String>(serviceString) {
            override fun getView(parent: com.zhy.view.flowlayout.FlowLayout?, position: Int, str: String?): View {
                val tv = LayoutInflater.from(parent!!.context).inflate(R.layout.layout_select_tag, flowlayout_service, false) as TextView
                tv.text = str
                return tv
            }
        }
        flowlayout.setMaxSelectCount(1)
        flowlayout_service.setMaxSelectCount(1)
        //设置显示背景 点击效果
        flowlayout.setOnTagClickListener(object : TagFlowLayout.OnTagClickListener {
            override fun onTagClick(view: View?, position: Int, parent: com.zhy.view.flowlayout.FlowLayout?): Boolean {
                view1 = view
                view!!.isSelected = true
                return true
            }
        })
        //设置显示背景 点击效果
        flowlayout_service.setOnTagClickListener(object : TagFlowLayout.OnTagClickListener {
            override fun onTagClick(view: View?, position: Int, parent: com.zhy.view.flowlayout.FlowLayout?): Boolean {
                view2 = view
                position2 = position
                view!!.isSelected = true
                return true
            }
        })

        //设置选择数据回调  套餐几人餐
        flowlayout.setOnSelectListener(TagFlowLayout.OnSelectListener { selectPosSet ->
            val ins = selectPosSet.iterator()
            if (ins.hasNext()) {
                this.position1 = ins.next()
            } else {
                this.position1 = -1
            }
        })

        //设置选择数据回调
        flowlayout_service.setOnSelectListener(TagFlowLayout.OnSelectListener { selectPosSet ->
            isTakeout = if (selectPosSet.size == 0) {
                "0"
            } else {
                "1"
            }
        })

    }

    override fun onClick(p0: View?) {
        when (p0) {
            tv_reset -> {//重置
                flowlayout.adapter.unSelected(position1, view1)
                flowlayout_service.adapter.unSelected(position2, view2)
                flowlayout.adapter.notifyDataChanged()
                flowlayout_service.adapter.notifyDataChanged()
                position1 = -1
                position2 = -1
                isTakeout = "0"
                switch_btn.isChecked = false
            }
            tv_finish -> {//完成
                val acerStatus = if (switch_btn.isChecked) {
                    "1"
                } else {
                    "0"
                }
                if (position1 == -1) {
                    onclickListener!!.onclick(acerStatus, isTakeout, "")
                } else {
                    onclickListener!!.onclick(acerStatus, isTakeout, list[position1].personTypeId.toString())
                }
                dismiss()
            }
        }
    }

    //点击事件接口
    public interface OnclickListener {
        fun onclick(acerStatus: String, takeOutStatus: String, personTypeId: String)
    }

    var onclickListener: OnclickListener? = null

    public fun setClickListener(onclickListener: OnclickListener?) {
        this.onclickListener = onclickListener
    }

}