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

/**
 * Created by :TYK
 * Date: 2018/12/1411:49
 * Desc:附近商家中的产品适配器
 */

class NearShopProductAdapter(layoutResId: Int) : BaseQuickAdapter<HomeShopAndProductBean.NearbyBean.GoodsListBean, BaseViewHolder>(layoutResId) {
    override fun convert(helper: BaseViewHolder?, item: HomeShopAndProductBean.NearbyBean.GoodsListBean?) {
        helper!!.setText(R.id.tv_near_shop_product_name, item!!.goods_name)
        helper.setText(R.id.tv_near_shop_product_price,"¥"+ item.goods_price)
        if (item.max_integral_num.toInt()==0){
            helper.getView<TextView>(R.id.tv_near_shop_product_ingots).visibility = View.GONE
        }else{
            helper.getView<TextView>(R.id.tv_near_shop_product_ingots).visibility = View.VISIBLE
            helper.setText(R.id.tv_near_shop_product_ingots, "+" + item.max_integral_num + "元宝")
        }

        Glide.with(helper.itemView.context).load(Utils.setImgNetUrl(Api.API_CITY_HOME_MER_IMG_URL,item.goods_image)).thumbnail(0.2f).
                apply(GlideUtil.RadioOptions(5)).into(helper.getView<ImageView>(R.id.img_near_shop_product_pic))
    }
}