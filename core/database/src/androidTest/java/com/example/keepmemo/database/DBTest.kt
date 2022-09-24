package com.example.keepmemo.database

import android.app.Application
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.junit.Before

abstract class DBTest {

    companion object {
        private const val DATABASE_NAME_TEST = "test_db"
    }

    lateinit var appDatabase: AppDatabase
        private set

    @Before
    open fun setUpParent() {
        val context = ApplicationProvider.getApplicationContext<Application>()

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
