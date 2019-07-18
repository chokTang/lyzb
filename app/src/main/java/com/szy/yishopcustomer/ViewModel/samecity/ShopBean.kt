package com.szy.yishopcustomer.ViewModel.samecity

import java.io.Serializable

data class ShopBean(
        val acerLabel: String,
        val acer: Double,
        val addressDetail: String?,
        val backgroundImg: String?,
        val collect: String?,
        val commentCount: Int,
        val commentList: List<Comment>,
        val distance: Double,
        val evalScore: Double,
        val nearbyShopList: List<NearbyShop>,
        val productCount: Int,
        val productList: List<Product>,
        val serviceTel: String,
        val shopDescription: String,
        val shopId: Int,
        val shopLat: String,
        val shopLng: String,
        val shopLogo: String?,
        val shopName: String,
        val saleSkip: String,
        val takeOutStatus: Boolean,
        val takeOut: Boolean,
        val userShopPower: String?
) : Serializable

data class Comment(
        val contentImg: List<ContentImg>,
        val evalContent: String,
        val evalScore: Double,
        val gmtCreate: String,
        val id: String,
        val replyId: String,
        val shopComment: String,
        val isSale: Int,
        val saleName: String,
        val userId: Int,
        val userNickName: String,
        val userPhoto: String
) : Serializable


data class ContentImg(
        val evalImgUrl: String
) : Serializable

data class NearbyShop(
        val acer: Double,
        val acerLabel: String,
        val distance: Double,
        val minPice: Double,
        val jibPice: Double,
        val prodectName: String,
        val shopDescription: String,
        val shopId: Int,
        val shopLogo: String,
        val shopName: String,
        val soldNum: Int,
        val takeOutStatus: Boolean,
        val userShopPower: String
) : Serializable

data class Product(
        val acer: Int,
        val jcPrice: Double,
        val jibPice: Double,
        val personTypeId: Int,
        val personTypeName: String,
        val prodDesc: String,
        val prodId: String,
        val prodLogo: String,
        val prodName: String,
        val soldNum: Int,
        val storeId: Int
) : Serializable


