package com.yuventius.mvi_view_sample.ui.view.screen.home.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import com.yuventius.domain.model.DataResult
import com.yuventius.domain.model.HistoryEvent
import com.yuventius.domain.use_case.SpaceXUseCase
import com.yuventius.mvi_view_sample.ui.view.base.UiState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel(assistedFactory = HomeDetailVM.HomeDetailVMFactory::class)
class HomeDetailVM @AssistedInject constructor(
    @Assisted private val historyEvent: HistoryEvent,
    private val spaceXUseCase: SpaceXUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow<UiState<HomeDetailState>>(UiState.Loaded(HomeDetailState(historyEvent)))
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: HomeDetailEvent) {
        viewModelScope.launch {
            when (event) {
                is HomeDetailEvent.GetFavorites -> {
                    getFavorites()
                }
                is HomeDetailEvent.InsertFavorite -> {
                    insertFavorite(event.historyEvent)
                }
                is HomeDetailEvent.DeleteFavorite -> {
                    deleteFavorite(event.historyEvent)
                }
            }
        }
    }

    private suspend fun getFavorites() {
        if (_uiState.value is UiState.Loaded) {
            val data = (_uiState.value as UiState.Loaded).data
            when (val result = spaceXUseCase.getHistoryEvents()) {
                is DataResult.Success -> {
                    _uiState.emit(UiState.Loaded(data.copy(favorites = result.data)))
                }
                is DataResult.Error -> {
                    Logger.e(result.e.localizedMessage ?: "UNKNOWN_ERROR")
                }
            }
        }
    }

    private suspend fun insertFavorite(historyEvent: HistoryEvent) {
        if (_uiState.value is UiState.Loaded) {
            val data = (_uiState.value as UiState.Loaded).data
            when (val result = spaceXUseCase.insertHistoryEvent(historyEvent)) {
                is DataResult.Success -> {
                    _uiState.emit(UiState.Loaded(data.copy(favorites = result.data)))
                }
                is DataResult.Error -> {
                    Logger.e(result.e.localizedMessage ?: "UNKNOWN_ERROR")
                }
            }
        }
    }

    private suspend fun deleteFavorite(historyEvent: HistoryEvent) {
        if (_uiState.value is UiState.Loaded) {
            val data = (_uiState.value as UiState.Loaded).data
            when (val result = spaceXUseCase.deleteHistoryEvent(historyEvent)) {
                is DataResult.Success -> {
                    _uiState.emit(UiState.Loaded(data.copy(favorites = result.data)))
                }
                is DataResult.Error -> {
                    Logger.e(result.e.localizedMessage ?: "UNKNOWN_ERROR")
                }
            }
        }
    }
    @AssistedFactory
    interface HomeDetailVMFactory {
        fun create(historyEvent: HistoryEvent): HomeDetailVM
    }
}