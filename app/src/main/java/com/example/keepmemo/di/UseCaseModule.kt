package com.example.keepmemo.di

import com.example.keepmemo.data.repository.memolist.KeepMemoListRepositoryInterface
import com.example.keepmemo.domain.AddKeepUseCase
import com.example.keepmemo.domain.KeepMemoListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object UseCaseModule {

    @ActivityRetainedScoped
    @Provides
    fun provideKeepMemoListUseCase(
        @FakeMemoListRepository keepMemoListRepository: KeepMemoListRepositoryInterface
    ): KeepMemoListUseCase {
        return KeepMemoListUseCase(keepMemoListRepository)
    }

    @ActivityRetainedScoped
    @Provides
    fun provideAddKeepUseCase(
        @FakeMemoListRepository keepMemoListRepository: KeepMemoListRepositoryInterface
    ): AddKeepUseCase {
        return AddKeepUseCase(keepMemoListRepository)
    }
}
