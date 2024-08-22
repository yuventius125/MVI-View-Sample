package com.yuventius.mvi_view_sample.ui.view.screen.qr.camera

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yuventius.mvi_view_sample.ui.view.base.BaseVM
import com.yuventius.mvi_view_sample.ui.view.base.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QRCameraVM @Inject constructor(

): BaseVM<QRCameraState>() {

    fun onEvent(event: QRCameraEvent) {
        viewModelScope.launch {
            when (event) {
                is QRCameraEvent.DetectUniqueString -> detectUniqueString()
                is QRCameraEvent.InitializeCamera -> initializeCamera()
            }
        }
    }

    private suspend fun initializeCamera() = reduce(QRCameraState(isCameraRunning = true))

    private suspend fun detectUniqueString() = reduce(QRCameraState(isCameraRunning = false, isAuthenticated = true))
}