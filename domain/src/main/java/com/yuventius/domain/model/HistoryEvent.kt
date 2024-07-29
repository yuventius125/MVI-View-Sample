package com.yuventius.domain.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class HistoryEvent(
    val id: String,
    val title: String,
    val eventDateUtc: String,
    val eventDateUnix: Long,
    val details: String,
    val links: ArrayList<String?>
)