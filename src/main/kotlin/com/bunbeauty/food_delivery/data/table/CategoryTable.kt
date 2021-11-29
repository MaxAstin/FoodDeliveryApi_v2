package com.bunbeauty.food_delivery.data.table

import org.jetbrains.exposed.dao.id.UUIDTable

object CategoryTable : UUIDTable() {

    val name = varchar("name", 512)
    val company = reference("company", CompanyTable)
}