package com.yuventius.mvi_view_sample.ext

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