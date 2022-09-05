package com.example.keepmemo.db.dao

import android.database.sqlite.SQLiteConstraintException
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.keepmemo.data.db.dao.KeepDao
import com.example.keepmemo.data.db.dao.MemoDao
import com.example.keepmemo.data.db.entity.KeepEntityImpl
import com.example.keepmemo.data.db.entity.MemoEntityImpl
import com.example.keepmemo.db.DBTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith

@RunWith(Enclosed::class)
class MemoDaoTest {

    abstract class MemoDaoTestSetup : DBTest() {

        companion object {
            fun createDummy(
                id: Long,
                keepId: Long,
                memoIndex: Long,
                isPinned: Boolean,
            ): MemoEntityImpl {
                return MemoEntityImpl(
                    id = id,
                    keepId = keepId,
                    memoIndex = memoIndex,
                    isPinned = isPinned,
                    updateDate = System.currentTimeMillis()
                )
            }
        }

        lateinit var keepDao: KeepDao
        lateinit var memoDao: MemoDao

        @Before
        override fun setUpParent() {
            super.setUpParent()
            keepDao = appDatabase.keepDao()
            memoDao = appDatabase.memoDao()
        }
    }

    @RunWith(AndroidJUnit4::class)
    class BlankRecord : MemoDaoTestSetup() {

        @Test(expected = SQLiteConstraintException::class)
        fun Keep0件でMemoは挿入できない() = runBlocking {
            (1L..2L).map {
                createDummy(
                    id = it,
                    keepId = it,
                    memoIndex = it,
                    isPinned = false
                )
            }.forEach { memoEntity ->
                memoDao.insert(memoEntity)
            }
        }

        @Test
        fun 挿入した件数と同じ件数取得できる() = runBlocking {
            val keepEntityImplList = (1L..3L).map { id ->
                KeepEntityImpl(
                    id = id,
                    title = "title$id",
                    body = "body$id",
                    updateDate = System.currentTimeMillis()
                )
            }

            val keepIdList = keepEntityImplList.map {
                keepDao.insert(it)
            }

            keepIdList.mapIndexed { index, keepId ->
                createDummy(
                    id = 0,
                    keepId = keepId,
                    memoIndex = index.toLong(),
                    isPinned = false
                )
            }.forEach {
                memoDao.insert(it)
            }

            assertThat(memoDao.selectOrderByIndex().first()).hasSize(3)
        }
    }
}