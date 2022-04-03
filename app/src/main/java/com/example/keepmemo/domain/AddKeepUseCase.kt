package com.example.keepmemo.domain

import com.example.keepmemo.data.EmptyKeepException
import com.example.keepmemo.data.Result
import com.example.keepmemo.data.repository.memolist.KeepRepositoryInterface
import com.example.keepmemo.model.Keep
import javax.inject.Inject

class AddKeepUseCase @Inject constructor(
    private val keepRepository: KeepRepositoryInterface
) {
    fun invokeAddKeep(keep: Keep): Result<Unit> {
        if (keep.title.isEmpty() && keep.body.isEmpty()) {
            return Result.Error(EmptyKeepException)
        }
        return keepRepository.saveKeep(keep)
    }
}
