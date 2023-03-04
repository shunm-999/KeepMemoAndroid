package com.example.keepmemo.core.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.keepmemo.core.database.dao.KeepDao
import com.example.keepmemo.core.database.dao.MemoDao
import com.example.keepmemo.core.database.dao.UserDao
import com.example.keepmemo.core.database.entity.KeepEntityImpl
import com.example.keepmemo.core.database.entity.MemoEntityImpl
import com.example.keepmemo.core.database.entity.UserEntityImpl

@Database(
    version = 3,
    entities = [
        KeepEntityImpl::class,
        MemoEntityImpl::class,
        UserEntityImpl::class
    ],
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
        AutoMigration(from = 2, to = 3)
    ]
)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        fun createDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "database-keep"
            ).build()
        }
    }

    abstract fun keepDao(): KeepDao
    abstract fun memoDao(): MemoDao

    abstract fun userDao(): UserDao
}
