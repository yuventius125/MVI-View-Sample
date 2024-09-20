package com.yuventius.mvi_view_sample.ui.view.screen.qr

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yuventius.mvi_view_sample.ui.view.base.BaseVM
import com.yuventius.mvi_view_sample.ui.view.base.UIState
import com.yuventius.qr_generator.core.encrypt
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QRVM @Inject constructor(

): BaseVM<QRState>() {
    init {
        viewModelScope.launch {
            reduce(data = QRState())
        }
    }

    fun onEvent(event: QREvent) {
        viewModelScope.launch {
            when (event) {
                is QREvent.SetIntValue -> setQRValue(event.value)
                QREvent.MakeEncryptString -> makeEncryptString()
            }
        }
    }

    private suspend fun setQRValue(value: String?) = reduce(getData().copy(qrValue = value))

    private suspend fun makeEncryptString() = reduce(getData().copy(qrString = getData().qrValue?.encrypt() ?: ""))
}