package com.lyzb.jbx.adapter.account

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.util.MultiTypeDelegate
import com.lyzb.jbx.R
import com.lyzb.jbx.model.account.BusinessModel

/**
 * Created by :TYK
 * Date: 2019/3/11  10:03
 * Desc: 完善信息中的行业适配器
 */


class PerfectBusinessAdapter : BaseQuickAdapter<BusinessModel, BaseViewHolder>(R.layout.item_business) {
    companion object {
        const val NULL = 1  //空数据的时候显示黑色的字体且只有一个
        const val NotNull = 2  //飞空数据的时候 显示蓝色字体
    }
    init {
        multiTypeDelegate = object : MultiTypeDelegate<BusinessModel>() {
            override fun getItemType(t: BusinessModel?): Int {
                var  type =  NotNull
                type = if (t!!.name==""){
                    NULL
                }else{
                    NotNull
                }
                return type
            }
        }
        multiTypeDelegate.registerItemType(NULL, R.layout.item_business_null)
                .registerItemType(NotNull, R.layout.item_business)
    }
    override fun convert(helper: BaseViewHolder?, item: BusinessModel?) {
        when(helper!!.itemViewType){
            NULL->{//空数据的时候
                helper.setText(R.id.item_business_null,"")
                helper.addOnClickListener(R.id.item_business_null)
            }
            NotNull->{//非空数据的时候
                helper.setText(R.id.tv_business,item!!.name)
                helper.addOnClickListener(R.id.ll_business)
            }
        }
    }
}