package com.yuventius.mvi_view_sample.ui.view.screen.qr.image

sealed class QRImageEvent {
    data class GenerateQRImage(val qrString: String): QRImageEvent()
    data object SaveQRImage: QRImageEvent()
}