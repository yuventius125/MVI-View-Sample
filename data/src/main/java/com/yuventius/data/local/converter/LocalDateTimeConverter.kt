package com.yuventius.data.local.converter

import kotlinx.datetime.LocalDateTime

class LocalDateTimeConverter: BaseConverter<LocalDateTime>() {
    override fun objectFromString(value: String?): LocalDateTime? = try {
        value?.let {
            LocalDateTime.parse(it)
        } ?: run {
            null
        }
    } catch (e: Exception) {
        null
    }
}