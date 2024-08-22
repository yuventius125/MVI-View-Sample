package com.lukus.media_extension

import android.Manifest
import android.app.Activity
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.media.MediaRecorder.AudioEncoder
import android.media.MediaRecorder.AudioSource
import android.media.MediaRecorder.OutputFormat
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File

class MediaUtil(private val context: Activity) {
    companion object {
        val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayListOf(
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_MEDIA_AUDIO
            )
        } else {
            arrayListOf(
                Manifest.permission.RECORD_AUDIO,
            )
        }
    }

    private var mediaRecorder: MediaRecorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        MediaRecorder(context)
    } else {
        MediaRecorder()
    }

    private val mediaPlayer: MediaPlayer = MediaPlayer()

    var isRecordPrepared: Boolean = false
    var isRecording: Boolean = false

    var coroutineScope: CoroutineScope? = null

    init {
//        mediaRecorder.setAudioChannels(1)
        mediaRecorder.setAudioSource(AudioSource.MIC)
        mediaRecorder.setOutputFormat(OutputFormat.MPEG_4)
        mediaRecorder.setAudioEncoder(AudioEncoder.AAC)
//        mediaRecorder.setAudioSamplingRate(44100)
//        mediaRecorder.setAudioEncodingBitRate(96000)
    }

    private fun recordInitialize() {
        mediaRecorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(context)
        } else {
            MediaRecorder()
        }

        mediaRecorder.setAudioSource(AudioSource.MIC)
        mediaRecorder.setOutputFormat(OutputFormat.MPEG_4)
        mediaRecorder.setAudioEncoder(AudioEncoder.AAC)
    }

    fun recordRelease() {
        mediaRecorder.release()
        recordInitialize()
    }

    fun setLifeCycleScope(lifecycleOwner: CoroutineScope) {
        this.coroutineScope = lifecycleOwner
    }

    fun startRecord(name: String): Boolean {
        val file = File(context.filesDir, name)

        if (file.exists()) {
            file.delete()
        }
        try {
            mediaRecorder.setOutputFile(file)
        } catch (e: Exception) {
            mediaRecorder.setNextOutputFile(file)
        }

        try {
            mediaRecorder.prepare()
            isRecordPrepared = true
        } catch (e: Exception) {
            Log.e("[MediaUtil]", e.localizedMessage ?: "UNKNOWN ERROR")
            isRecordPrepared = false
        }

        if (isRecordPrepared) {
            mediaRecorder.start()
            isRecording = true
            return true
        } else {
            Log.e("[MediaUtil]", "녹음 파일이 지정되지 않았습니다.")
            return false
        }
    }

    fun pauseRecord(): Boolean {
        if (isRecording) {
            mediaRecorder.pause()
            isRecording = false
            return true
        } else {
            return false
        }
    }

    fun resumeRecord(): Boolean {
        if (isRecording.not() && isRecordPrepared) {
            mediaRecorder.resume()
            isRecording = true
        } else {
            Log.e("[MediaUtil]", "이미 녹음 중이거나 녹음 파일이 지정되지 않았습니다.")
        }
        return isRecording
    }

    fun stopRecord(): Boolean {
        if (isRecording) {
            mediaRecorder.stop()
            isRecording = false
            isRecordPrepared = false
        } else {
            Log.e("[MediaUtil]", "녹음 중 상태가 아닙니다.")
        }
        return isRecordPrepared.not()
    }

    fun playFile(name: String, onPlaying: (current: Long, total: Long) -> Unit, onCompletion: () -> Unit): Boolean {
        try {
            mediaPlayer.reset()
            val file = File(context.filesDir, name)
            mediaPlayer.setDataSource("file://${file.absolutePath}")
            mediaPlayer.prepare()

            coroutineScope?.launch {
                while (mediaPlayer.isPlaying) {
                    onPlaying.invoke(mediaPlayer.currentPosition.toLong(), mediaPlayer.duration.toLong())
                    delay(100L)
                }
            }

            mediaPlayer.setOnCompletionListener {
                onCompletion.invoke()
            }
            mediaPlayer.start()
            return true
        } catch (e: Exception) {
            Log.e("[MediaUtil]", e.localizedMessage ?: "UNKNOWN ERROR")
            return false
        }
    }

    fun stopFile() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            mediaPlayer.reset()
        }
    }

    fun seekTo(time: Long, onPlaying: (current: Long, total: Long) -> Unit, onCompletion: () -> Unit) {
        mediaPlayer.seekTo(time.toInt())
        if (mediaPlayer.isPlaying.not()) {
            coroutineScope?.launch {
                while (mediaPlayer.isPlaying) {
                    onPlaying.invoke(mediaPlayer.currentPosition.toLong(), mediaPlayer.duration.toLong())
                    delay(100L)
                }
            }

            mediaPlayer.setOnCompletionListener {
                onCompletion.invoke()
            }

            mediaPlayer.start()
        }
    }

    fun requestPermissions(requestCode: Int) = ActivityCompat.requestPermissions(context, permissions.toTypedArray(), requestCode)
}