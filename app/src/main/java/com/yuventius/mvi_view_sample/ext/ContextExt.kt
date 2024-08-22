package com.yuventius.mvi_view_sample.ext

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import androidx.activity.ComponentActivity

fun Context.url(url: String) = startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))

fun Context.getActivity(): ComponentActivity? {
    when (this) {
        is ComponentActivity -> return this
        is ContextWrapper -> return baseContext.getActivity()
    }
    return null
}