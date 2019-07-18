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
import com.szy.yishopcustomer.Constant.Api
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.GlideUtil
import com.szy.yishopcustomer.Util.Utils
import com.szy.yishopcustomer.Util.json.GsonUtils
import com.szy.yishopcustomer.ViewModel.samecity.NearbyShop
import com.szy.yishopcustomer.ViewModel.samecity.ProdDecBean
import com.szy.yishopcustomer.ViewModel.samecity.Product

class ShopDetailNearShopAdapter(context: Context, layoutResId: Int) : BaseQuickAdapter<NearbyShop, BaseViewHolder>(layoutResId) {
    var context: Context? = null

    init {
        this.context = context
    }

    override fun convert(helper: BaseViewHolder?, item: NearbyShop?) {

        helper!!.setText(R.id.tv_price_group_buy_package, item!!.jibPice.toString())
        helper.setText(R.id.tv_name_group_buy_package, item.shopName)
        helper.setText(R.id.tv_summary_group_buy_package, item.shopDescription)
        //宝箱价
        helper.setText(R.id.tv_deduction_group_buy_package, "宝箱价:¥"+item.minPice.toString())
        if (item.acer == 0.0) {
            helper.getView<TextView>(R.id.tv_shop_detail_qi).visibility = View.GONE
        } else {
            helper.getView<TextView>(R.id.tv_shop_detail_qi).visibility = View.VISIBLE
            helper.setText(R.id.tv_shop_detail_qi, "+" + item.acer + "元宝")
        }

        helper.setText(R.id.tv_sold_group_buy_package, "已售" + item.soldNum.toString())
        val distance = Math.ceil(item.distance)
        when {
            distance > 1000 -> //距离 单位换算
                helper.setText(R.id.tv_distance_group_buy_package, Utils.toDistance(distance) + "km")
            distance == 0.0 ->
                helper.getView<TextView>(R.id.tv_distance_group_buy_package).visibility = View.GONE
            else ->
                helper.setText(R.id.tv_distance_group_buy_package, Utils.toM(distance) + "m")
        }
        if (!TextUtils.isEmpty(item.shopLogo)) {
            Glide.with(this.context!!).load(Utils.setImgNetUrl(Api.API_CITY_HOME_MER_IMG_URL, item.shopLogo)).apply(GlideUtil.OptionsDefaultLogo()).into(helper.getView<ImageView>(R.id.img_group_buy_package)!!)
        }
    }
}