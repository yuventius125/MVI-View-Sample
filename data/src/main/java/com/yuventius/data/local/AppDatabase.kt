package com.yuventius.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.yuventius.data.local.converter.ArrayListStringConverter
import com.yuventius.data.local.converter.LocalDateTimeConverter
import com.yuventius.data.local.dao.space_x.HistoryEventDao
import com.yuventius.data.local.model.space_x.HistoryEventLocal

@Database(
    entities = [
        HistoryEventLocal::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(value = [
    LocalDateTimeConverter::class,
    ArrayListStringConverter::class
])
abstract class AppDatabase: RoomDatabase() {
    abstract fun historyEventDao(): HistoryEventDao
}