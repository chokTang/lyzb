package com.szy.yishopcustomer.Adapter

import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lyzb.jbx.R
import com.szy.yishopcustomer.Constant.Api


import com.szy.yishopcustomer.Util.GlideUtil
import com.szy.yishopcustomer.Util.Utils
import com.szy.yishopcustomer.View.RatingBar
import com.szy.yishopcustomer.ViewModel.FoodsSelectResultBean

/**
 * Created by :TYK
 * Date: 2019/1/3  16:23
 * Desc:
 */
class FoodsResultAdapter(layoutResId: Int) : BaseQuickAdapter<FoodsSelectResultBean, BaseViewHolder>(layoutResId) {

    var str = ""

    override fun convert(helper: BaseViewHolder?, item: FoodsSelectResultBean?) {
        Glide.with(helper!!.itemView.context).load(Api.API_CITY_HOME_MER_IMG_URL + item!!.shopImage)
                .thumbnail(0.2f).apply(GlideUtil.EmptyOptions()).into(helper.getView(R.id.img_shop_logo))
        helper.setText(R.id.tv_shop_name, item.shopName)
        if (item.distance == "0" || item.distance.isEmpty()) {
            helper.getView<TextView>(R.id.tv_distance).visibility = View.GONE
        } else {
            helper.getView<TextView>(R.id.tv_distance).visibility = View.VISIBLE
            if (item.distance.toDouble() >= 1000) {
                helper.setText(R.id.tv_distance, Utils.toDistance(item.distance.toDouble()) + "km")
            } else {
                helper.setText(R.id.tv_distance, String.format("%.2f", item.distance.toDouble()) + "m")
            }
        }

        //评分
        if (item.evalScore == "0") {
            helper.getView<RatingBar>(R.id.ratingBar_shop).visibility = View.GONE
        } else {
            helper.getView<RatingBar>(R.id.ratingBar_shop).visibility = View.VISIBLE
            helper.getView<RatingBar>(R.id.ratingBar_shop).star = Math.ceil(item.evalScore.toDouble()).toInt()
        }

        //核销 几人套餐行
        if (null == item.personTypeList || item.personTypeList.size == 0 || item.personTypeList.isEmpty()) {
            helper.getView<TextView>(R.id.tv_package).visibility = View.GONE
            helper.getView<TextView>(R.id.tv_isShow_tag_hexiao).visibility = View.GONE
        } else {
            helper.getView<TextView>(R.id.tv_package).visibility = View.VISIBLE
            helper.getView<TextView>(R.id.tv_isShow_tag_hexiao).visibility = View.VISIBLE
            for (i in 0 until item.personTypeList.size) {
                str = str + item.personTypeList[i].name + "餐" + item.personTypeList[i].jcPrice + "元,"
            }
            helper.setText(R.id.tv_package, str)
        }
        //元宝 最高抵扣
        if (item.acer == "0") {
            helper.getView<TextView>(R.id.tv_acer).visibility = View.GONE
            helper.getView<TextView>(R.id.tv_isShow_tag).visibility = View.GONE
        } else {
            helper.getView<TextView>(R.id.tv_acer).visibility = View.VISIBLE
            helper.getView<TextView>(R.id.tv_isShow_tag).visibility = View.VISIBLE
            helper.setText(R.id.tv_acer, "元宝最高抵扣¥" + item.acer)
        }
        helper.getView<TextView>(R.id.tv_isShow_tag_ticket).visibility = View.GONE
        helper.getView<TextView>(R.id.tv_ticket).visibility = View.GONE


    }
}