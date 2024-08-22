package com.yuventius.mvi_view_sample.ui.view.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yuventius.domain.use_case.PrefUseCase
import com.yuventius.mvi_view_sample.ui.view.base.BaseVM
import com.yuventius.mvi_view_sample.ui.view.base.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginVM @Inject constructor(
    private val prefUseCase: PrefUseCase
): BaseVM<LoginState>() {
    init {
        viewModelScope.launch {
            prefUseCase.getLoginInfo().collect {
                setEmail(it.first)
                setPassword(it.second)
            }
        }
    }

    fun onEvent(event: LoginEvent) {
        viewModelScope.launch {
            when (event) {
                is LoginEvent.SetEmail -> setEmail(event.email)
                is LoginEvent.SetPassword -> setPassword(event.password)
                LoginEvent.Login -> login()
                LoginEvent.LogOut -> logOut()
            }
        }
    }

    private suspend fun setEmail(email: String) = reduce(getData().copy(email = email))

    private suspend fun setPassword(password: String) = reduce(getData().copy(password = password))

    private suspend fun login() {
        reduce(getData().copy(loginPending = true, loginFailed = false, loginSucceed = false))
        checkLogin(getData().email, getData().password).collect { result ->
            if (result) {
                prefUseCase.saveLoginInfo(getData().email, getData().password)
                reduce(getData().copy(loginPending = false, loginFailed = false, loginSucceed = true))
            } else {
                reduce(getData().copy(loginPending = false, loginFailed = true, loginSucceed = false))
            }
        }
    }

    private suspend fun logOut() {
        prefUseCase.logOut().collect { result ->
            if (result) {
                prefUseCase.getLoginInfo().collect { pref ->
                    setEmail(pref.first)
                    setPassword(pref.second)
                }
            }
        }
    }

    private suspend fun checkLogin(email: String, password: String) = flow {
        delay(2000L)
        emit((email == "test@test.com" && password == "test"))
    }
}