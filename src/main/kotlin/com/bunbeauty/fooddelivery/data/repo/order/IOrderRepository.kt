package com.bunbeauty.fooddelivery.data.repo.order

import com.bunbeauty.fooddelivery.data.model.order.GetCafeOrder
import com.bunbeauty.fooddelivery.data.model.order.GetClientOrder
import com.bunbeauty.fooddelivery.data.model.order.InsertOrder
import java.util.*

interface IOrderRepository {

    suspend fun insertOrder(insertOrder: InsertOrder): GetClientOrder
    suspend fun getOrderListByCafeUuidLimited(cafeUuid: UUID, limitTime: Long): List<GetCafeOrder>
    suspend fun getOrderListByCafeUuid(cafeUuid: UUID): List<GetCafeOrder>
    suspend fun getOrderListByCompanyUuid(companyUuid: UUID): List<GetCafeOrder>
    suspend fun getOrderByUuid(orderUuid: UUID): GetClientOrder?
    suspend fun updateOrderStatusByUuid(orderUuid: UUID, status:String): GetCafeOrder?
    suspend fun observeActiveOrderList(clientUserUuid: UUID): List<GetClientOrder>

}