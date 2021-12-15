package com.bunbeauty.food_delivery.data.model.order

import com.bunbeauty.food_delivery.data.model.menu_product.GetMenuProduct
import kotlinx.serialization.Serializable

@Serializable
data class GetOrderProduct(
    val count: Int,
    val name: String,
    val newPrice: Int,
    val oldPrice: Int?,
    val utils: String?,
    val nutrition: Int?,
    val description: String,
    val comboDescription: String?,
    val photoLink: String,
    val barcode: Int,
    val menuProduct: GetMenuProduct,
)
