package com.yuventius.mvi_view_sample.ext

import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.url(url: String) = startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))