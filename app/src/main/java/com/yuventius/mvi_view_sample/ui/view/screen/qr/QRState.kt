package com.yuventius.mvi_view_sample.ui.view.screen.qr

import com.yuventius.mvi_view_sample.util.AppConst
import kotlinx.serialization.Serializable

@Serializable
data class QRState (
    val qrValue: String? = AppConst.APP_QR_STRING,
    val qrString: String = "",
    val routeToQRMaker: Boolean = false,
    val routeToQRCamera: Boolean = false,
)