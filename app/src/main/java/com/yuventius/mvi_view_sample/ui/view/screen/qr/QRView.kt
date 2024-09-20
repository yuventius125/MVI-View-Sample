package com.yuventius.mvi_view_sample.ui.view.screen.qr

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.navigation.NavController
import com.yuventius.mvi_view_sample.ext.route
import com.yuventius.mvi_view_sample.ui.view.base.UIState
import com.yuventius.mvi_view_sample.ui.view.component.CustomTextField
import com.yuventius.mvi_view_sample.ui.view.screen.Screen
import com.yuventius.mvi_view_sample.util.AppConst

@Composable
fun QRView(
    navController: NavController,
    vm: QRVM = hiltViewModel(),
    onCreate: () -> Unit = {}
) {
    val context = LocalContext.current
    val state = vm.uiState.collectAsState()
    val scope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val clipboardManager = LocalClipboardManager.current

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        when (state.value) {
            is UIState.Success -> {
                val data = (state.value as UIState.Success).data

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CustomTextField(
                        modifier = Modifier
                            .focusRequester(focusRequester)
                            .padding(horizontal = 24.dp),
                        value = data.qrValue ?: "",
                        onValueChange = {
                            if (it.isNotBlank()) {
                                vm.onEvent(QREvent.SetIntValue(it))
                            } else {
                                vm.onEvent(QREvent.SetIntValue(null))
                            }
                        },
                        leadingIcon = Icons.Default.Edit,
                        trailingIcon = Icons.Default.PlayArrow,
                        onTrailingIconClick = {
                            focusManager.clearFocus()
                            vm.onEvent(QREvent.MakeEncryptString)
                        },
                        imeAction = ImeAction.Done,
                        onAction = {
                            keyboardController?.hide()
                            focusManager.clearFocus()
                            vm.onEvent(QREvent.MakeEncryptString)
                        }
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        modifier = Modifier
                            .padding(12.dp)
                            .clickable {
                                clipboardManager.setText(AnnotatedString(data.qrString))
                            },
                        text = data.qrString
                    )

                    Row(
                        modifier = Modifier
                            .padding(12.dp)
                    ) {
                        Button(
                            onClick = {
                                if (data.qrString.isNotBlank()) {
                                    navController.route(
                                        Screen.Route.QRImage, mapOf("qrString" to "${AppConst.DEFAULT_PAGE_URL}?pid=${data.qrString}")
                                    )
                                }
                            }
                        ) {
                            Text(text = "QR 이미지 생성")
                        }
                        Spacer(modifier = Modifier.padding(12.dp))
                        Button(onClick = { navController.route(Screen.Route.QRCamera) }) {
                            Text(text = "QR 카메라 실행")
                        }
                    }
                }

            }
            else -> {}
        }
    }

    LifecycleEventEffect(event = Lifecycle.Event.ON_CREATE) {
        onCreate.invoke()
    }
}