package com.example.keepmemo.di

import com.example.keepmemo.data.repository.memolist.KeepMemoListRepositoryInterface
import com.example.keepmemo.domain.KeepMemoListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideKeepMemoListUseCase(
        @FakeMemoListRepository keepMemoListRepository: KeepMemoListRepositoryInterface
    ): KeepMemoListUseCase {
        return KeepMemoListUseCase(keepMemoListRepository)
    }
}
