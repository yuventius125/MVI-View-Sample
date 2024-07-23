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
}