package com.yuventius.data.local.converter

import kotlinx.datetime.LocalDateTime

class LocalDateTimeConverter: BaseConverter<LocalDateTime>() {
    override fun objectFromString(value: String): LocalDateTime? = try {
        LocalDateTime.parse(value)
    } catch (e: Exception) {
        null
    }
}