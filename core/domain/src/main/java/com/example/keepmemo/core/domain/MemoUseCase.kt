package com.example.keepmemo.core.domain

import com.example.keepmemo.core.common.exception.EmptyKeepException
import com.example.keepmemo.core.common.result.Result
import com.example.keepmemo.core.data.repository.MemoRepositoryInterface
import com.example.keepmemo.core.model.data.Memo
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class MemoUseCase @Inject constructor(
    private val memoRepository: MemoRepositoryInterface
) {
    suspend fun invokeMemo(memoId: Long): Result<Memo> {
        return memoRepository.getMemo(memoId)
    }

    suspend fun invokeMemoList(): Flow<Result<List<Memo>>> {
        return memoRepository.observeMemoList()
    }

    suspend fun invokeAddMemo(title: String, body: String): Result<Long> {
        if (title.isEmpty() && body.isEmpty()) {
            return Result.Error(EmptyKeepException)
        }
        return memoRepository.saveMemo(title, body)
    }

    suspend fun invokeUpdateMemo(memoId: Long, title: String, body: String): Result<Unit> {
        if (title.isEmpty() && body.isEmpty()) {
            return Result.Error(EmptyKeepException)
        }
        return memoRepository.updateMemo(memoId, title, body)
    }
}
