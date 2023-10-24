package com.bunbeauty.fooddelivery.data.table

import org.jetbrains.exposed.dao.id.UUIDTable

object NonWorkingDayTable : UUIDTable() {

    val timestamp = long("timestamp")
    val cafe = reference("cafe", CafeTable)

}