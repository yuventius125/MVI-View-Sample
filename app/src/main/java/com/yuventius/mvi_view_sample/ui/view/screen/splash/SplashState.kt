package com.yuventius.mvi_view_sample.ui.view.screen.splash

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class SplashState(
    val needUpdate: Boolean = false
) {
    fun copy(): SplashState {
        return Json.decodeFromString(Json.encodeToString(this))
    }
}