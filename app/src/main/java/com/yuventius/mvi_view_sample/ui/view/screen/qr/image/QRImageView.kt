package com.yuventius.mvi_view_sample.ui.view.screen.qr.image

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.navigation.NavController
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.yuventius.mvi_view_sample.ui.view.base.UIState

@Composable
fun QRImageView(
    navController: NavController,
    qrString: String,
    vm: QRImageVM = hiltViewModel(),
    onCreate: () -> Unit = {}
) {
    val context = LocalContext.current
    val state = vm.uiState.collectAsState()
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        when (state.value) {
            UIState.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            is UIState.Success -> {
                val data = (state.value as UIState.Success).data

                Image(
                    modifier = Modifier
                        .size(200.dp)
                        .align(Alignment.Center),
                    painter = rememberDrawablePainter(drawable = data.qrDrawable),
                    contentDescription = ""
                )

                Button(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(),
                    onClick = { vm.onEvent(QRImageEvent.SaveQRImage) }
                ) {
                    Text(text = "이미지 저장")
                }
            }
            UIState.Error -> {}
        }

        LifecycleEventEffect(event = Lifecycle.Event.ON_CREATE) {
            onCreate.invoke()
            vm.onEvent(QRImageEvent.GenerateQRImage(qrString))
        }
    }
}