package com.bunbeauty.fooddelivery.service.user

import at.favre.lib.crypto.bcrypt.BCrypt.MIN_COST
import com.bunbeauty.fooddelivery.auth.IJwtService
import com.bunbeauty.fooddelivery.data.enums.UserRole
import com.bunbeauty.fooddelivery.data.ext.toUuid
import com.bunbeauty.fooddelivery.data.model.user.UserAuthResponse
import com.bunbeauty.fooddelivery.data.model.user.GetUser
import com.bunbeauty.fooddelivery.data.model.user.InsertUser
import com.bunbeauty.fooddelivery.data.model.user.PostUserAuth
import com.bunbeauty.fooddelivery.data.model.user.PostUser
import com.bunbeauty.fooddelivery.data.repo.user.IUserRepository
import com.toxicbakery.bcrypt.Bcrypt
import com.toxicbakery.bcrypt.Bcrypt.verify

class UserService(private val userRepository: IUserRepository, private val jwtService: IJwtService) : IUserService {

    override suspend fun createUser(postUser: PostUser): GetUser {
        val passwordHash = String(Bcrypt.hash(postUser.password, MIN_COST))
        val insertUser = InsertUser(
            username = postUser.username.lowercase(),
            passwordHash = passwordHash,
            role = UserRole.findByRoleName(postUser.role),
            cityUuid = postUser.cityUuid.toUuid()
        )

        return userRepository.insertUser(insertUser)
    }

    override suspend fun login(postUserAuth: PostUserAuth): UserAuthResponse? {
        return userRepository.getUserByUsername(postUserAuth.username.lowercase())?.let { user ->
            if (verify(postUserAuth.password, user.passwordHash.toByteArray())) {
                val token = jwtService.generateToken(user)
                UserAuthResponse(
                    token = token,
                    cityUuid = user.city.uuid,
                    companyUuid = user.company.uuid
                )
            } else {
                null
            }
        }
    }
}