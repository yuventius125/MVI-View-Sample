package com.yuventius.mvi_view_sample.ui.view.screen.qr.camera

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yuventius.mvi_view_sample.ui.view.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QRCameraVM @Inject constructor(

): ViewModel() {
    private val _uiState = MutableStateFlow<UiState<QRCameraState>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: QRCameraEvent) {
        viewModelScope.launch {
            when (event) {
                is QRCameraEvent.DetectUniqueString -> detectUniqueString()
                is QRCameraEvent.InitializeCamera -> initializeCamera()
            }
        }
    }

    private suspend fun initializeCamera() {
        _uiState.emit(UiState.Loaded(QRCameraState(isCameraRunning = true)))
    }

    private suspend fun detectUniqueString() {
        _uiState.emit(UiState.Loaded(QRCameraState(isCameraRunning = false, isAuthenticated = true)))
    }
}