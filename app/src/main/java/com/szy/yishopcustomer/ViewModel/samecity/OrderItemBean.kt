package com.szy.yishopcustomer.ViewModel.samecity


data class OrderItemBean(
        val orderProdContent: String,
        val orderProdLogo: String,
        val prodAmount: Double,
        val prodId: String,
        val prodNum: Int,
        val prodPrice: Double,
        val storeId: Int
)