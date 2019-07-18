package com.lyzb.jbx.adapter.account

import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lyzb.jbx.R
import com.lyzb.jbx.model.account.BusinessModel

/**
 * Created by :TYK
 * Date: 2019/3/11  14:02
 * Desc: 选择行业适配器
 */
class ChoiceBusinessAdapter : BaseQuickAdapter<BusinessModel, BaseViewHolder>(R.layout.item_choise_business) {
    override fun convert(helper: BaseViewHolder?, item: BusinessModel?) {
        helper!!.setText(R.id.tv_business, item!!.name)
        if (item.isChecked) {
            helper.getView<ImageView>(R.id.check_box).visibility = View.VISIBLE
            helper.getView<TextView>(R.id.tv_business).setTextColor(ContextCompat.getColor(mContext, R.color.app_blue))
        } else {
            helper.getView<ImageView>(R.id.check_box).visibility = View.GONE
            helper.getView<TextView>(R.id.tv_business).setTextColor(ContextCompat.getColor(mContext, R.color.fontcColor1))
        }
    }
}