package com.szy.yishopcustomer.Adapter.samecity

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ViewModel.samecity.ProdDescAttribute

class ProductDetailAdapter(context: Context, layoutResId: Int) : BaseQuickAdapter<ProdDescAttribute, BaseViewHolder>(layoutResId) {
    var adapter: ProductDetailDecAdapte? = null
    var context: Context? = null
    var group = 0   //第几组
    var decrow = 0
    init {
        this.context = context
    }

    override fun getItemCount(): Int {
        return if (group==0){
            super.getItemCount()
        }else{
            group
        }
    }

    override fun convert(helper: BaseViewHolder?, item: ProdDescAttribute?) {
        if (TextUtils.isEmpty(item!!.descTitle)){//这里是因为有一个主title没数据造成空白难看所以就隐藏
            helper!!.getView<TextView>(R.id.tv_item_product_descTitle).visibility = View.GONE
        }else{
            helper!!.getView<TextView>(R.id.tv_item_product_descTitle).visibility = View.VISIBLE
            helper.setText(R.id.tv_item_product_descTitle, item.descTitle)
        }
        adapter = ProductDetailDecAdapte(R.layout.item_product_detail_dec)
        adapter!!.toprow = decrow
        helper.getView<RecyclerView>(R.id.rv_detail_dec).layoutManager = LinearLayoutManager(context)
        helper.getView<RecyclerView>(R.id.rv_detail_dec).adapter = adapter
        adapter!!.setNewData(item.list)
    }
}