package com.yuventius.domain.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class HistoryEvent(
    val title: String,
    val eventDateUtc: LocalDateTime,
    val eventDateUnix: Long,
    val details: String,
    val links: ArrayList<String>
)