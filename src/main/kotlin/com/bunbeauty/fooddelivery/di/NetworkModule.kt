package com.bunbeauty.fooddelivery.di

import com.bunbeauty.fooddelivery.network.NetworkService
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.dsl.module

private const val AUTH_API_EMAIL_KEY = "AUTH_API_EMAIL_KEY"
private const val AUTH_API_KEY = "AUTH_API_KEY"

private val authApiUsername = System.getenv(AUTH_API_EMAIL_KEY)
private val authApiPassword = System.getenv(AUTH_API_KEY)

val networkModule = module(createdAtStart = true) {
    factory {  NetworkService(get()) }
    single {
        HttpClient(OkHttp.create()) {
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                        encodeDefaults = true
                    }
                )
            }

            install(DefaultRequest) {
                host = "gate.smsaero.ru/v2"
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                basicAuth(authApiUsername, authApiPassword)

                url {
                    protocol = URLProtocol.HTTPS
                }
            }
        }
    }
}