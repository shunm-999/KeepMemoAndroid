package com.example.keepmemo.database

import android.content.Context
import com.example.keepmemo.database.dao.KeepDao
import com.example.keepmemo.database.dao.MemoDao
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
