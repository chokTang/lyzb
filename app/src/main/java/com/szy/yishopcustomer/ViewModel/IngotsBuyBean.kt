package com.szy.yishopcustomer.ViewModel

/**
 * Created by :TYK
 * Date: 2018/12/1410:19
 * Desc:
 */


data class IngotsBuyBean(
        val list: List<X>
)

data class X(
        val goods_id: String,
        val goods_image: String,
        val goods_name: String,
        val goods_price: Double,
        val max_integral_num: Int,
        val shop_id: Int,
        val shop_name: String
)