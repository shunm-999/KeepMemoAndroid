package com.example.keepmemo.data.repository.memolist

import com.example.keepmemo.common.converter.RoomEntityConverter
import com.example.keepmemo.core.common.di.Dispatcher
import com.example.keepmemo.core.common.di.KeepMemoDispatchers
import com.example.keepmemo.core.common.result.Result
import com.example.keepmemo.core.database.dao.KeepDao
import com.example.keepmemo.core.database.dao.MemoDao
import com.example.keepmemo.core.database.entity.KeepEntityImpl
import com.example.keepmemo.core.database.entity.MemoEntityImpl
import com.example.keepmemo.core.model.data.Memo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class MemoRepositoryImpl(
    @Dispatcher(KeepMemoDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val keepDao: KeepDao,
    private val memoDao: MemoDao
) : MemoRepositoryInterface {

    override suspend fun observeMemoList(): Flow<Result<List<Memo>>> {
        return memoDao.selectOrderByIndex().map {
            val memoList = RoomEntityConverter.convertToMemo(it)
            Result.Success(memoList)
        }.catch { e ->
            Result.Error(Exception(e))
        }.flowOn(ioDispatcher)
    }

    override suspend fun getMemo(memoId: Long): Result<Memo> {
        return try {
            val memo = withContext(ioDispatcher) {
                memoDao.selectById(memoId)
            } ?: return Result.Error(IllegalStateException("memoId $memoId is not found"))
            Result.Success(RoomEntityConverter.convertToMemo(memo))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun saveMemo(title: String, body: String): Result<Long> {
        return withContext(ioDispatcher) {
            try {
                val keepEntityImpl = KeepEntityImpl(
                    id = 0,
                    title = title,
                    body = body,
                    updateDate = System.currentTimeMillis()
                )
                val keepId = keepDao.insert(keepEntityImpl)
                val memoIndex = memoDao.selectMaxIndex() + 1L
                val memoEntityImpl = MemoEntityImpl(
                    id = 0,
                    keepId = keepId,
                    memoIndex = memoIndex,
                    isPinned = false,
                    updateDate = System.currentTimeMillis()
                )
                val id = memoDao.insert(memoEntityImpl)
                Result.Success(id)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }

    override suspend fun updateMemo(memoId: Long, title: String, body: String): Result<Unit> {
        return withContext(ioDispatcher) {
            try {
                val memoEntityImpl =
                    memoDao.selectById(memoId) ?: return@withContext Result.Error(
                        IllegalStateException("memoId $memoId is not found")
                    )
                val keepEntityImpl = KeepEntityImpl(
                    id = memoEntityImpl.keep.id,
                    title = title,
                    body = body,
                    updateDate = System.currentTimeMillis()
                )
                keepDao.update(keepEntityImpl)
                Result.Success(Unit)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }
}
