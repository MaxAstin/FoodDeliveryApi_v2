package com.bunbeauty.fooddelivery.di

import com.bunbeauty.fooddelivery.data.repo.UserRepository
import com.bunbeauty.fooddelivery.service.UserService
import org.koin.dsl.module

val userModule = module(createdAtStart = true) {
    single { UserService(get(), get()) }
    single { UserRepository() }
}