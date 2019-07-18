package com.szy.yishopcustomer.Adapter

import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.szy.yishopcustomer.Constant.Api
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.GlideUtil
import com.szy.yishopcustomer.Util.LogUtils
import com.szy.yishopcustomer.Util.Utils
import com.szy.yishopcustomer.ViewModel.HomeShopAndProductBean
import com.szy.yishopcustomer.ViewModel.X

/**
 * Created by :TYK
 * Date: 2018/12/1410:10
 * Desc:首页元宝换购模块适配器
 */

class HomeIngotsBuyAdapter(layoutResId: Int) : BaseQuickAdapter<HomeShopAndProductBean.RepurchaseBean, BaseViewHolder>(layoutResId) {
    override fun convert(helper: BaseViewHolder?, item: HomeShopAndProductBean.RepurchaseBean?) {
        helper!!.setText(R.id.tv_ingots_buy_product_name, item!!.goods_name)
        helper.setText(R.id.tv_ingots_buy_product_price,"¥"+ item.goods_price)
        if (TextUtils.isEmpty(item.max_integral_num)||item.max_integral_num=="0"){
            helper.getView<TextView>(R.id.tv_ingots_buy_product_ingots).visibility= View.GONE
        }else{
            helper.getView<TextView>(R.id.tv_ingots_buy_product_ingots).visibility= View.VISIBLE
            helper.setText(R.id.tv_ingots_buy_product_ingots, "+" + item.max_integral_num + "元宝")
        }
        helper.setText(R.id.tv_ingots_buy_shop_name, item.shop_name)
        helper.addOnClickListener(R.id.tv_ingots_buy_go_shop)
        Glide.with(helper.itemView.context).load(Utils.setImgNetUrl(Api.API_CITY_HOME_MER_IMG_URL,item.goods_image)).thumbnail(0.2f).
                apply(GlideUtil.RadioOptions(5)).
                into(helper.getView<ImageView>(R.id.img_ingots_buy_product_pic))

    }

}