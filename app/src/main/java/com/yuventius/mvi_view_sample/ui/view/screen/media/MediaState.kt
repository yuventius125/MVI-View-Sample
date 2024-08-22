package com.yuventius.mvi_view_sample.ui.view.screen.media

import com.yuventius.mvi_view_sample.ui.view.base.Copyable
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

@Serializable
data class MediaState (
    val fileName: String = "",
    val isRecording: Boolean = false,
    val isPlayPrepared: Boolean = false,
    val isPlaying: Boolean = false,
    val recordList: ArrayList<String> = arrayListOf(),
    val currentPosition: Long = 0L,
    val totalDuration: Long = 0L,
    val isSliderDraggingState: Boolean = false
): Copyable<MediaState> {
    override fun copy(): MediaState = Json.decodeFromString(Json.encodeToString(this))
}