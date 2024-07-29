package com.yuventius.mvi_view_sample.ui.view.screen.home

import com.yuventius.domain.model.HistoryEvent

sealed class HomeEvent {
    data object GetRemoteHistories: HomeEvent()
    data class InsertFavorite(val historyEvent: HistoryEvent): HomeEvent()
    data class DeleteFavorite(val historyEvent: HistoryEvent): HomeEvent()
}