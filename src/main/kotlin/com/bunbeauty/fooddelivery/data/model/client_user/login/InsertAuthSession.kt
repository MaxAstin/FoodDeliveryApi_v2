package com.bunbeauty.fooddelivery.data.model.client_user.login

import java.util.*

class InsertAuthSession(
    val phoneNumber: String,
    val time: Long,
    val isConfirmed: Boolean,
    val companyUuid: UUID,
)
