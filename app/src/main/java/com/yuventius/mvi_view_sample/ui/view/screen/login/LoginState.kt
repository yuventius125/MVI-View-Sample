package com.yuventius.mvi_view_sample.ui.view.screen.login

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class LoginState (
    val test: Boolean = false
) {
    fun copy(): LoginState {
        return Json.decodeFromString(Json.encodeToString(this))
    }
}