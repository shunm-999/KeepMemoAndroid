package com.example.keepmemo.di

import com.example.keepmemo.data.repository.memolist.KeepRepositoryInterface
import com.example.keepmemo.domain.AddKeepUseCase
import com.example.keepmemo.domain.KeepListUseCase
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
    fun provideKeepListUseCase(
        @FakeMemoListRepository keepRepository: KeepRepositoryInterface
    ): KeepListUseCase {
        return KeepListUseCase(keepRepository)
    }

    @ActivityRetainedScoped
    @Provides
    fun provideAddKeepUseCase(
        @FakeMemoListRepository keepRepository: KeepRepositoryInterface
    ): AddKeepUseCase {
        return AddKeepUseCase(keepRepository)
    }
}
