package com.yuventius.mvi_view_sample.ui.view.screen.media

import android.app.Activity
import com.yuventius.mvi_view_sample.core.MainActivity

sealed class MediaEvent {
    data class Initialize(val activity: Activity): MediaEvent()
    data object StartRecord: MediaEvent()
    data object PauseRecord: MediaEvent()
    data object ResumeRecord: MediaEvent()
    data object StopRecord: MediaEvent()
    data class PreparePlay(val name: String): MediaEvent()
    data class StartPlay(val name: String): MediaEvent()
    data object PausePlay: MediaEvent()
    data object ResumePlay: MediaEvent()
    data object StopPlay: MediaEvent()
    data class SeekTo(val percent: Float): MediaEvent()
    data object Seek: MediaEvent()
}