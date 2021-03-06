package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.data.Constants
import com.bunbeauty.fooddelivery.data.model.client_user.ClientAuthResponse
import com.bunbeauty.fooddelivery.data.model.client_user.GetClientUser
import com.bunbeauty.fooddelivery.data.model.client_user.PatchClientUser
import com.bunbeauty.fooddelivery.data.model.client_user.PostClientUserAuth
import com.bunbeauty.fooddelivery.data.model.client_user.login.GetClientUserLoginSessionUuid
import com.bunbeauty.fooddelivery.data.model.client_user.login.PostClientCode
import com.bunbeauty.fooddelivery.data.model.client_user.login.PostClientCodeRequest
import com.bunbeauty.fooddelivery.routing.extension.*
import com.bunbeauty.fooddelivery.service.client_user.IClientUserService
import com.bunbeauty.fooddelivery.service.ip.IRequestService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.request.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Application.configureClientUserRouting() {

    routing {
        clientLogin()

        authenticate {
            sendCode()
            checkCode()
            getClient()
            patchClientUser()
        }
    }
}

@Deprecated("Old version of login by Firebase")
fun Routing.clientLogin() {

    val clientUserService: IClientUserService by inject()

    post("/client/login") {
        safely {
            val postClientUserAuth: PostClientUserAuth = call.receive()
            val clientAuthResponse = clientUserService.login(postClientUserAuth)
            if (clientAuthResponse == null) {
                call.respondBad("Unable to log in with provided credentials")
            } else {
                call.respondOk(clientAuthResponse)
            }
        }
    }
}

fun Route.sendCode() {

    val clientUserService: IClientUserService by inject()
    val requestService: IRequestService by inject()

    post("/client/code_request") {
        limitRequestNumber(requestService) {
            clientWithBody<PostClientCodeRequest, GetClientUserLoginSessionUuid> { bodyRequest ->
                clientUserService.sendCode(bodyRequest.body)
            }
        }
    }
}

fun Route.checkCode() {

    val clientUserService: IClientUserService by inject()

    post("/client/code") {
        clientWithBody<PostClientCode, ClientAuthResponse> { bodyRequest ->
            clientUserService.checkCode(bodyRequest.body)
        }
    }
}

fun Route.getClient() {

    val clientUserService: IClientUserService by inject()

    get("/client") {
        client { request ->
            val clientUser = clientUserService.getClientUserByUuid(request.jwtUser.uuid)
            if (clientUser == null) {
                call.respondBad("No user with such uuid")
            } else {
                call.respondOk(clientUser)
            }
        }
    }
}

fun Route.patchClientUser() {

    val clientUserService: IClientUserService by inject()

    patch("/client") {
        clientWithBody<PatchClientUser, GetClientUser>(Constants.UUID_PARAMETER) { bodyRequest ->
            val clientUserUuid = bodyRequest.request.parameterMap[Constants.UUID_PARAMETER]!!
            clientUserService.updateClientUserByUuid(clientUserUuid, bodyRequest.body)
        }
    }
}