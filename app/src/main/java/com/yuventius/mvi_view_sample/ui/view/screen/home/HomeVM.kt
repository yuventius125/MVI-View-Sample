package com.yuventius.mvi_view_sample.ui.view.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import com.yuventius.domain.model.DataResult
import com.yuventius.domain.use_case.SpaceXUseCase
import com.yuventius.mvi_view_sample.ui.view.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeVM @Inject constructor(
    private val spaceXUseCase: SpaceXUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow<UiState<HomeState>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: HomeEvent) {
        viewModelScope.launch {
            when (event) {
                HomeEvent.GetRemoteHistories -> getHistories()
            }
        }
    }

    private suspend fun getHistories() {
        when (val result = spaceXUseCase.getHistoryEvents(isRemote = true)) {
            is DataResult.Success -> {
                _uiState.emit(UiState.Loaded(HomeState(remoteHistories = result.data.sortedByDescending { it.eventDateUnix })))
            }
            is DataResult.Error -> {
                Logger.e(result.e.localizedMessage ?: "UNKNOWN_ERROR")
                _uiState.emit(UiState.Failed)
            }
        }
    }
}