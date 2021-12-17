package com.bunbeauty.fooddelivery.data.repo.order

import com.bunbeauty.fooddelivery.data.DatabaseFactory.query
import com.bunbeauty.fooddelivery.data.entity.*
import com.bunbeauty.fooddelivery.data.enums.OrderStatus
import com.bunbeauty.fooddelivery.data.model.order.GetCafeOrder
import com.bunbeauty.fooddelivery.data.model.order.GetClientOrder
import com.bunbeauty.fooddelivery.data.model.order.InsertOrder
import com.bunbeauty.fooddelivery.data.table.OrderTable
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.and
import java.util.*

class OrderRepository : IOrderRepository {

    override suspend fun insertOrder(insertOrder: InsertOrder): GetClientOrder = query {
        val orderEntity = OrderEntity.new {
            time = insertOrder.time
            isDelivery = insertOrder.isDelivery
            code = insertOrder.code
            addressDescription = insertOrder.addressDescription
            comment = insertOrder.comment
            deferredTime = insertOrder.deferredTime
            status = insertOrder.status
            cafe = CafeEntity[insertOrder.cafeUuid]
            clientUser = ClientUserEntity[insertOrder.clientUserUuid]
        }
        insertOrder.orderProductList.onEach { insertOrderProduct ->
            val menuProductEntity = MenuProductEntity[insertOrderProduct.menuProductUuid]
            OrderProductEntity.new {
                count = insertOrderProduct.count
                name = menuProductEntity.name
                newPrice = menuProductEntity.newPrice
                oldPrice = menuProductEntity.oldPrice
                utils = menuProductEntity.utils
                nutrition = menuProductEntity.nutrition
                description = menuProductEntity.description
                comboDescription = menuProductEntity.comboDescription
                photoLink = menuProductEntity.photoLink
                barcode = menuProductEntity.barcode
                menuProduct = menuProductEntity
                order = orderEntity
            }
        }

        orderEntity.toClientOrder()
    }

    override suspend fun getOrderListByCafeUuidLimited(cafeUuid: UUID, limitTime: Long): List<GetCafeOrder> = query {
        OrderEntity.find {
            (OrderTable.cafe eq cafeUuid) and
                    (OrderTable.time greater limitTime)
        }.orderBy(OrderTable.time to SortOrder.DESC)
            .map { orderEntity ->
                orderEntity.toCafeOrder()
            }
    }

    override suspend fun getOrderListByCafeUuid(cafeUuid: UUID): List<GetCafeOrder> = query {
        OrderEntity.find {
            OrderTable.cafe eq cafeUuid
        }.orderBy(OrderTable.time to SortOrder.DESC)
            .map { orderEntity ->
                orderEntity.toCafeOrder()
            }
    }

    override suspend fun getOrderListByCompanyUuid(companyUuid: UUID): List<GetCafeOrder> = query {
        CompanyEntity.findById(companyUuid)?.cities?.flatMap { cityEntity ->
            cityEntity.cafes
        }?.flatMap { cafeEntity ->
            cafeEntity.orders
        }?.sortedByDescending { orderEntity ->
            orderEntity.time
        }?.map { orderEntity ->
            orderEntity.toCafeOrder()
        } ?: emptyList()
    }

    override suspend fun getOrderByUuid(orderUuid: UUID): GetClientOrder? {
        return OrderEntity.findById(orderUuid)?.toClientOrder()
    }

    override suspend fun updateOrderStatusByUuid(orderUuid: UUID, status: String): GetCafeOrder? = query {
        val orderEntity =  OrderEntity.findById(orderUuid)
        orderEntity?.status = status
        orderEntity?.toCafeOrder()
    }

    override suspend fun observeActiveOrderList(clientUserUuid: UUID): List<GetClientOrder> = query {
        OrderEntity.find {
            (OrderTable.clientUser eq clientUserUuid) and
                    (OrderTable.status neq OrderStatus.DELIVERED.name) and
                    (OrderTable.status neq OrderStatus.CANCELED.name)
        }.map { orderEntity ->
            orderEntity.toClientOrder()
        }
    }
}