package com.example.keepmemo.di

import com.example.keepmemo.data.repository.memolist.FakeKeepRepositoryImpl
import com.example.keepmemo.data.repository.memolist.KeepRepositoryInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @FakeMemoListRepository
    @Singleton
    @Provides
    fun provideKeepRepository(
        @IODispatcher ioDispatcher: CoroutineDispatcher
    ): KeepRepositoryInterface = FakeKeepRepositoryImpl(
        ioDispatcher = ioDispatcher
    )
}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class FakeMemoListRepository
