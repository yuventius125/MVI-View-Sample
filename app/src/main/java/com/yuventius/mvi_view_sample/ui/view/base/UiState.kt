package com.yuventius.mvi_view_sample.ui.view.base

import androidx.compose.runtime.State
import com.yuventius.mvi_view_sample.ui.view.screen.login.LoginState

sealed class UiState<out T> {
    data object Loading: UiState<Nothing>()
    data class Loaded<T>(val data: T): UiState<T>()
    data object Failed: UiState<Nothing>()
}