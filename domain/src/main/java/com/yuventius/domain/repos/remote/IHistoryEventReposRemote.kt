package com.yuventius.domain.repos.remote

import com.yuventius.domain.model.DataResult
import com.yuventius.domain.model.HistoryEvent

interface IHistoryEventReposRemote {
    suspend fun getHistoryEvents(): DataResult<List<HistoryEvent>>
}