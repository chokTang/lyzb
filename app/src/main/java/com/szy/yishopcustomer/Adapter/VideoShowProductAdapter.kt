package com.szy.yishopcustomer.Adapter

import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.szy.yishopcustomer.Constant.Api
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.GlideUtil
import com.szy.yishopcustomer.Util.Utils

import com.szy.yishopcustomer.ViewModel.HomeShopAndProductBean

/**
 * Created by :TYK
 * Date: 2019/1/16  20:18
 * Desc:
 */

class VideoShowProductAdapter(layoutResId: Int) : BaseQuickAdapter<HomeShopAndProductBean.LiveBean.GoodsBean, BaseViewHolder>(layoutResId) {
    override fun convert(helper: BaseViewHolder?, item: HomeShopAndProductBean.LiveBean.GoodsBean?) {
        Glide.with(helper!!.itemView.context).load(Utils.setImgNetUrl(Api.API_CITY_HOME_MER_IMG_URL,item!!.goods_image))
                .apply(GlideUtil.RadioOptions(3)).into(helper.getView(R.id.img_product))

        helper.setText(R.id.tv_product_name,item.goods_name)
        if(item.price_fomat.contains("元宝")){
            helper.getView<TextView>(R.id.tv_product_acer).visibility = View.VISIBLE
            if(item.price_fomat.contains("+")){
                val str = item.price_fomat.split("+")
                helper.setText(R.id.tv_product_price,str[0])
                if (str.isNotEmpty()){
                    helper.setText(R.id.tv_product_acer,"+"+str[1])
                }
            }else{
                helper.setText(R.id.tv_product_acer,"+"+item.price_fomat)
            }

        }else{
            helper.getView<TextView>(R.id.tv_product_acer).visibility = View.GONE
        }
        helper.setText(R.id.tv_box_price,item.price_msg)
        helper.addOnClickListener(R.id.tv_now_buy)
    }
}