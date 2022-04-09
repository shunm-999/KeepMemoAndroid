package com.example.keepmemo.data.repository.memolist

import com.example.keepmemo.data.Result
import com.example.keepmemo.model.Memo
import kotlinx.coroutines.flow.Flow

interface MemoRepositoryInterface {

    suspend fun observeMemoList(): Flow<Result<List<Memo>>>
    suspend fun getMemo(memoId: Long): Result<Memo>

    /**
     * 新規追加S
     */
    suspend fun saveMemo(title: String, body: String): Result<Unit>

    suspend fun updateMemo(memoId: Long, title: String, body: String): Result<Unit>
}
