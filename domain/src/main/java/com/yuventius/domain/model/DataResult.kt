package com.yuventius.domain.model

sealed class DataResult<out T> {
    data class Success<T>(val data: T): DataResult<T>()
    data class Error(val e: Exception): DataResult<Nothing>()
}
