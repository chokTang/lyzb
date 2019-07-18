package com.szy.yishopcustomer.Adapter.samecity

import android.content.Context
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ViewModel.samecity.X

class ProductDetailDecAdapte(layoutResId: Int) : BaseQuickAdapter<X, BaseViewHolder>(layoutResId) {

    var toprow = 0 //第几组 第几行

    override fun getItemCount(): Int {
        return if (toprow==0){
            super.getItemCount()
        }else{
            toprow
        }
    }
    override fun convert(helper: BaseViewHolder?, item: X?) {
        if(TextUtils.isEmpty(item!!.descName)){
            helper!!.getView<TextView>(R.id.tv_item_product_descName).visibility =View.GONE
        }else{
            helper!!.getView<TextView>(R.id.tv_item_product_descName).visibility =View.VISIBLE
            helper.setText(R.id.tv_item_product_descName, "·"+ item.descName)

        }
        if(TextUtils.isEmpty(item.descPrice)){
            helper.getView<TextView>(R.id.tv_item_product_descPrice).visibility =View.GONE
        }else{
            helper.getView<TextView>(R.id.tv_item_product_descPrice).visibility =View.VISIBLE
            helper.setText(R.id.tv_item_product_descPrice, "¥"+item.descPrice)
        }
    }
}