package com.example.keepmemo.database.dao

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.keepmemo.database.DBTest
import com.example.keepmemo.database.entity.KeepEntityImpl
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith

@RunWith(Enclosed::class)
class KeepDaoTest {
    abstract class KeepDaoTestSetup : DBTest() {

        companion object {

            fun createDummy(id: Long): KeepEntityImpl {
                return KeepEntityImpl(
                    id = id,
                    title = "title",
                    body = "body",
                    updateDate = System.currentTimeMillis()
                )
            }

            fun createDummy(range: LongRange): List<KeepEntityImpl> {
                return range.map { id ->
                    KeepEntityImpl(
                        id = id,
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
    class BlankRecord : KeepDaoTestSetup() {

        @Test
        fun 挿入した件数と同じ件数が取得できる() = runBlocking {
            val keepEntityDummyList = createDummy(1L..10L)
            keepEntityDummyList.forEach {
                keepDao.insert(it)
            }

            val actual = keepDao.select().first().size
            assertThat(actual).isEqualTo(10)
        }

        @Test
        fun 挿入したKeepをId指定で取得できる() = runBlocking {
            val keepEntityDummy = createDummy(id = 1)
            assertThat(keepDao.selectById(1)).isNull()
            keepDao.insert(keepEntityDummy)
            assertThat(keepDao.selectById(1)).isEqualTo(keepEntityDummy)
        }

        @Test
        fun 存在しないId指定でNullが変える() = runBlocking {
            val keepEntityDummy = createDummy(id = 1)
            keepDao.insert(keepEntityDummy)
            assertThat(keepDao.selectById(2)).isNull()
        }

        @Test
        fun すでに存在するIdでinsertする場合は_上書きする() = runBlocking {
            val keepEntityDummy = createDummy(id = 1)
            keepDao.insert(keepEntityDummy.copy(title = "title before"))
            assertThat(keepDao.selectById(1)?.title).isEqualTo("title before")
            keepDao.insert(keepEntityDummy.copy(title = "title after"))
            assertThat(keepDao.selectById(1)?.title).isEqualTo("title after")
        }

        @Test
        fun すでに存在するIdでupdateする場合は_上書きする() = runBlocking {
            val keepEntityDummy = createDummy(id = 1)
            keepDao.insert(keepEntityDummy.copy(title = "title before"))
            assertThat(keepDao.selectById(1)?.title).isEqualTo("title before")
            keepDao.update(keepEntityDummy.copy(title = "title after"))
            assertThat(keepDao.selectById(1)?.title).isEqualTo("title after")
        }
    }
}
