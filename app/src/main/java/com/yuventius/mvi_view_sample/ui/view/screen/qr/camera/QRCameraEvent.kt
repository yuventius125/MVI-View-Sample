package com.yuventius.mvi_view_sample.ui.view.screen.qr.camera

sealed class QRCameraEvent {
    data object InitializeCamera: QRCameraEvent()
    data object DetectUniqueString: QRCameraEvent()
}