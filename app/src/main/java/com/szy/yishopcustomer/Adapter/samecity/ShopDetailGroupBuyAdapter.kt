package com.szy.yishopcustomer.Adapter.samecity

import android.content.Context
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.GlideUtil
import com.szy.yishopcustomer.Util.json.GsonUtils
import com.szy.yishopcustomer.ViewModel.samecity.ProdDecBean
import com.szy.yishopcustomer.ViewModel.samecity.Product

class ShopDetailGroupBuyAdapter(context: Context, layoutResId: Int) : BaseQuickAdapter<Product, BaseViewHolder>(layoutResId) {
    var context: Context? = null

    init {
        this.context = context
    }

    override fun convert(helper: BaseViewHolder?, item: Product?) {
        helper!!.setText(R.id.tv_price_group_buy_package, item!!.jibPice.toString())
        helper.setText(R.id.tv_name_group_buy_package, item.prodName)

        if (!TextUtils.isEmpty(item.prodDesc)) {
            try {//这里这样写是因为当前字段有时候返回的是一个实体类的json字符串,有时候又是返回直接描述的字符串
                val prodDecBean = GsonUtils.toObj(item.prodDesc, ProdDecBean::class.java)
                if (prodDecBean != null && !TextUtils.isEmpty(prodDecBean.desc)) {
                    helper.setText(R.id.tv_summary_group_buy_package, prodDecBean.desc)
                }
            } catch (e: JsonSyntaxException) {
                if (!TextUtils.isEmpty(item.prodDesc)) {
                    helper.setText(R.id.tv_summary_group_buy_package, item.prodDesc)
                }
            }

        }
        //宝箱价
        helper.setText(R.id.tv_deduction_group_buy_package, "宝箱价:¥" + item.jcPrice.toString())
        if (item.acer == 0) {
            helper.getView<TextView>(R.id.tv_shop_detail_qi).visibility = View.GONE
        } else {
            helper.getView<TextView>(R.id.tv_shop_detail_qi).visibility = View.VISIBLE
            helper.setText(R.id.tv_shop_detail_qi, "+" + item.acer + "元宝")
        }

        helper.setText(R.id.tv_sold_group_buy_package, "已售" + item.soldNum.toString())
        helper.getView<TextView>(R.id.tv_distance_group_buy_package).visibility = View.GONE
        Glide.with(this.context!!).load(item.prodLogo).apply(GlideUtil.OptionsDefaultLogo()).into(helper.getView<ImageView>(R.id.img_group_buy_package)!!)
    }
}