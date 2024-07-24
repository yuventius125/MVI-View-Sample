package com.yuventius.mvi_view_sample.ui.view.base

sealed class UiState<out T> {
    data object Loading: UiState<Nothing>()
    data class Loaded<T>(val data: T): UiState<T>()
    data object Failed: UiState<Nothing>()
}