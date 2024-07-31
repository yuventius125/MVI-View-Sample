package com.yuventius.domain.core

import com.yuventius.domain.repos.local.IHistoryEventReposLocal
import com.yuventius.domain.repos.pref.IPrefRepos
import com.yuventius.domain.repos.remote.IHistoryEventReposRemote
import com.yuventius.domain.use_case.PrefUseCase
import com.yuventius.domain.use_case.SpaceXUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun provideSpaceXUseCase(
        historyEventRemote: IHistoryEventReposRemote,
        historyEventLocal: IHistoryEventReposLocal
    ) = SpaceXUseCase(historyEventRemote, historyEventLocal)

    @Provides
    @Singleton
    fun providePrefUseCase(
        prefRepos: IPrefRepos
    ) = PrefUseCase(prefRepos)
}