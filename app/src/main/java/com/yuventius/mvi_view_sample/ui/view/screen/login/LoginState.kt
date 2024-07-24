package com.yuventius.mvi_view_sample.ui.view.screen.login

import com.yuventius.mvi_view_sample.ui.view.base.Copyable
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class LoginState (
    val email: String = "",
    val password: String = "",
    val loginPending: Boolean = false,
    val loginSucceed: Boolean = false,
    val loginFailed: Boolean = false,
): Copyable<LoginState> {
    override fun copy(): LoginState = Json.decodeFromString(Json.encodeToString(this))
}