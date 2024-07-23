package com.yuventius.domain.repos.local

import com.yuventius.domain.model.DataResult
import com.yuventius.domain.model.HistoryEvent

interface IHistoryEventReposLocal {
    suspend fun getHistoryEvents(): DataResult<List<HistoryEvent>>
    suspend fun insertHistoryEvent(historyEvent: HistoryEvent): DataResult<Boolean>
    suspend fun deleteHistoryEvent(historyEvent: HistoryEvent): DataResult<Boolean>
}