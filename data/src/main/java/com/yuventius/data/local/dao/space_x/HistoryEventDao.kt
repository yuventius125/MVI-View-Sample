package com.yuventius.data.local.dao.space_x

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yuventius.data.local.model.space_x.HistoryEventLocal

@Dao
interface HistoryEventDao {
    @Query("""
        SELECT * FROM history_event
    """)
    @Throws(Exception::class)
    suspend fun getHistoryEvents(): List<HistoryEventLocal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Throws(Exception::class)
    suspend fun insertHistoryEvent(historyEvent: HistoryEventLocal)

    @Delete
    @Throws(Exception::class)
    suspend fun deleteHistoryEvent(historyEvent: HistoryEventLocal)
}