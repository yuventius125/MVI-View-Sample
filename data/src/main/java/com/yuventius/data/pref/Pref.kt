package com.yuventius.data.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.yuventius.data.UserInfo
import com.yuventius.data.pref.serializer.UserInfoSerializer

val Context.dataStore: DataStore<UserInfo> by dataStore(
    fileName = "user_info.pb",
    serializer = UserInfoSerializer
)