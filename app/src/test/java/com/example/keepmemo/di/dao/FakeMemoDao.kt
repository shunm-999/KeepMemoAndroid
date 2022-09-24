package com.example.keepmemo.di.dao

import com.example.keepmemo.database.dao.MemoDao
import com.example.keepmemo.database.entity.KeepEntityImpl
import com.example.keepmemo.database.entity.MemoEntityImpl
import com.example.keepmemo.database.entity.MemoWithKeepEntityImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.update

class FakeMemoDao(
    private val keepEntityMapFlow: MutableStateFlow<MutableMap<Long, KeepEntityImpl>>,
    private val memoEntityMapFlow: MutableStateFlow<MutableMap<Long, MemoEntityImpl>>
) : MemoDao {

    override fun selectOrderByIndex(): Flow<List<MemoWithKeepEntityImpl>> {
        return memoEntityMapFlow.map { memoEntityMap ->
            memoEntityMap.values.toList()
        }.mapNotNull { memoEntityList ->
            memoEntityList.map { memoEntity ->
                val keepEntity =
                    keepEntityMapFlow.value[memoEntity.keepId] ?: return@mapNotNull null
                MemoWithKeepEntityImpl(
                    memo = memoEntity,
                    keep = keepEntity
                )
            }
        }
    }

    override fun selectById(id: Long): MemoWithKeepEntityImpl? {
        val memoEntityImpl = memoEntityMapFlow.value[id] ?: return null
        val keepEntityImpl = keepEntityMapFlow.value[memoEntityImpl.keepId] ?: return null

        return MemoWithKeepEntityImpl(
            memo = memoEntityImpl,
            keep = keepEntityImpl
        )
    }

    override fun selectMaxIndex(): Long {
        if (memoEntityMapFlow.value.values.isEmpty()) {
            return 0L
        }
        return memoEntityMapFlow.value.values.map {
            it.memoIndex
        }.maxOf {
            it
        }
    }

    override fun insert(memoEntityImpl: MemoEntityImpl): Long {
        val memoWithKeepList = memoEntityMapFlow.value

        val id = if (memoWithKeepList.contains(memoEntityImpl.id)) {
            memoEntityImpl.id
        } else {
            (memoWithKeepList.keys.maxOfOrNull { it } ?: 0) + 1
        }

        memoEntityMapFlow.update {
            it.apply {
                this[id] = memoEntityImpl.copy(
                    id = id
                )
            }
        }
        return id
    }
}
