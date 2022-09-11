package com.example.keepmemo.domain

import com.example.keepmemo.data.EmptyKeepException
import com.example.keepmemo.data.Result
import com.example.keepmemo.data.repository.memolist.MemoRepositoryInterface
import com.example.keepmemo.model.Memo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

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
