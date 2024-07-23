package com.yuventius.data.remote

import android.util.Log
import com.yuventius.data.core.DataConst
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object AppClient {
    fun getInstance() = HttpClient(Android) {
        engine {
            connectTimeout = 1000
            socketTimeout = 1000
        }

        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    useAlternativeNames = true
                    ignoreUnknownKeys = true
                }
            )
        }

        install(Logging) {
            logger = object: Logger {
                override fun log(message: String) {
                    Log.d("[REMOTE LOG]", message)
                }
            }
        }

        install(ResponseObserver) {
            onResponse { response ->
                Log.d("[REMOTE CODE]", "${response.status.value}")
            }
        }

        install(DefaultRequest) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            host = DataConst.API_URL
        }
    }

    enum class Apis(val url: String) {
        GET_HISTORY_EVENT("/v4/history")
    }

    sealed class ApiResult {
        data class Success(val data: Any): ApiResult()
        data class Error(val message: String): ApiResult()
    }
}