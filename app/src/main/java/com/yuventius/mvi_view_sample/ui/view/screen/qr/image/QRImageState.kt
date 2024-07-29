package com.yuventius.mvi_view_sample.ui.view.screen.qr.image

import android.graphics.drawable.Drawable
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class QRImageState (
    val qrString: String? = null,
    @Contextual val qrDrawable: Drawable? = null,
    val pendingSaveImage: Boolean = false
)