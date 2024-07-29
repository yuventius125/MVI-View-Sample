package com.yuventius.mvi_view_sample.ui.view.screen.qr

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yuventius.mvi_view_sample.ui.view.base.UiState
import com.yuventius.qr_generator.core.encrypt
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QRVM @Inject constructor(

): ViewModel() {
    private val _state = MutableStateFlow<UiState<QRState>>(UiState.Loaded(QRState()))
    val state = _state.asStateFlow()

    fun onEvent(event: QREvent) {
        viewModelScope.launch {
            when (event) {
                is QREvent.SetIntValue -> setQRValue(event.value)
                QREvent.MakeEncryptString -> makeEncryptString()
            }
        }
    }

    private suspend fun setQRValue(value: String?) {
        (_state.value as? UiState.Loaded<QRState>)?.data?.let {
            _state.emit(UiState.Loaded(it.copy(qrValue = value)))
        }
    }

    private suspend fun makeEncryptString() {
        (_state.value as? UiState.Loaded<QRState>)?.data?.let {
            _state.emit(UiState.Loaded(it.copy(qrString = it.qrValue?.encrypt() ?: "")))
        }
    }
}