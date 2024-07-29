package com.yuventius.mvi_view_sample.ui.view.screen.qr

sealed class QREvent {
    data class SetIntValue(val value: String?): QREvent()
    data object MakeEncryptString: QREvent()
}