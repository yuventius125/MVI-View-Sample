package com.yuventius.data.pref.serializer

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.yuventius.data.UserInfo
import java.io.InputStream
import java.io.OutputStream

object UserInfoSerializer: Serializer<UserInfo> {
    override val defaultValue: UserInfo
        get() = UserInfo.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserInfo {
        try {
            return UserInfo.parseFrom(input)
        } catch (e: InvalidProtocolBufferException) {
            throw CorruptionException("proto 파싱 에러", e)
        }
    }

    override suspend fun writeTo(t: UserInfo, output: OutputStream) {
        t.writeTo(output)
    }
}