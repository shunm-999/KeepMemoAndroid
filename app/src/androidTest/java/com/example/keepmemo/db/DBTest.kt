package com.example.keepmemo.db

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.keepmemo.KeepMemoApplication
import com.example.keepmemo.data.db.AppDatabase
import org.junit.Before

abstract class DBTest {

    companion object {
        private const val DATABASE_NAME_TEST = "test_db"
    }

    lateinit var appDatabase: AppDatabase
        private set

    @Before
    open fun setUpParent() {
        val context = ApplicationProvider.getApplicationContext<KeepMemoApplication>()

        appDatabase = Room
            .databaseBuilder(
                context,
                AppDatabase::class.java,
                DATABASE_NAME_TEST
            )
            .allowMainThreadQueries().build().apply {
                clearAllTables()
            }
    }
}
