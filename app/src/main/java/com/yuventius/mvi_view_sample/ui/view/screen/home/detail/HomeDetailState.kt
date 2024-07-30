package com.yuventius.mvi_view_sample.ui.view.screen.home.detail

import com.yuventius.domain.model.HistoryEvent

data class HomeDetailState (
    val historyEvent: HistoryEvent,
    val favorites: List<HistoryEvent> = listOf()
)