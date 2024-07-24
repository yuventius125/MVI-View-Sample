package com.yuventius.data.local.converter

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ArrayListStringConverter {
    @TypeConverter
    fun toString(list: ArrayList<String>): String {
        return Json.encodeToString(list)
    }

    @TypeConverter
    fun toArrayListString(value: String): ArrayList<String> {
        return Json.decodeFromString(value)
    }
}