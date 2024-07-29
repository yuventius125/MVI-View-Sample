package com.yuventius.data.local.repos

import com.yuventius.data.local.dao.space_x.HistoryEventDao
import com.yuventius.data.local.model.space_x.HistoryEventMapper.toData
import com.yuventius.data.local.model.space_x.HistoryEventMapper.toDomain
import com.yuventius.domain.model.DataResult
import com.yuventius.domain.model.HistoryEvent
import com.yuventius.domain.repos.local.IHistoryEventReposLocal
import javax.inject.Inject

class HistoryEventReposLocal @Inject constructor(
    private val historyEventDao: HistoryEventDao
): IHistoryEventReposLocal {
    override suspend fun getHistoryEvents(): DataResult<List<HistoryEvent>> {
        try {
            val result = historyEventDao.getHistoryEvents()
            return DataResult.Success(result.map { it.toDomain() })
        } catch (e: Exception) {
            return DataResult.Error(e)
        }
    }
    override suspend fun insertHistoryEvent(historyEvent: HistoryEvent): DataResult<Boolean> {
        try {
            historyEventDao.insertHistoryEvent(historyEvent.toData())
            return DataResult.Success(true)
        } catch (e: Exception) {
            return DataResult.Error(e)
        }
    }
    override suspend fun deleteHistoryEvent(historyEvent: HistoryEvent): DataResult<Boolean> {
        try {
            historyEventDao.deleteHistoryEvent(historyEvent.toData().id)
            return DataResult.Success(true)
        } catch (e: Exception) {
            return DataResult.Error(e)
        }
    }
}