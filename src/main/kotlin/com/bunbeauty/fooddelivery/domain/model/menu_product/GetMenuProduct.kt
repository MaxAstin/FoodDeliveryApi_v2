package com.bunbeauty.fooddelivery.domain.model.menu_product

import com.bunbeauty.fooddelivery.domain.model.category.GetCategory
import kotlinx.serialization.Serializable

@Serializable
class GetMenuProduct(
    val uuid: String,
    val name: String,
    val newPrice: Int,
    val oldPrice: Int?,
    val utils: String?,
    val nutrition: Int?,
    val description: String,
    val comboDescription: String?,
    val photoLink: String,
    val barcode: Int,
    val isRecommended: Boolean,
    val categories: MutableList<GetCategory>,
    val isVisible: Boolean,
)
