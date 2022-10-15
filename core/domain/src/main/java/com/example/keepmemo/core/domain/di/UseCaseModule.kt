package com.example.keepmemo.core.domain.di

import com.example.keepmemo.core.data.repository.MemoRepositoryInterface
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
    fun provideMemoUseCase(
        memoRepository: MemoRepositoryInterface
    ): com.example.keepmemo.core.domain.MemoUseCase {
        return com.example.keepmemo.core.domain.MemoUseCase(memoRepository)
    }
}
