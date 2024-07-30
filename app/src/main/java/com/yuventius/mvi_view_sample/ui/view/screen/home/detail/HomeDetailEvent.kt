package com.yuventius.mvi_view_sample.ui.view.screen.home.detail

import com.yuventius.domain.model.HistoryEvent

sealed class HomeDetailEvent {
    data object GetFavorites: HomeDetailEvent()
    data class InsertFavorite(val historyEvent: HistoryEvent): HomeDetailEvent()
    data class DeleteFavorite(val historyEvent: HistoryEvent): HomeDetailEvent()
}