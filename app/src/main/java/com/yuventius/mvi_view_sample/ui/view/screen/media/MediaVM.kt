package com.yuventius.mvi_view_sample.ui.view.screen.media

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lukus.media_extension.MediaUtil
import com.yuventius.mvi_view_sample.ext.toFormattedString
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class MediaVM @Inject constructor(
    @ApplicationContext private val context: Context
): ViewModel() {
    private val _state = MutableStateFlow(MediaState())
    val state = _state.asStateFlow()

    private lateinit var mediaUtil: MediaUtil

    fun onEvent(event: MediaEvent) {
        viewModelScope.launch {
            when (event) {
                is MediaEvent.Initialize -> initialize(event.activity)
                MediaEvent.PausePlay -> {}
                MediaEvent.PauseRecord -> pauseRecord()
                is MediaEvent.PreparePlay -> {}
                MediaEvent.ResumePlay -> {}
                MediaEvent.ResumeRecord -> resumeRecord()
                is MediaEvent.StartPlay -> playFile(event.name)
                MediaEvent.StartRecord -> startRecord()
                MediaEvent.StopPlay -> stopFile()
                MediaEvent.StopRecord -> stopRecord()
                is MediaEvent.SeekTo -> seekTo(event.percent)
                MediaEvent.Seek -> seek()
            }
        }
    }

    private suspend fun initialize(activity: Activity) {
        mediaUtil = MediaUtil(activity)
        mediaUtil.setLifeCycleScope(viewModelScope)
//        mediaUtil.requestPermissions(1000)
        getRecordFiles()
    }

    private suspend fun startRecord() {
        val fileName = "${LocalDateTime.now().toFormattedString() ?: "ERROR"}.mp4"
        val result = mediaUtil.startRecord(fileName)
        reduce(_state.value.copy(fileName = fileName, isRecording = result))
    }

    private suspend fun pauseRecord() {
        val result = mediaUtil.pauseRecord()
        reduce(_state.value.copy(isRecording = result))
    }

    private suspend fun resumeRecord() {
        val result = mediaUtil.resumeRecord()
        reduce(_state.value.copy(isRecording = result))
    }

    private suspend fun stopRecord() {
        mediaUtil.stopRecord()
//        val fileName = "${LocalDateTime.now().toFormattedString() ?: "ERROR"}.mp4"
//        mediaUtil.prepareRecord(fileName)
        reduce(_state.value.copy(isRecording = false))
        mediaUtil.recordRelease()
        getRecordFiles()
    }

    private suspend fun playFile(name: String) {
        mediaUtil.playFile(name = name, onPlaying = { current, total ->
            viewModelScope.launch {
                if (_state.value.isSliderDraggingState.not()) {
                    reduce(_state.value.copy(currentPosition = current, totalDuration = total))
                }
            }
        }) {
            viewModelScope.launch {
                reduce(_state.value.copy(isPlaying = false))
            }
        }
        reduce(_state.value.copy(isPlaying = true))
    }

    private suspend fun stopFile() {
        mediaUtil.stopFile()
        reduce(_state.value.copy(isPlaying = false))
    }

    private suspend fun seekTo(time: Float) {
        reduce(_state.value.copy(isSliderDraggingState = true, currentPosition = time.toLong()))
    }

    private suspend fun seek() {
        mediaUtil.seekTo(time = _state.value.currentPosition, onPlaying = { current, total ->
            viewModelScope.launch {
                if (_state.value.isSliderDraggingState.not()) {
                    reduce(_state.value.copy(currentPosition = current, totalDuration = total))
                }
            }
        }) {
            viewModelScope.launch {
                reduce(_state.value.copy(isPlaying = false))
            }
        }
        reduce(_state.value.copy(isPlaying = true, isSliderDraggingState = false))
    }

    private suspend fun getRecordFiles() {
        val fileList = ArrayList(context.filesDir.listFiles()?.map { it.name }?.filter { it.contains(".mp4") }?.sortedDescending() ?: listOf())
        reduce(_state.value.copy(recordList = fileList))
    }

    private suspend fun reduce(state: MediaState) = _state.emit(state)
}