package com.example.keepmemo.db.dao

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.keepmemo.data.db.dao.KeepDao
import com.example.keepmemo.data.db.entity.KeepEntityImpl
import com.example.keepmemo.db.DBTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith

@RunWith(Enclosed::class)
class KeepMemoDaoTest {
    abstract class KeepMemoDaoTestSetup : DBTest() {

        companion object {
            fun createDummy(range: LongRange): List<KeepEntityImpl> {
                return range.map { id ->
                    KeepEntityImpl(
                        id = 0,
                        title = "title$id",
                        body = "body$id",
                        updateDate = System.currentTimeMillis()
                    )
                }
            }
        }

        lateinit var keepDao: KeepDao

        @Before
        override fun setUpParent() {
            super.setUpParent()
            keepDao = appDatabase.keepDao()
        }
    }

    @RunWith(AndroidJUnit4::class)
    class BlankRecord : KeepMemoDaoTestSetup() {

        @OptIn(ExperimentalCoroutinesApi::class)
        @Test
        fun 挿入した件数と同じ件数が返ってくる() = runBlocking {
            val keepEntityDummyList = createDummy(1L..10L)
            keepEntityDummyList.forEach {
                keepDao.insert(it)
            }

            val actual = keepDao.select().first().size
            assertThat(actual).isEqualTo(10)
        }
    }
}
