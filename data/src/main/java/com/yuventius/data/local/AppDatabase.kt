package com.yuventius.data.local

import androidx.room.Database
import com.yuventius.data.local.dao.space_x.HistoryEventDao
import com.yuventius.data.local.model.space_x.HistoryEventLocal

@Database(
    entities = [
        HistoryEventLocal::class
    ],
    version = 1
)
abstract class AppDatabase {
    abstract fun historyEventDao(): HistoryEventDao
}