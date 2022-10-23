package com.bunbeauty.fooddelivery.data.model.order

import com.bunbeauty.fooddelivery.data.model.client_user.GetCafeClientUser
import kotlinx.serialization.Serializable

@Serializable
data class GetCafeOrderDetails(
    val uuid: String,
    val code: String,
    val status: String,
    val time: Long,
    val timeZone: String,
    val isDelivery: Boolean,
    val deferredTime: Long?,
    val addressDescription: String,
    val comment: String?,
    val clientUser: GetCafeClientUser,
    val cafeUuid: String,
    val deliveryCost: Int?,
    val oderProductList: List<GetOrderProduct>,
    val availableStatusList: List<String>
)