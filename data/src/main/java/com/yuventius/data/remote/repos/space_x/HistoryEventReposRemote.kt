package com.yuventius.data.remote.repos.space_x

import com.yuventius.data.remote.AppClient
import com.yuventius.data.remote.model.space_x.HistoryEventRemote
import com.yuventius.data.remote.model.space_x.HistoryMapperRemote.toDomain
import com.yuventius.domain.model.DataResult
import com.yuventius.domain.model.HistoryEvent
import com.yuventius.domain.repos.remote.IHistoryEventReposRemote
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class HistoryEventReposRemote @Inject constructor(
    private val httpClient: HttpClient
): IHistoryEventReposRemote {
    override suspend fun getHistoryEvents(): DataResult<List<HistoryEvent>> {
        try {
            val result = httpClient.get(urlString = AppClient.Apis.GET_HISTORY_EVENT.url)
                .body<List<HistoryEventRemote>>()
            return DataResult.Success(result.map { it.toDomain() })
        } catch (e: Exception) {
            return DataResult.Error(e)
        }
    }
}