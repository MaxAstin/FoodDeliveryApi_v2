package com.bunbeauty.fooddelivery.domain.model.order.client.get

import com.bunbeauty.fooddelivery.domain.model.order.GetOrderAddress
import com.bunbeauty.fooddelivery.domain.model.order.GetOrderProduct
import kotlinx.serialization.Serializable

@Serializable
class GetClientOrderV2(
    val uuid: String,
    val code: String,
    val status: String,
    val time: Long,
    val timeZone: String,
    val isDelivery: Boolean,
    val deferredTime: Long?,
    val address: GetOrderAddress,
    val comment: String?,
    val deliveryCost: Int?,
    val oldTotalCost: Int?,
    val newTotalCost: Int,
    val paymentMethod: String?,
    val percentDiscount: Int?,
    val clientUserUuid: String,
    val oderProductList: List<GetOrderProduct>,
)
