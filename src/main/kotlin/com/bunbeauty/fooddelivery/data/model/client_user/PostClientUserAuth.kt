package com.bunbeauty.fooddelivery.data.model.client_user

import kotlinx.serialization.Serializable

@Serializable
class PostClientUserAuth(
    val firebaseUuid: String,
    val phoneNumber: String,
    val companyUuid: String
)
