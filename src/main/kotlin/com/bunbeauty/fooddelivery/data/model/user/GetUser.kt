package com.bunbeauty.fooddelivery.data.model.user

import com.bunbeauty.fooddelivery.data.model.city.GetCity
import com.bunbeauty.fooddelivery.data.model.company.GetCompany
import kotlinx.serialization.Serializable

@Serializable
data class GetUser(
    val uuid: String,
    val username: String,
    val passwordHash: String,
    val company: GetCity,
    val role: String,
)