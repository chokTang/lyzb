package com.szy.yishopcustomer.ViewModel.samecity

import java.io.Serializable

data class AliPayCallBackBean(
        val `data`: AliData,
        val isFree: Int,
        val type: Int,
        var orderId: String

) : Serializable {
    override fun toString(): String {
        return "AliPayCallBackBean(`data`=$`data`, isFree=$isFree, type=$type, orderId='$orderId')"
    }
}

data class AliData(
        val app_id: String,
        val biz_content: String,
        val charset: String,
        val format: String,
        val method: String,
        val notify_url: String,
        val orderInfo: String,
        val sign: String,
        val sign_type: String,
        val timestamp: String,
        val version: String

) : Serializable {
    override fun toString(): String {
        return "AliData(app_id='$app_id', biz_content='$biz_content', charset='$charset', format='$format', method='$method', notify_url='$notify_url', orderInfo='$orderInfo', sign='$sign', sign_type='$sign_type', timestamp='$timestamp', version='$version')"
    }
}



