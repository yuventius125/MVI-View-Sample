package com.yuventius.mvi_view_sample.ui.view.screen.home

sealed class HomeEvent {
    data object GetRemoteHistories: HomeEvent()
}