package com.yuventius.domain.use_case

import com.yuventius.domain.model.DataResult
import com.yuventius.domain.model.HistoryEvent
import com.yuventius.domain.repos.local.IHistoryEventReposLocal
import com.yuventius.domain.repos.remote.IHistoryEventReposRemote
import javax.inject.Inject

class SpaceXUseCase @Inject constructor(
    private var historyEventRemote: IHistoryEventReposRemote,
    private var historyEventLocal: IHistoryEventReposLocal
) {
    suspend fun getHistoryEvents(isRemote: Boolean = false): DataResult<List<HistoryEvent>> {
        return if (isRemote) historyEventRemote.getHistoryEvents() else historyEventLocal.getHistoryEvents()
    }

    suspend fun insertHistoryEvent(historyEvent: HistoryEvent): DataResult<List<HistoryEvent>> {
        val result = historyEventLocal.insertHistoryEvent(historyEvent = historyEvent)
        if (result is DataResult.Success) {
            return if (result.data) {
                historyEventLocal.getHistoryEvents()
            } else {
                DataResult.Error(Exception("Insert Failed"))
            }
        }
        return DataResult.Error((result as DataResult.Error).e)
    }

    suspend fun deleteHistoryEvent(historyEvent: HistoryEvent): DataResult<List<HistoryEvent>> {
        val result = historyEventLocal.deleteHistoryEvent(historyEvent)
        if (result is DataResult.Success) {
            return if (result.data) {
                historyEventLocal.getHistoryEvents()
            } else {
                DataResult.Error(Exception("Delete Failed"))
            }
        }
        return DataResult.Error((result as DataResult.Error).e)
    }
}