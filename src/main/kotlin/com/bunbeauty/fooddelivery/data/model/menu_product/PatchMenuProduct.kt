package com.bunbeauty.fooddelivery.data.model.menu_product

import kotlinx.serialization.Serializable

@Serializable
data class PatchMenuProduct(
    val name: String? = null,
    val newPrice: Int? = null,
    val oldPrice: Int? = null,
    val utils: String? = null,
    val nutrition: Int? = null,
    val description: String? = null,
    val comboDescription: String? = null,
    val photoLink: String? = null,
    val barcode: Int? = null,
    val categoryUuids: List<String>? = null,
    val isVisible: Boolean? = null,
)