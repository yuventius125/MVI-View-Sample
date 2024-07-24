package com.yuventius.mvi_view_sample.ui.view.screen.splash

sealed class SplashEvent {
    data class CheckVersion(val tempVersion: String? = null): SplashEvent()
}