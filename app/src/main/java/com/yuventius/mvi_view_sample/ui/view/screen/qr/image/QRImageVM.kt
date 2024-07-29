package com.yuventius.mvi_view_sample.ui.view.screen.qr.image

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.provider.MediaStore
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yuventius.mvi_view_sample.R
import com.yuventius.mvi_view_sample.ui.view.base.UiState
import com.yuventius.qr_generator.QRLib
import com.yuventius.qr_generator.core.decrypt
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import java.io.FileNotFoundException
import javax.inject.Inject

@HiltViewModel
class QRImageVM @Inject constructor(
    @ApplicationContext
    val context: Context
): ViewModel() {
    private val _state = MutableStateFlow<UiState<QRImageState>>(UiState.Loading)
    val state = _state.asStateFlow()

    fun onEvent(event: QRImageEvent) {
        viewModelScope.launch {
            when (event) {
                is QRImageEvent.GenerateQRImage -> setQrString(event.qrString)
                is QRImageEvent.SaveQRImage -> saveImage()
            }
        }
    }

    private suspend fun setQrString(qrString: String) {
        val qrDrawable = QRLib.makeQRImage(qrString, context.getDrawable(R.drawable.ic_launcher_foreground))
        _state.emit(UiState.Loaded(QRImageState(qrString = qrString, qrDrawable = qrDrawable)))
    }

    private suspend fun saveImage() {
        val data = (state.value as UiState.Loaded).data.copy(pendingSaveImage = true)
        _state.emit(UiState.Loaded(data))
        val values = ContentValues().apply {
            val value = data.qrString!!.decrypt()
            put(MediaStore.Images.Media.DISPLAY_NAME, "QRImage_$value.png")
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            put(MediaStore.Images.Media.IS_PENDING, 1)
        }

        val resolver = context.contentResolver
        val imageContentUri = resolver.insert(MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY), values)
        if (imageContentUri == null) {
            _state.emit(UiState.Loaded(data.copy(pendingSaveImage = false)))
            return
        }

        try {
            resolver.openOutputStream(imageContentUri, "w").use { os ->
                data.qrDrawable!!.toBitmap(500, 500).compress(Bitmap.CompressFormat.PNG, 100, os!!)
                values.clear()
                values.put(MediaStore.Images.Media.IS_PENDING, 0)
                resolver.update(imageContentUri, values, null, null)
                _state.emit(UiState.Loaded(data.copy(pendingSaveImage = false)))
            }
        } catch (e: FileNotFoundException) {
            _state.emit(UiState.Loaded(data.copy(pendingSaveImage = false)))
            return
        }
    }
}