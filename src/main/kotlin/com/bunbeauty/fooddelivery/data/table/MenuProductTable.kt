package com.bunbeauty.fooddelivery.data.table

import org.jetbrains.exposed.dao.id.UUIDTable

object MenuProductTable : UUIDTable() {

    val name = varchar("name", 512)
    val newPrice = integer("newPrice")
    val oldPrice = integer("oldPrice").nullable()
    val utils = varchar("utils", 512).nullable()
    val nutrition = integer("nutrition").nullable()
    val description = varchar("description", 512)
    val comboDescription = varchar("comboDescription", 512).nullable()
    val photoLink = varchar("photoLink", 512)
    val barcode = integer("barcode")
    val company = reference("company", CompanyTable)
    val isVisible = bool("isVisible")

}