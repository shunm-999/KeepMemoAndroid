package com.example.keepmemo.core.common.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
class DispatchersModule {

    @Provides
    @Dispatcher(KeepMemoDispatchers.DEFAULT)
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    @Dispatcher(KeepMemoDispatchers.IO)
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO
}
