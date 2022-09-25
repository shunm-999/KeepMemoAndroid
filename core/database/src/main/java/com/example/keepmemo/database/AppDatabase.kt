package com.example.keepmemo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.keepmemo.database.dao.KeepDao
import com.example.keepmemo.database.dao.MemoDao
import com.example.keepmemo.database.entity.KeepEntityImpl
import com.example.keepmemo.database.entity.MemoEntityImpl

@Database(
    entities = [
        KeepEntityImpl::class,
        MemoEntityImpl::class
    ],
    version = 2
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
}
