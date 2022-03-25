package com.example.keepmemo.data.repository.memolist

import com.example.keepmemo.data.Result
import com.example.keepmemo.di.IODispatcher
import com.example.keepmemo.model.Keep
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FakeKeepMemoListRepositoryImpl(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) : KeepMemoListRepositoryInterface {

    override fun observeKeepMemoList(): Flow<Result<List<Keep>>> {
        return flow {
            emit(Result.Success(testData) as Result<List<Keep>>)
        }.catch {
            emit(Result.Error(IllegalStateException("keep not found")))
        }.flowOn(ioDispatcher)
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
