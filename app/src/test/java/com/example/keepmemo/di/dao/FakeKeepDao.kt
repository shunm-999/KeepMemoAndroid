package com.example.keepmemo.di.dao

import com.example.keepmemo.core.database.dao.KeepDao
import com.example.keepmemo.core.database.entity.KeepEntityImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class FakeKeepDao(
    private val keepEntityMapFlow: MutableStateFlow<MutableMap<Long, KeepEntityImpl>>
) : KeepDao {

    override fun select(): Flow<List<KeepEntityImpl>> {
        return keepEntityMapFlow.map {
            it.values.toList()
        }
    }

    override fun selectById(id: Long): KeepEntityImpl? {
        return keepEntityMapFlow.value[id]
    }

    override fun insert(keepEntityImpl: KeepEntityImpl): Long {
        val keepEntityList = keepEntityMapFlow.value

        val id = if (keepEntityList.contains(keepEntityImpl.id)) {
            keepEntityImpl.id
        } else {
            (keepEntityList.keys.maxOfOrNull { it } ?: 0) + 1
        }

        keepEntityMapFlow.update { keepEntityMap ->
            keepEntityMap.apply {
                this[id] = keepEntityImpl.copy(
                    id = id
                )
            }
        }

        return id
    }

    override fun update(keepEntityImpl: KeepEntityImpl) {
        if (!keepEntityMapFlow.value.contains(keepEntityImpl.id)) {
            return
        }

        keepEntityMapFlow.update { keepEntityMap ->
            keepEntityMap.apply {
                this[keepEntityImpl.id] = keepEntityImpl
            }
        }
    }
}
