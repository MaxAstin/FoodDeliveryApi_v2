package com.bunbeauty.food_delivery.service.date_time

interface IDateTimeService {

    fun getDateDDMMMMYYYY(millis: Long): String
    fun getWeekPeriod(millis: Long): String
    fun getDateMMMMYYYY(millis: Long): String
}