package com.example.keepmemo.domain

import com.example.keepmemo.data.Result
import com.example.keepmemo.data.repository.memolist.KeepRepositoryInterface
import com.example.keepmemo.model.Keep
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class KeepListUseCase @Inject constructor(
    private val keepRepository: KeepRepositoryInterface
) {

    fun invokeKeep(keepId: Long): Result<Keep> {
        return keepRepository.getKeep(keepId)
    }

    fun invokeKeepMemoList(): Flow<Result<List<Keep>>> {
        return keepRepository.observeKeepMemoList()
    }
}
