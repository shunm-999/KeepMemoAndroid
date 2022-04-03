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

class FakeKeepRepositoryImpl(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) : KeepRepositoryInterface {

    private val _keepMap = MutableStateFlow(testDataMap)

    override fun getKeep(keepId: Long): Result<Keep> {
        return try {
            val keep = _keepMap.value[keepId] ?: throw IllegalStateException("Keep is not found")
            Result.Success(keep)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override fun observeKeepMemoList(): Flow<Result<List<Keep>>> {
        return _keepMap.transform {
            emit(Result.Success(it.values.sortedBy { keep -> keep.id }) as Result<List<Keep>>)
        }.catch {
            emit(Result.Error(IllegalStateException("keep not found")))
        }.flowOn(ioDispatcher)
    }

    override fun saveKeep(keep: Keep): Result<Unit> {
        _keepMap.update {
            val newId = if (keep.id == Keep.EMPTY.id) {
                it.keys.maxOf { key -> key } + 1L
            } else {
                keep.id
            }
            mutableMapOf<Long, Keep>().apply {
                putAll(it)
                put(
                    newId,
                    keep.copy(
                        id = newId
                    )
                )
            }
        }
        return Result.Success(Unit)
    }
}

private val testDataMap = listOf<Keep>(
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
).let { keepList ->
    mutableMapOf<Long, Keep>().apply {
        keepList.forEach { keep ->
            this[keep.id] = keep
        }
    }
}
