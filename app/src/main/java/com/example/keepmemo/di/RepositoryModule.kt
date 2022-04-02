package com.example.keepmemo.di

import com.example.keepmemo.data.repository.memolist.FakeKeepMemoListRepositoryImpl
import com.example.keepmemo.data.repository.memolist.KeepMemoListRepositoryInterface
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
    fun provideKeepMemoListRepository(
        @IODispatcher ioDispatcher: CoroutineDispatcher
    ): KeepMemoListRepositoryInterface = FakeKeepMemoListRepositoryImpl(
        ioDispatcher = ioDispatcher
    )
}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class FakeMemoListRepository
