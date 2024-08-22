package com.yuventius.mvi_view_sample.ext

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun LocalDateTime?.toFormattedString(): String? {
    return this?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss"))
}