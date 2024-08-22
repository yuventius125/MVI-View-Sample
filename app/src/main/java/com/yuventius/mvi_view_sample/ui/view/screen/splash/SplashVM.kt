package com.yuventius.mvi_view_sample.ui.view.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yuventius.mvi_view_sample.BuildConfig
import com.yuventius.mvi_view_sample.ext.toCompareVersion
import com.yuventius.mvi_view_sample.ui.view.base.BaseVM
import com.yuventius.mvi_view_sample.ui.view.base.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashVM @Inject constructor(

): BaseVM<SplashState>() {

    fun onEvent(event: SplashEvent) {
        viewModelScope.launch {
            when (event) {
                is SplashEvent.CheckVersion -> getVersion(event.tempVersion)
            }
        }
    }

    private suspend fun getVersion(serverVersion: String?) {
        serverVersion?.let {
            getFakeVersion(serverVersion).collect { version ->
                reduce(SplashState(needUpdate = BuildConfig.VERSION_NAME.toCompareVersion(version)))
            }
        } ?: run {
            reduce(uiState = UIState.Error)
        }
    }

    /**
     * TEST CODE
     * 3초 뒤 버전을 반환
     */
    private suspend fun getFakeVersion(version: String): Flow<String> = flow {
        delay(1000L)
        emit(version)
    }
}