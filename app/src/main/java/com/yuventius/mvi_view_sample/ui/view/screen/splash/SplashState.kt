package com.yuventius.mvi_view_sample.ui.view.screen.splash

import com.yuventius.mvi_view_sample.ui.view.base.Copyable
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class SplashState(
    val needUpdate: Boolean = false
): Copyable<SplashState> {
    override fun copy(): SplashState = Json.decodeFromString(Json.encodeToString(this))
}