package com.example.keepmemo.di

import com.example.keepmemo.data.repository.memolist.MemoRepositoryImpl
import com.example.keepmemo.data.repository.memolist.MemoRepositoryInterface
import com.example.keepmemo.database.dao.KeepDao
import com.example.keepmemo.database.dao.MemoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMemoRepository(
        @IODispatcher ioDispatcher: CoroutineDispatcher,
        keepDao: KeepDao,
        memoDao: MemoDao
    ): MemoRepositoryInterface = MemoRepositoryImpl(
        ioDispatcher = ioDispatcher,
        keepDao = keepDao,
        memoDao = memoDao
    )
}
