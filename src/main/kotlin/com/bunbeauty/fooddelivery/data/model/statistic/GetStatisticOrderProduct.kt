package com.bunbeauty.fooddelivery.data.model.statistic

class GetStatisticOrderProduct(
    val uuid: String,
    val count: Int,
    val menuProductUuid: String,
    val name: String,
    val newPrice: Int,
    val photoLink: String,
)