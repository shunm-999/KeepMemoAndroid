package com.example.keepmemo.data.repository.memolist

import com.example.keepmemo.data.Result
import com.example.keepmemo.di.IODispatcher
import com.example.keepmemo.model.Keep
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.update

class FakeKeepMemoListRepositoryImpl(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) : KeepMemoListRepositoryInterface {

    private val _keepList = MutableStateFlow(testData)

    override fun observeKeepMemoList(): Flow<Result<List<Keep>>> {
        return _keepList.transform {
            emit(Result.Success(it) as Result<List<Keep>>)
        }.catch {
            emit(Result.Error(IllegalStateException("keep not found")))
        }.flowOn(ioDispatcher)
    }

    override fun saveKeep(keep: Keep): Result<Unit> {
        _keepList.update {
            it.toMutableList().apply {
                add(keep)
            }
        }
        return Result.Success(Unit)
    }
}

private val testData = listOf<Keep>(
    Keep(
        id = 1, title = "title1",
        body = """
        body1body1body1body1body1body1body1body1body1body1body1
        body1body1body1body1body1body1body1body1body1body1body1
        body1body1body1body1body1body1body1body1body1body1body1
        body1body1body1body1body1body1body1body1body1body1body1
        body1body1body1body1body1body1body1body1body1body1body1
        body1body1body1body1body1body1body1body1body1body1body1
        """.trimIndent()
    ),
    Keep(
        id = 2, title = "title2",
        body = """
        body2body2body2body2body2body2body2body2body2body2body2
        body2body2body2body2body2body2body2body2body2body2body2
        body2body2body2body2body2body2body2body2body2body2body2
        body2body2body2body2body2body2body2body2body2body2body2
        body2body2body2body2body2body2body2body2body2body2body2
        body2body2body2body2body2body2body2body2body2body2body2
        """.trimIndent()
    ),
    Keep(
        id = 3, title = "title3",
        body = """
        body3body3body3body3body3body3body3body3body3body3body3
        body3body3body3body3body3body3body3body3body3body3body3
        body3body3body3body3body3body3body3body3body3body3body3
        body3body3body3body3body3body3body3body3body3body3body3
        body3body3body3body3body3body3body3body3body3body3body3
        body3body3body3body3body3body3body3body3body3body3body3
        """.trimIndent()
    )
)
