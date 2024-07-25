package com.yuventius.data.core

import android.content.Context
import androidx.room.Room
import com.yuventius.data.local.AppDatabase
import com.yuventius.data.local.repos.HistoryEventReposLocal
import com.yuventius.data.remote.AppClient
import com.yuventius.data.remote.repos.space_x.HistoryEventReposRemote
import com.yuventius.domain.repos.local.IHistoryEventReposLocal
import com.yuventius.domain.repos.remote.IHistoryEventReposRemote
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataBindModule {
    @Binds
    @Singleton
    abstract fun bindHistoryReposRemote(impl: HistoryEventReposRemote): IHistoryEventReposRemote

    @Binds
    @Singleton
    abstract fun bindHistoryReposLocal(impl: HistoryEventReposLocal): IHistoryEventReposLocal
}

@Module
@InstallIn(SingletonComponent::class)
class DataProvideModule {
    @Provides
    @Singleton
    fun provideHttpClient() = AppClient.getInstance()

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(context, AppDatabase::class.java, "app_database.db").build()

    @Provides
    @Singleton
    fun provideHistoryEventDao(db: AppDatabase) = db.historyEventDao()
}