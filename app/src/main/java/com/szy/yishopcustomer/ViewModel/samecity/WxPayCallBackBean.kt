package com.szy.yishopcustomer.ViewModel.samecity

import java.io.Serializable

data class WxPayCallBackBean(
        val `data`: Data,
        val isFree: Int,
        val type: Int,
        var orderId: String

):Serializable

data class Data(
    val appid: String,
    val noncestr: String,
    val orderInfo: String,
    val `package`: String,
    val partnerid: String,
    val prepayid: String,
    val sign: String,
    val timestamp: String
): Serializable