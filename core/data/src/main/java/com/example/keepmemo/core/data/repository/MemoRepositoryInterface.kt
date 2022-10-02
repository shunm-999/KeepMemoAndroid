package com.example.keepmemo.core.data.repository

import com.example.keepmemo.core.common.result.Result
import com.example.keepmemo.core.model.data.Memo
import kotlinx.coroutines.flow.Flow

interface MemoRepositoryInterface {

    suspend fun observeMemoList(): Flow<Result<List<Memo>>>
    suspend fun getMemo(memoId: Long): Result<Memo>

    /**
     * 新規追加S
     */
    suspend fun saveMemo(title: String, body: String): Result<Long>

    suspend fun updateMemo(memoId: Long, title: String, body: String): Result<Unit>
}
