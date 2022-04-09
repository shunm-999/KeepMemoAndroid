package com.example.keepmemo.data.repository.memolist

import androidx.room.withTransaction
import com.example.keepmemo.common.converter.RoomEntityConverter
import com.example.keepmemo.data.Result
import com.example.keepmemo.data.db.AppDatabase
import com.example.keepmemo.data.db.dao.KeepDao
import com.example.keepmemo.data.db.dao.MemoDao
import com.example.keepmemo.data.db.entity.KeepEntityImpl
import com.example.keepmemo.data.db.entity.MemoEntityImpl
import com.example.keepmemo.di.IODispatcher
import com.example.keepmemo.model.Memo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class MemoRepositoryImpl(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val appDatabase: AppDatabase,
    private val keepDao: KeepDao,
    private val memoDao: MemoDao
) : MemoRepositoryInterface {

    override suspend fun observeMemoList(): Flow<Result<List<Memo>>> {
        return memoDao.select().map {
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
            }
            Result.Success(RoomEntityConverter.convertToMemo(memo))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun saveMemo(title: String, body: String): Result<Unit> {
        return try {
            appDatabase.withTransaction {
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
                memoDao.insert(memoEntityImpl)
            }
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun updateMemo(memoId: Long, title: String, body: String): Result<Unit> {
        return try {
            appDatabase.withTransaction {
                val memoEntityImpl = memoDao.selectById(memoId)
                val keepEntityImpl = KeepEntityImpl(
                    id = memoEntityImpl.keep.id,
                    title = title,
                    body = body,
                    updateDate = System.currentTimeMillis()
                )
                keepDao.update(keepEntityImpl)
            }
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
