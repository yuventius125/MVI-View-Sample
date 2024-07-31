package com.yuventius.mvi_view_sample.ui.view.screen.login

sealed class LoginEvent {
    data class SetEmail(val email: String): LoginEvent()
    data class SetPassword(val password: String): LoginEvent()
    data object Login: LoginEvent()
    data object LogOut: LoginEvent()
}