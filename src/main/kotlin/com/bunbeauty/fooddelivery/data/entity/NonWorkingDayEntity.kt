package com.bunbeauty.fooddelivery.data.entity

import com.bunbeauty.fooddelivery.data.model.non_working_day.GetNonWorkingDay
import com.bunbeauty.fooddelivery.data.table.NonWorkingDayTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class NonWorkingDayEntity(uuid: EntityID<UUID>) : UUIDEntity(uuid) {

    val uuid: String = uuid.value.toString()
    var timestamp: Long by NonWorkingDayTable.timestamp
    var cafe: CafeEntity by CafeEntity referencedOn NonWorkingDayTable.cafe

    companion object : UUIDEntityClass<NonWorkingDayEntity>(NonWorkingDayTable)

    fun toNonWorkingDay() = GetNonWorkingDay(
        uuid = uuid,
        timestamp = timestamp,
        cafeUuid = cafe.uuid,
    )

}