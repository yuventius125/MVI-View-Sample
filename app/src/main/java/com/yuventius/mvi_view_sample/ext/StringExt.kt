package com.yuventius.mvi_view_sample.ext

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Locale

/**
 * 버전 체크
 * @param serverVersion 서버 버전
 * @return [Boolean] True: 앱 버전이 같거나 높음, False: 버전이 낮음
 */
fun String.toCompareVersion(serverVersion: String): Boolean {
    val appVersionArr: List<Int> = this.split(".").map { it.toInt() }
    val serverVersionArr: List<Int> = serverVersion.split(".").map { it.toInt() }
    for (index in 0 until minOf(serverVersionArr.size, appVersionArr.size)) {
        if (serverVersionArr[index] > appVersionArr[index]) {
            return true
        } else if (serverVersionArr[index] == appVersionArr[index]) {
            continue
        } else {
            return false
        }
    }

    return serverVersionArr.size > appVersionArr.size
}

/**
 * UTC 기반 String 변환
 * @return [LocalDateTime] 날짜 데이터 / 변환 불가 시 null
 */
fun String.toLocalDateTimeByUTC(): LocalDateTime? {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    val date = dateFormat.parse(this)
    return date?.toInstant()?.atZone(ZoneId.systemDefault())?.toLocalDateTime()
}