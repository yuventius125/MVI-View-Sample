package com.yuventius.data.local.converter

import androidx.room.TypeConverter

abstract class BaseConverter<T> {
    @TypeConverter
    open fun toString(value: T?): String? = value?.toString()

    @TypeConverter
    open fun fromString(value: String?): T? = value?.let { objectFromString(it) } ?: run { null }

    abstract fun objectFromString(value: String?): T?
}