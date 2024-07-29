package com.yuventius.data.local

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.yuventius.data.local.converter.ArrayListStringConverter
import com.yuventius.data.local.dao.space_x.HistoryEventDao
import com.yuventius.data.local.model.space_x.HistoryEventLocal

@Database(
    entities = [
        HistoryEventLocal::class
    ],
    version = 2,
    exportSchema = true
)
@TypeConverters(value = [
    ArrayListStringConverter::class
])
abstract class AppDatabase: RoomDatabase() {
    abstract fun historyEventDao(): HistoryEventDao
}

val MIGRATION_1_TO_2 = object: Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("""
            ALTER TABLE history_event
            RENAME COLUMN id TO idx
            ADD COLUMN id TEXT NOT NULL
        """.trimIndent())
    }
}