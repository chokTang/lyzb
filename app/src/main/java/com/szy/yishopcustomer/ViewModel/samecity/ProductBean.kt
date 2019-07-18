package com.szy.yishopcustomer.ViewModel.samecity

import java.io.Serializable

data class ProductBean(
    val acer: Int,
    val addPrice: Double,
    val jcPrice: Double,
    val isSale: String,
    val marketPrice: Double,
    val personTypeId: Int,
    val personTypeName: String,
    val prodDesc: String,
    val prodId: String,
    val prodName: String,
    val prodStockNum: Int,
    val productImageVoList: List<ProductImageVoList>,
    val restrictionNum: Int,
    val soldNum: Int,
    val storeId: String,
    val commentCount: Int
):Serializable

data class ProductImageVoList(
    val imgId: String,
    val imgUrl: String
):Serializable