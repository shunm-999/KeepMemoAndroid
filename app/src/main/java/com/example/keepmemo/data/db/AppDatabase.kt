package com.example.keepmemo.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.keepmemo.data.db.dao.KeepDao
import com.example.keepmemo.data.db.dao.MemoDao
import com.example.keepmemo.data.db.entity.KeepEntityImpl
import com.example.keepmemo.data.db.entity.MemoEntityImpl

@Database(
    entities = [
        KeepEntityImpl::class,
        MemoEntityImpl::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        fun createDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java, "database-keep"
            ).build()
        }
    }

    abstract fun keepDao(): KeepDao
    abstract fun memoDao(): MemoDao
}
