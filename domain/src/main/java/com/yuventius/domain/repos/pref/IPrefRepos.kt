package com.yuventius.domain.repos.pref

import kotlinx.coroutines.flow.Flow

interface IPrefRepos {
    suspend fun saveLoginInfo(email: String, password: String)
    suspend fun getLoginInfo(): Flow<Pair<String, String>>
    suspend fun logOut(): Flow<Boolean>
}