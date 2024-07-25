package com.yuventius.mvi_view_sample.ui.view.screen.home

import com.yuventius.domain.model.HistoryEvent
import com.yuventius.mvi_view_sample.ui.view.base.Copyable
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class HomeState (
    val remoteHistories: List<HistoryEvent> = emptyList(),
): Copyable<HomeState> {
    override fun copy(): HomeState = Json.decodeFromString(Json.encodeToString(this))
}