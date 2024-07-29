package com.yuventius.mvi_view_sample.ui.view.component

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionView (
    modifier: Modifier,
    permissionState: PermissionState,
    msg: String = ""
) {
    val context = LocalContext.current

    Box(
        modifier = modifier
            .background(Color.Black.copy(alpha = 0.6F))
    ) {
        when (permissionState.status) {
            is PermissionStatus.Granted -> {

            }
            is PermissionStatus.Denied -> {
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(12.dp)
                        .background(Color.White)
                        .padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val textToShow = if (permissionState.status.shouldShowRationale) {
                        "QR코드 인식을 위해 카메라 권한이 필요합니다."
                    } else {
                        "QR코드 인식을 위해 카메라 권한이 필요합니다.\n설정에서 권한을 허용해주세요"
                    }
                    Text(
                        modifier = Modifier
                            .padding(12.dp),
                        text = textToShow,
                        textAlign = TextAlign.Center,
                    )
                    Button(onClick = {
                        if (permissionState.status.shouldShowRationale.not()) {
                            context.startActivity(
                                Intent(
                                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                    Uri.fromParts("package", context.packageName, null)
                                )
                            )
                        } else {
                            permissionState.launchPermissionRequest()
                        }
                    }) {
                        Text(text = msg.ifBlank { "권한 허용" })
                    }
                }
            }
        }

    }
}