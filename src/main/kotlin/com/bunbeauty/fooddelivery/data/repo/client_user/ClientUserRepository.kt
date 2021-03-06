package com.bunbeauty.fooddelivery.data.repo.client_user

import com.bunbeauty.fooddelivery.data.DatabaseFactory.query
import com.bunbeauty.fooddelivery.data.entity.ClientUserEntity
import com.bunbeauty.fooddelivery.data.entity.ClientUserLoginSessionEntity
import com.bunbeauty.fooddelivery.data.entity.CompanyEntity
import com.bunbeauty.fooddelivery.data.model.client_user.GetClientUser
import com.bunbeauty.fooddelivery.data.model.client_user.InsertClientUser
import com.bunbeauty.fooddelivery.data.model.client_user.login.GetClientUserLoginSessionUuid
import com.bunbeauty.fooddelivery.data.model.client_user.login.GetClientUserLoginSession
import com.bunbeauty.fooddelivery.data.model.client_user.login.InsertClientUserLoginSession
import com.bunbeauty.fooddelivery.data.table.ClientUserTable
import java.util.*

class ClientUserRepository : IClientUserRepository {

    override suspend fun insertClientUserLoginSession(insertClientUserLoginSession: InsertClientUserLoginSession): GetClientUserLoginSessionUuid =
        query {
            ClientUserLoginSessionEntity.new {
                phoneNumber = insertClientUserLoginSession.phoneNumber
                time = insertClientUserLoginSession.time
                code = insertClientUserLoginSession.code
            }.toClientUserLoginSession()
        }

    override suspend fun getClientUserLoginSessionByUuid(uuid: UUID): GetClientUserLoginSession? = query {
        ClientUserLoginSessionEntity.findById(uuid)?.toClientUserLoginSessionWithCode()
    }

    override suspend fun getClientUserByPhoneNumber(phoneNumber: String): GetClientUser? = query {
        ClientUserEntity.find {
            ClientUserTable.phoneNumber eq phoneNumber
        }.singleOrNull()?.toClientUser()
    }

    override suspend fun getClientUserByUuid(uuid: UUID): GetClientUser? = query {
        ClientUserEntity.findById(uuid)?.toClientUser()
    }

    override suspend fun insertClientUser(insertClientUser: InsertClientUser): GetClientUser = query {
        ClientUserEntity.new {
            phoneNumber = insertClientUser.phoneNumber
            email = insertClientUser.email
            company = CompanyEntity[insertClientUser.companyUuid]
        }.toClientUser()
    }

    override suspend fun updateClientUserByUuid(uuid: UUID, email: String?): GetClientUser? = query {
        ClientUserEntity.findById(uuid)?.apply {
            this.email = email
        }?.toClientUser()
    }
}