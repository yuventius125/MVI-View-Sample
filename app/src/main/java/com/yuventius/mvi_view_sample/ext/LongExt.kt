package com.yuventius.mvi_view_sample.ext

fun Long.toTimeStamp(): String {
    val seconds = this / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    return "%02d:%02d:%02d".format(hours, minutes % 60, seconds % 60)
}