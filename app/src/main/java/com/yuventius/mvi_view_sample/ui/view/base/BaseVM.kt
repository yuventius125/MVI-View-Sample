package com.yuventius.mvi_view_sample.ui.view.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

open class BaseVM<T> (
    uiState: UIState<T> = UIState.Loading
): ViewModel() {
    private val _uiState = MutableStateFlow(uiState)
    val uiState = _uiState.asStateFlow()
    private val _error: MutableStateFlow<Exception?> = MutableStateFlow(null)
    val error = _error.asStateFlow()

    suspend fun reduce(data: T? = null, uiState: UIState<T>? = null, error: Exception? = null) {
        uiState?.let { state ->
            when (state) {
                is UIState.Loading -> _uiState.emit(UIState.Loading)
                is UIState.Success -> {
                    data?.let { data ->
                        _uiState.emit(UIState.Success(data))
                    } ?: run {
                        _error.emit(AppException.NeedStateValueException())
                        _uiState.emit(UIState.Error)
                    }
                }
                is UIState.Error -> {
                    _error.emit(error)
                    _uiState.emit(UIState.Error)
                }
            }
        } ?: run {
            data?.let { data ->
                _uiState.emit(UIState.Success(data))
            } ?: run {
                _error.emit(AppException.NeedStateValueException())
                _uiState.emit(UIState.Error)
            }
        }
    }

    suspend fun getData(): T {
        return (_uiState.value as UIState.Success).data
    }
}