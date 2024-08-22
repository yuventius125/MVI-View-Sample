package com.yuventius.mvi_view_sample.ui.view.screen.home

import com.yuventius.domain.model.HistoryEvent
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class HomeState (
    val remoteHistories: List<HistoryEvent> = emptyList(),
    val localHistories: List<HistoryEvent> = emptyList(),
)