package com.yuventius.mvi_view_sample.ui.view.base

sealed class UIState<out T> {
    data object Loading: UIState<Nothing>()
    data class Success<T>(val data: T): UIState<T>()
    data object Error: UIState<Nothing>()
}