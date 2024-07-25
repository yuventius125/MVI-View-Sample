package com.yuventius.data.remote

import android.util.Log
import com.yuventius.data.core.DataConst
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.header
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import land.sungbin.ktor.client.plugins.logging.JsonAwareLogger
import land.sungbin.ktor.client.plugins.logging.JsonAwareLogging

object AppClient {
    @OptIn(ExperimentalSerializationApi::class)
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
                    com.orhanobut.logger.Logger.d(message)
                }
            }
            level = LogLevel.HEADERS
        }

        install(ResponseObserver) {
            onResponse { response ->
                com.orhanobut.logger.Logger.json(response.bodyAsText())
            }
        }

        install(DefaultRequest) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            header(HttpHeaders.Accept, ContentType.Application.Json)
            host = DataConst.API_URL
            url {
                protocol = URLProtocol.HTTPS
            }
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