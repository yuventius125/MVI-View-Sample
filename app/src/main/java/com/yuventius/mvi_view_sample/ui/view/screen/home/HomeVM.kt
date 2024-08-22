package com.yuventius.mvi_view_sample.ui.view.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import com.yuventius.domain.model.DataResult
import com.yuventius.domain.model.HistoryEvent
import com.yuventius.domain.use_case.SpaceXUseCase
import com.yuventius.mvi_view_sample.ui.view.base.BaseVM
import com.yuventius.mvi_view_sample.ui.view.base.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeVM @Inject constructor(
    private val spaceXUseCase: SpaceXUseCase
): BaseVM<HomeState>() {
    fun onEvent(event: HomeEvent) {
        viewModelScope.launch {
            when (event) {
                HomeEvent.GetRemoteHistories -> getHistories()
                is HomeEvent.DeleteFavorite -> deleteFavorite(event.historyEvent)
                is HomeEvent.InsertFavorite -> insertFavorite(event.historyEvent)
            }
        }
    }

    private suspend fun getHistories() {
        when (val result = spaceXUseCase.getHistoryEvents(isRemote = true)) {
            is DataResult.Success -> {
                reduce(getData().copy(remoteHistories = result.data.sortedByDescending { it.eventDateUnix }))
                getFavorites()
            }
            is DataResult.Error -> {
                Logger.e(result.e.localizedMessage ?: "UNKNOWN_ERROR")
                reduce(uiState = UIState.Error)
            }
        }
    }

    private suspend fun getFavorites() {
        when (val result = spaceXUseCase.getHistoryEvents()) {
            is DataResult.Success -> {
                reduce(getData().copy(localHistories = result.data))
            }
            is DataResult.Error -> {
                Logger.e(result.e.localizedMessage ?: "UNKNOWN_ERROR")
            }
        }
    }

    private suspend fun insertFavorite(historyEvent: HistoryEvent) {
        when (val result = spaceXUseCase.insertHistoryEvent(historyEvent)) {
            is DataResult.Success -> {
                reduce(getData().copy(localHistories = result.data))
            }
            is DataResult.Error -> {
                Logger.e(result.e.localizedMessage ?: "UNKNOWN_ERROR")
            }
        }
    }

    private suspend fun deleteFavorite(historyEvent: HistoryEvent) {
        when (val result = spaceXUseCase.deleteHistoryEvent(historyEvent)) {
            is DataResult.Success -> {
                reduce(getData().copy(localHistories = result.data))
            }
            is DataResult.Error -> {
                Logger.e(result.e.localizedMessage ?: "UNKNOWN_ERROR")
            }
        }
    }
}