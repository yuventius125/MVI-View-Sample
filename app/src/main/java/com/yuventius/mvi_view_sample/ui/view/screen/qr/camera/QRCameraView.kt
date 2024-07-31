package com.yuventius.mvi_view_sample.ui.view.screen.qr.camera

import android.Manifest
import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.CompoundBarcodeView
import com.journeyapps.barcodescanner.camera.CameraSettings
import com.orhanobut.logger.Logger
import com.yuventius.mvi_view_sample.ui.view.base.UiState
import com.yuventius.mvi_view_sample.ui.view.component.PermissionView
import com.yuventius.mvi_view_sample.util.AppConst
import com.yuventius.qr_generator.core.decrypt

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun QRCameraView(
    navController: NavController,
    vm: QRCameraVM = hiltViewModel(),
    onCreate: () -> Unit = {}
) {
    val context = LocalContext.current
    val state = vm.uiState.collectAsState()
    val permissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        when (state.value) {
            is UiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
            is UiState.Failed -> {
                Text(text = "ERROR")
            }
            is UiState.Loaded -> {
                val data = (state.value as UiState.Loaded).data

                if (data.isCameraRunning) {
                    AndroidView(factory = { ctx ->
                        val preview = CompoundBarcodeView(ctx)
//                        preview.cameraSettings.isAutoFocusEnabled = true
                        preview.setStatusText("QR코드를 인식해주세요")
                        preview.apply {
                            val capture = CaptureManager(ctx as Activity, this)
                            capture.initializeFromIntent(ctx.intent, null)
                            capture.decode()
                            this.decodeContinuous { result ->
                                Logger.d("Scanned: ${result.text}")
                                try {
                                    if (result.text.decrypt() == AppConst.APP_QR_STRING) {
                                        vm.onEvent(QRCameraEvent.DetectUniqueString)
                                        this.pause()
                                    }
                                } catch (_: Exception) { }
                            }
                            this.resume()
                        }
                    })
                } else {
                    Text(
                        modifier = Modifier
                            .align(Alignment.Center),
                        text = "고유 String 인식 완료",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                }
            }
        }
        if (permissionState.status is PermissionStatus.Denied) {
            PermissionView(
                modifier = Modifier
                    .fillMaxSize(),
                permissionState = permissionState
            )
        }
    }

    LaunchedEffect(permissionState) {
        if (permissionState.status is PermissionStatus.Granted) {
            vm.onEvent(QRCameraEvent.InitializeCamera)
        }
    }

    LifecycleEventEffect(event = Lifecycle.Event.ON_CREATE) {
        onCreate.invoke()
    }
}