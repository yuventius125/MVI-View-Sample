package com.yuventius.domain.use_case

import android.content.Context
import com.yuventius.domain.repos.pref.IPrefRepos
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PrefUseCase @Inject constructor(
    private val prefRepos: IPrefRepos,
) {
    suspend fun saveLoginInfo(email: String, password: String) = prefRepos.saveLoginInfo(email, password)
    suspend fun getLoginInfo() = prefRepos.getLoginInfo()
    suspend fun logOut() = prefRepos.logOut()
}