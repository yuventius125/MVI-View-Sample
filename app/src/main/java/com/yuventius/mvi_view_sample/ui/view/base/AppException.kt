package com.yuventius.mvi_view_sample.ui.view.base

sealed class AppException(message: String? = null, cause: Throwable? = null): Exception(message, cause) {
    data class NeedStateValueException (
        override val message: String? = "State 값이 Null 입니다.",
        override val cause: Throwable? = null
    ): AppException(message, cause)
}