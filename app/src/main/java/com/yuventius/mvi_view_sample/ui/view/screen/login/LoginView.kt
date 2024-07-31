package com.yuventius.mvi_view_sample.ui.view.screen.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yuventius.mvi_view_sample.ext.root
import com.yuventius.mvi_view_sample.ui.view.base.UiState
import com.yuventius.mvi_view_sample.ui.view.component.CustomTextField
import com.yuventius.mvi_view_sample.ui.view.screen.Screen

@Composable
fun LoginView(
    navController: NavController,
    vm: LoginVM = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState = vm.uiState.collectAsState()
    val scope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        when (uiState.value) {
            UiState.Failed -> Text(text = "UNKNOWN ERROR")
            UiState.Loading -> CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
            )
            is UiState.Loaded -> {
                val data = (uiState.value as UiState.Loaded).data
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (data.loginFailed) {
                        Text(
                            modifier = Modifier
                                .padding(12.dp),
                            text = "로그인 실패"
                        )
                    }
                    CustomTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp)
                            .padding(bottom = 12.dp),
                        value = data.email,
                        onValueChange = { vm.onEvent(LoginEvent.SetEmail(it)) },
                        leadingIcon = Icons.Default.Email,
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    )
                    CustomTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp)
                            .padding(bottom = 12.dp),
                        value = data.password,
                        onValueChange = { vm.onEvent(LoginEvent.SetPassword(it)) },
                        leadingIcon = Icons.Default.Lock,
                        keyboardType = KeyboardType.Password,
                        onAction = {
                            keyboardController?.hide()
                            vm.onEvent(LoginEvent.Login)
                        }
                    )
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        shape = RoundedCornerShape(12.dp),
                        onClick = { vm.onEvent(LoginEvent.Login) },
                        enabled = data.loginPending.not()
                    ) {
                        if (data.loginPending) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(12.dp),
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(
                                text = "로그인"
                            )
                        }
                    }
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        shape = RoundedCornerShape(12.dp),
                        onClick = { vm.onEvent(LoginEvent.LogOut) },
                        enabled = data.loginPending.not()
                    ) {
                        if (data.loginPending) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(12.dp),
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(
                                text = "로그아웃"
                            )
                        }
                    }
                }

                LaunchedEffect(key1 = data.loginSucceed) {
                    if (data.loginSucceed) {
                        navController.root(Screen.Root.Home)
                    }
                }
            }
        }
    }
}