package com.bunbeauty.fooddelivery.routing.extension

import com.bunbeauty.fooddelivery.routing.model.BodyRequest
import com.bunbeauty.fooddelivery.routing.model.Request
import io.ktor.server.application.*
import io.ktor.util.pipeline.*

suspend inline fun PipelineContext<Unit, ApplicationCall>.admin(block: (Request) -> Unit) {
    checkRights(block) { jwtUser ->
        jwtUser.isManager()
    }
}

suspend inline fun <reified B, reified R> PipelineContext<Unit, ApplicationCall>.adminWithBody(
    errorMessage: String? = null,
    block: (BodyRequest<B>) -> R?,
) {
    admin { request ->
        handleRequestWithBody(request, errorMessage, block)
    }
}

suspend inline fun <reified R> PipelineContext<Unit, ApplicationCall>.adminDelete(
    deleteBlock: (String) -> R?,
) {
    admin {
        delete(deleteBlock)
    }
}