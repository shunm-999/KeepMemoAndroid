package com.example.keepmemo.data.repository.memolist

import com.example.keepmemo.data.Result
import com.example.keepmemo.model.Keep
import kotlinx.coroutines.flow.Flow

interface KeepMemoListRepositoryInterface {
    fun observeKeepMemoList(): Flow<Result<List<Keep>>>

    fun saveKeep(keep: Keep): Result<Unit>
}
