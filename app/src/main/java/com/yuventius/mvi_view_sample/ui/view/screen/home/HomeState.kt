package com.yuventius.mvi_view_sample.ui.view.screen.home

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class HomeState (
    val test: Boolean = false
) {
    fun copy(): HomeState {
        return Json.decodeFromString(Json.encodeToString(this))
    }
}