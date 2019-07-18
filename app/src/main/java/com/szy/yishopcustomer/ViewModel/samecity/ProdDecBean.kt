package com.szy.yishopcustomer.ViewModel.samecity

data class ProdDecBean(
        val desc: String,
        val prodDescAttributes: List<ProdDescAttribute>
)

data class ProdDescAttribute(
        val descTitle: String,
        val list: List<X>
)

data class X(
        val descName: String,
        val descPrice: String
)