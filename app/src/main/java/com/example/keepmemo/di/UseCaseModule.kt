package com.example.keepmemo.di

import com.example.keepmemo.data.repository.memolist.MemoRepositoryInterface
import com.example.keepmemo.domain.AlarmUseCase
import com.example.keepmemo.domain.MemoUseCase
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
    ): MemoUseCase {
        return MemoUseCase(memoRepository)
    }

    @ActivityRetainedScoped
    @Provides
    fun provideAlarmUseCase(): AlarmUseCase {
        return AlarmUseCase()
    }
}
