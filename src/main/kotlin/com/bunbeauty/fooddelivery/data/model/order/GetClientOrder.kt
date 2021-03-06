package com.bunbeauty.fooddelivery.data.model.order

import kotlinx.serialization.Serializable

@Serializable
data class GetClientOrder(
    val uuid: String,
    val code: String,
    val status: String,
    val time: Long,
    val timeZone: String,
    val isDelivery: Boolean,
    val deferredTime: Long?,
    val addressDescription: String,
    val comment: String?,
    val deliveryCost: Int?,
    val clientUserUuid: String,
    val oderProductList: List<GetOrderProduct>,
)