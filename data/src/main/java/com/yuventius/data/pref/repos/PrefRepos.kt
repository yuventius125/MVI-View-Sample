package com.yuventius.data.pref.repos

import android.content.Context
import com.yuventius.data.pref.dataStore
import com.yuventius.domain.repos.pref.IPrefRepos
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single
import javax.inject.Inject

class PrefRepos @Inject constructor(
    @ApplicationContext
    private val context: Context
) : IPrefRepos {
    override suspend fun saveLoginInfo(email: String, password: String) {
        context.dataStore.updateData {
            it.toBuilder()
                .setEmail(email)
                .setPassword(password)
                .build()
        }
    }

    override suspend fun getLoginInfo(): Flow<Pair<String, String>> {
        return flow {
            context.dataStore.data.collect {
                emit(Pair(it.email, it.password))
            }
        }
    }

    override suspend fun logOut(): Flow<Boolean> {
        return flow {
            context.dataStore.updateData {
                it.toBuilder()
                    .setEmail("")
                    .setPassword("")
                    .setFavoriteCount(0)
                    .build()
            }
            emit(true)
        }
    }
}