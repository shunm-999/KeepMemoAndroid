package com.example.keepmemo.core.data.di

import com.example.keepmemo.core.common.di.Dispatcher
import com.example.keepmemo.core.common.di.KeepMemoDispatchers
import com.example.keepmemo.core.data.repository.MemoRepositoryImpl
import com.example.keepmemo.core.data.repository.MemoRepositoryInterface
import com.example.keepmemo.core.data.repository.UserRepositoryImpl
import com.example.keepmemo.core.data.repository.UserRepositoryInterface
import com.example.keepmemo.core.database.dao.KeepDao
import com.example.keepmemo.core.database.dao.MemoDao
import com.example.keepmemo.core.database.dao.UserDao
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
        @Dispatcher(KeepMemoDispatchers.IO) ioDispatcher: CoroutineDispatcher,
        keepDao: KeepDao,
        memoDao: MemoDao
    ): MemoRepositoryInterface = MemoRepositoryImpl(
        ioDispatcher = ioDispatcher,
        keepDao = keepDao,
        memoDao = memoDao
    )

    @Singleton
    @Provides
    fun provideUserRepository(
        @Dispatcher(KeepMemoDispatchers.IO) ioDispatcher: CoroutineDispatcher,
        userDao: UserDao
    ): UserRepositoryInterface = UserRepositoryImpl(
        ioDispatcher = ioDispatcher,
        userDao = userDao
    )
}
