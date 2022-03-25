package com.example.keepmemo.domain

import com.example.keepmemo.data.Result
import com.example.keepmemo.data.repository.memolist.KeepMemoListRepositoryInterface
import com.example.keepmemo.model.Keep
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class KeepMemoListUseCase @Inject constructor(
    private val keepMemoListRepository: KeepMemoListRepositoryInterface
) {

    fun invokeKeepMemoList(): Flow<Result<List<Keep>>> {
        return keepMemoListRepository.observeKeepMemoList()
    }
}
