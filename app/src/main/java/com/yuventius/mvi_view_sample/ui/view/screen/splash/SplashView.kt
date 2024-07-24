package com.yuventius.mvi_view_sample.ui.view.screen.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.navigation.NavController
import com.yuventius.mvi_view_sample.BuildConfig
import com.yuventius.mvi_view_sample.ext.root
import com.yuventius.mvi_view_sample.ext.url
import com.yuventius.mvi_view_sample.ui.view.base.UiState
import com.yuventius.mvi_view_sample.ui.view.screen.Screen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashView(
    navController: NavController,
    vm: SplashVM = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState = vm.uiState.collectAsState()
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = "Splash View"
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (uiState.value) {
                is UiState.Loading -> {
                    CircularProgressIndicator()

                    LifecycleEventEffect(event = Lifecycle.Event.ON_CREATE) {
                        /**
                         * 업데이트 필요
                         */
//                        vm.onEvent(SplashEvent.CheckVersion(tempVersion = "0.0.0.1"))
                        /**
                         * 업데이트 필요 없음
                         */
                        vm.onEvent(SplashEvent.CheckVersion(tempVersion = "1.0"))
                    }
                }
                is UiState.Loaded<SplashState> -> {
                    val data = (uiState.value as UiState.Loaded<SplashState>).data

                    if (data.needUpdate) {
                        Text(
                            text = "업데이트가 필요합니다",
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        )
                        Button(onClick = {
                            context.url("https://naver.com")
                        }) {
                            Text(text = "업데이트")
                        }
                    } else {
                        Text(text = BuildConfig.VERSION_NAME)

                        LifecycleEventEffect(event = Lifecycle.Event.ON_CREATE) {
                            scope.launch {
                                delay(2000L)
                                navController.root(Screen.Root.Login)
                            }
                        }
                    }
                }
                is UiState.Failed -> {
                    Text(
                        text = "네트워크 에러",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                    Text(text = "나중에 다시 시도해주세요")
                }
            }
        }
    }
}