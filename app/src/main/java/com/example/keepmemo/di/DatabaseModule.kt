package com.example.keepmemo.di

import android.content.Context
import com.example.keepmemo.data.db.AppDatabase
import com.example.keepmemo.data.db.dao.KeepDao
import com.example.keepmemo.data.db.dao.MemoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return AppDatabase.createDatabase(context)
    }

    @Singleton
    @Provides
    fun provideKeepDao(
        appDatabase: AppDatabase
    ): KeepDao {
        return appDatabase.keepDao()
    }

    @Singleton
    @Provides
    fun provideMemoDao(
        appDatabase: AppDatabase
    ): MemoDao {
        return appDatabase.memoDao()
    }
}
