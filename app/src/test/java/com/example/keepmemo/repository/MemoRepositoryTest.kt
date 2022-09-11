package com.example.keepmemo.repository

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.keepmemo.data.Result
import com.example.keepmemo.data.db.dao.KeepDao
import com.example.keepmemo.data.db.dao.MemoDao
import com.example.keepmemo.data.db.entity.KeepEntityImpl
import com.example.keepmemo.data.db.entity.MemoEntityImpl
import com.example.keepmemo.data.repository.memolist.MemoRepositoryImpl
import com.example.keepmemo.di.dao.FakeKeepDao
import com.example.keepmemo.di.dao.FakeMemoDao
import com.example.keepmemo.testutil.MainDispatcherRule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class MemoRepositoryTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    lateinit var keepEntityMapFlow: MutableStateFlow<MutableMap<Long, KeepEntityImpl>>
    lateinit var memoEntityMapFlow: MutableStateFlow<MutableMap<Long, MemoEntityImpl>>

    lateinit var keepDao: KeepDao
    lateinit var memoDao: MemoDao
    lateinit var memoRepositoryImpl: MemoRepositoryImpl

    @Before
    fun setUp() {
        keepEntityMapFlow = MutableStateFlow<MutableMap<Long, KeepEntityImpl>>(mutableMapOf())
        memoEntityMapFlow = MutableStateFlow<MutableMap<Long, MemoEntityImpl>>(mutableMapOf())
        keepDao = FakeKeepDao(keepEntityMapFlow)
        memoDao = FakeMemoDao(keepEntityMapFlow, memoEntityMapFlow)
        memoRepositoryImpl = MemoRepositoryImpl(
            ioDispatcher = UnconfinedTestDispatcher(),
            keepDao = keepDao,
            memoDao = memoDao
        )
    }

    @Test
    fun `空のリストを取得できる`() {
        runTest {
            val memoList = when (val result = memoRepositoryImpl.observeMemoList().first()) {
                is Result.Success -> result.data
                is Result.Error -> throw Exception("Result is Error")
            }
            assertThat(memoList).isEmpty()
        }
    }

    @Test
    fun `Memoを保存できる`() {
        runTest {
            val title = "title1"
            val body = "body1"
            val id = when (val result = memoRepositoryImpl.saveMemo(title, body)) {
                is Result.Success -> result.data
                is Result.Error -> throw Exception("Result is Error")
            }

            val memo = when (val result = memoRepositoryImpl.getMemo(id)) {
                is Result.Success -> result.data
                is Result.Error -> throw Exception("Result is Error")
            }

            assertThat(memo).isNotNull()
            assertThat(memo.keep.title).isEqualTo(title)
            assertThat(memo.keep.body).isEqualTo(body)
        }
    }

    @Test
    fun `Memoの内容を更新できる`() {
        runTest {
            val title1 = "title1"
            val body1 = "body1"

            val title2 = "title2"
            val body2 = "body2"

            val id = when (val result = memoRepositoryImpl.saveMemo(title1, body1)) {
                is Result.Success -> result.data
                is Result.Error -> throw Exception("Result is Error")
            }

            memoRepositoryImpl.updateMemo(
                memoId = id,
                title = title2,
                body = body2
            )

            val memo = when (val result = memoRepositoryImpl.getMemo(id)) {
                is Result.Success -> result.data
                is Result.Error -> throw Exception("Result is Error")
            }

            with(memo.keep) {
                assertThat(title).isNotEqualTo(title1)
                assertThat(title).isEqualTo(title2)
                assertThat(body).isNotEqualTo(body1)
                assertThat(body).isEqualTo(body2)
            }
        }
    }
}
