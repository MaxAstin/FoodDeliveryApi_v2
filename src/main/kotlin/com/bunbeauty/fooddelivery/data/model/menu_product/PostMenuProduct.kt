package com.bunbeauty.fooddelivery.data.model.menu_product

import kotlinx.serialization.Serializable

@Serializable
data class PostMenuProduct(
    val name: String,
    val newPrice: Int,
    val oldPrice: Int?,
    val utils: String?,
    val nutrition: Int?,
    val description: String,
    val comboDescription: String?,
    val photoLink: String,
    val barcode: Int,
    val categoryUuids: List<String>,
    val isVisible: Boolean,
)